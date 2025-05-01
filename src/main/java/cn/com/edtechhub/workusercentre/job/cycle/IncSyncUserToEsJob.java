package cn.com.edtechhub.workusercentre.job.cycle;

import cn.com.edtechhub.workusercentre.mapper.UserEsMapper;
import cn.com.edtechhub.workusercentre.model.entity.User;
import cn.com.edtechhub.workusercentre.model.entity.UserEs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 增量同步用户到 ES(每分钟一次)
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
// todo 取消注释开启任务
@Component // 注释这个则关闭增量同步
@Slf4j
public class IncSyncUserToEsJob {

    /**
     * 注入 userEsMapper
     */
    @Resource
    private UserEsMapper userEsMapper;

    /**
     * 注入 jdbcTemplate
     */
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 执行内容
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {

        // 当前时间(UTC)
        Instant now = Instant.now();

        // 2 分钟前(UTC)
        Instant fiveMinutesAgo = now.minus(2, ChronoUnit.MINUTES);

        // 构建 SQL 语句
        String sql = "SELECT * FROM user WHERE update_time >= ?"; //直接用 update_time 比较，无需转换（timestamp 默认是 UTC）

        // 查询数据得到 2 分钟内被更新的数据(不过不包含直接被删除的记录)
        List<User> userList = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(User.class),
                Timestamp.from(fiveMinutesAgo)
        );

        // 转为 ES 实体类
        List<UserEs> userEsList = userList.stream()
                .map(UserEs::EntityToMapping)
                .collect(Collectors.toList());

        // 分页批量插入到 ES
        final int pageSize = 10;
        int total = userEsList.size();

        if (total == 0) {
            log.debug("IncSyncUserToEsJob no data...");
        }
        else {
            log.debug("IncSyncUserToEsJob start, total {}", total);

            for (int i = 0; i < total; i += pageSize) {
                // 注意同步的数据下标不能超过总数据量
                int end = Math.min(i + pageSize, total);
                log.debug("sync from {} to {}", i, end);
                userEsMapper.saveAll(userEsList.subList(i, end));
            }

            log.debug("IncSyncUserToEsJob end, total {}, is {}", total, userEsList);
        }
    }
}
