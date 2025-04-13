package cn.com.edtechhub.workusercentre.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 应用项目配置
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Component
@Data
public class ProjectConfig {

    /**
     * 项目名称
     */
    @Value("${spring.application.name}")
    String projectName;

    /**
     * api 前缀
     */
    @Value("${server.servlet.context-path}")
    String apiPrefix;

    /**
     * 运行地址
     */
    @Value("${server.address}")
    String ip;

    /**
     * 运行端口
     */
    @Value("${server.port}")
    String port;

    /**
     * 在线文档地址
     */
    String apiDoscUrl = "/doc.html";

    /**
     * 配置文档地址
     */
    String apiDoscInfoUrl = "/v3/api-docs";

}
