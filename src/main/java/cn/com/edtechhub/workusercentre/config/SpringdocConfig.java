package cn.com.edtechhub.workusercentre.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "springdoc")
public class SpringdocConfig {

    /**
     * Knife4j Ui 在线文档地址
     */
    private String knife4jUi = "/doc.html";

    /**
     * Swagger Ui 在线文档地址
     */
    private SwaggerUi swaggerUi;

    @Data
    public static class SwaggerUi {
        private String path;
    }

    public String getSwaggerUi() {
        return swaggerUi != null ? swaggerUi.getPath() : null;
    }

    /**
     * 配置文档地址
     */
    private ApiDocs apiDocs;

    @Data
    public static class ApiDocs {
        private String path;
    }

    public String getApiDocs() {
        return apiDocs != null ? apiDocs.getPath() : null;
    }
}
