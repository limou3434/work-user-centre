package cn.com.edtechhub.workusercentre.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 应用项目常量
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Configuration
@Component
@Data
@ConfigurationProperties(prefix = "server")
public class ServerConfig {

    /**
     * 项目名称
     */
    String projectName;

    /**
     * 运行地址
     */
    String address;

    /**
     * 运行端口
     */
    String port;

    /**
     * 接口前缀
      */
    private Servlet servlet;
    @Data
    public static class Servlet {
        private String contextPath;
    }
    public String getContextPath() {
        return servlet.getContextPath();
    }

}
