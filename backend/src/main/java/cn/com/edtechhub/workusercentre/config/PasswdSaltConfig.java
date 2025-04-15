package cn.com.edtechhub.workusercentre.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 加密盐值配置
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Component
@Data
public class PasswdSaltConfig {

    /**
     * 默认读取项目名称作为盐值, 如果需要安全则可以注释掉注解并且直接赋值
     */
    @Value("${spring.salt}")
    String salt;

}
