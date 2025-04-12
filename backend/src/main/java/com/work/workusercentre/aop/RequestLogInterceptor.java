package com.work.workusercentre.aop;

import com.work.workusercentre.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class RequestLogInterceptor implements HandlerInterceptor {

    /**
     * 每次接口调用都会执行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("拦截到请求: {}", "来自 " + IpUtils.getIpAddress(request) + " - " + request.getMethod() + " " + request.getRequestURI());

        return true; // 返回 false 会终止请求

    }

}