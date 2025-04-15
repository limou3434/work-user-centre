package cn.com.edtechhub.workusercentre.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Data
@ConfigurationProperties(prefix = "springdoc")
public class SpringdocConfig {

    /**
     * 在线文档地址
     */
    String apiDoscUrl = "/doc.html";

    /**
     * 配置文档地址
     */
    String apiDoscInfoUrl = "/v3/api-docs";

}
