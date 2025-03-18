// ./src/main/java/com/work/workusercentre/universal/annotation/AuthCheck.java
package com.work.workusercentre.universal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验注解
 *
 * @author <a href="https://github.com/xiaogithuboo">limou3434</a>
 */
@Target(ElementType.METHOD) // 注解用于方法
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时可以通过反射获取到
public @interface AuthCheck {

    /**
     * 必须有某个角色
     *
     * @return 角色
     */
    String mustRole() default "";

}
