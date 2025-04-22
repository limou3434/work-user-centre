package cn.com.edtechhub.workusercentre.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 应用项目常量
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Configuration
@Data
public class ServerConfig {

    /**
     * 项目名称
     */
    @Value("${spring.application.name}")
    private String projectName;

    /**
     * 运行地址
     */
    @Value("${server.address}")
    private String address;

    /**
     * 运行端口
     */
    @Value("${server.port}")
    private String port;

    /**
     * 接口前缀
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;

}
