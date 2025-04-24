package cn.com.edtechhub.workusercentre.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class OpenApiConfig {

    /**
     * Knife4j Ui 在线文档地址
     */
    private String knife4jUi = "/doc.html";

    /**
     * Swagger Ui 在线文档地址
     */
    private String swaggerUi = "/swagger-ui/index.html";

    /**
     * 配置文档 json 地址
     */
    private String apiDocsJson = "/v3/api-docs";

}
