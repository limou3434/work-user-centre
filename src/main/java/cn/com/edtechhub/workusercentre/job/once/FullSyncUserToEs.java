package cn.com.edtechhub.workusercentre.job.once;

import cn.com.edtechhub.workusercentre.mapper.UserEsMapper;
import cn.com.edtechhub.workusercentre.model.entity.User;
import cn.com.edtechhub.workusercentre.model.entity.UserEs;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全量同步用户到 ES
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Component // 注释这个则关闭全量同步
@Slf4j
public class FullSyncUserToEs {

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
    @PostConstruct // 在 Spring 容器初始化后时执行一次
    public void run() {
        // 全量获取题目, 数据量不大的情况下使用
        String sql = "SELECT * FROM user";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class)); // 使用原生 JDBC 绕过逻辑删除避免无法同部到 ES

        log.debug("asdkljuugasd {}", userList);

        if (CollUtil.isEmpty(userList)) {
            return;
        }

        // 转为 ES 实体类
        List<UserEs> userEsList = userList.stream()
                .map(UserEs::EntityToMapping)
                .collect(Collectors.toList());

        // 分页批量插入到 ES
        final int pageSize = 10;
        int total = userEsList.size();

        log.debug("FullSyncUserToEs start, total {}", total);

        for (int i = 0; i < total; i += pageSize) {
            // 注意同步的数据下标不能超过总数据量
            int end = Math.min(i + pageSize, total);
            log.debug("sync from {} to {}", i, end);
            userEsMapper.saveAll(userEsList.subList(i, end));
        }

        log.debug("FullSyncUserToEs end, total {}, is {}", total, userEsList);
    }

}
