package cn.com.edtechhub.workusercentre.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 应用项目常量
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "server")
public class ServerConfig {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 运行地址
     */
    private String address;

    /**
     * 运行端口
     */
    private String port;

    /**
     * 接口前缀
     */
    private Servlet servlet;

    @Data
    public static class Servlet {
        private String contextPath;
    }

    public String getContextPath() {
        return servlet != null ? servlet.getContextPath() : null;
    }

}
