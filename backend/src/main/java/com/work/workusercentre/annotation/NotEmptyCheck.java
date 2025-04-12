package com.work.workusercentre.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 简单校验注解声明
 * 保证某些参数不能为空, 可以传递多个参数列表
 */
@Target(ElementType.METHOD) // 注解用于方法
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时可以通过反射获取到
public @interface NotEmptyCheck {

    /**
     * 需要检查的参数
     */
    String[] value(); // 要校验的参数名列表

}
