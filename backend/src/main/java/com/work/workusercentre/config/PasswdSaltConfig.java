package com.work.workusercentre.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 加密盐值类
 *
 * @author <a href="https://github.com/xiaogithuboo">limou3434</a>
 */
@Component
@Data
public class PasswdSaltConfig {

    @Value("${spring.application.name}")
    String salt; // 导入项目名称作为盐值, 如果需要安全则可以注释掉注解并且直接赋值

}
