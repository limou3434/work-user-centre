package com.work.workusercentre.config;

import com.work.workusercentre.aop.RequestLogInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域共享配置类
 *
 * @author <a href="https://github.com/xiaogithuboo">limou3434</a>
 */
@Configuration
public class CrossDomainConfig implements WebMvcConfigurer {

    @Resource
    private RequestLogInterceptor requestLogInterceptor;

    /**
     * 拦截所有接口调用
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogInterceptor).addPathPatterns("/**");
    }

    /**
     * 配置跨域共享
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns(
                        "http://127.0.0.1:3000", // 开发环境
                        "http://10.10.174.243:3000", // 测试环境
                        "http://134.175.86.228:3000" // 测试环境
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }


}
