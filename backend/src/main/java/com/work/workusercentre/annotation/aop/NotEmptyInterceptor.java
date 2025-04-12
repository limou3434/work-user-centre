package com.work.workusercentre.annotation.aop;

import com.work.workusercentre.annotation.NotEmptyCheck; // ← 正确引入你自己定义的注解
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class NotEmptyInterceptor {

    @Around("@annotation(notEmptyCheck)")
    public Object validateNotEmpty(ProceedingJoinPoint joinPoint, NotEmptyCheck notEmptyCheck) throws Throwable {

        // 获取注解中的参数
        String[] paramNames = notEmptyCheck.value();
        Object[] args = joinPoint.getArgs();

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            String paramName = parameters[i].getName();
            Object value = args[i];

            if (Arrays.asList(paramNames).contains(paramName)) {
                if (value == null || isEmptyObject(value)) {
                    throw new IllegalArgumentException("参数 [" + paramName + "] 为空");
                }
            }
        }

        return joinPoint.proceed();
    }

    private boolean isEmptyObject(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String) return ((String) obj).trim().isEmpty();
        if (obj instanceof Collection) return ((Collection<?>) obj).isEmpty();
        if (obj instanceof Map) return ((Map<?, ?>) obj).isEmpty();
        return false;
    }
}
