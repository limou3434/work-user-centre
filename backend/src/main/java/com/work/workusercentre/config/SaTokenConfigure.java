package com.work.workusercentre.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-token 配置类
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Configuration
@Slf4j
public class SaTokenConfigure implements WebMvcConfigurer {

    /**
     * 重写此方法以注册 Sa-Token 拦截器, 打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.debug("查询一次通过 Sa-token 的拦截器的记录");
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**"); // 如果接口较多可以参考使用拦截器把接口结合通配符进行登录校验拦截, 并且排除部分无需校验登录的接口
    }

}
