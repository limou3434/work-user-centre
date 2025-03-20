package com.work.workusercentre.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置类
 *
 * @author <a href="https://github.com/xiaogithuboo">limou3434</a>
 */
@Configuration
public class CrossDomainConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns( // TODO: 修改为环境变量
                    "http://localhost:3000", // 开发环境
                    "http://192.168.101.254:80", // 测试环境
                    "http://134.175.86.228:80" // 生产环境
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }

}

// TODO: 改为读取配置文件的形式来配置
