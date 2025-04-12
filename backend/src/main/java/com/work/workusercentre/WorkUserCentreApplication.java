package com.work.workusercentre;

import cn.dev33.satoken.SaManager;
import com.work.workusercentre.config.ProjectConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 启动类
 *
 * @author <a href="https://github.com/xiaogithuboo">limou3434</a>
 */
@SpringBootApplication
@MapperScan("com.work.workusercentre.mapper") // 启用 MyBatisPlus 扫描 ./src/Mapper/ 中的映射
@Slf4j
public class WorkUserCentreApplication {

    public static void main(String[] args) {

        var context = SpringApplication.run(WorkUserCentreApplication.class, args);
        log.debug("本项目启动成功");

        ProjectConfig projectConfig = context.getBean(ProjectConfig.class);
        String baseUrl = "http://" + projectConfig.getIp() + ":" + projectConfig.getPort() + projectConfig.getApiPrefix();
        log.debug(
                "OpenAPI 配置: 访问 {} 即可得到在线文档, 访问 {} 即可得到文档配置",
                baseUrl + projectConfig.getApiDoscUrl(),
                baseUrl + projectConfig.getApiDoscInfoUrl()
        );

        log.debug(
                "Sa-Token 配置: {}",
                SaManager.getConfig()
        );

    }

}
