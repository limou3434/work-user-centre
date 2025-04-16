package cn.com.edtechhub.workusercentre;

import cn.com.edtechhub.workusercentre.config.SpringdocConfig;
import cn.dev33.satoken.SaManager;
import cn.com.edtechhub.workusercentre.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 启动类
 * 1. 在 controller 中定义需要哪些接口
 * 2. 在 sql 中编写对应数据库文件并且设置假数据, 运行插入数据
 * 3. 使用 MyBatisPlus 导入 server、serverImpl、mapper, 然后编写简单的 CUDA 代码
 * 4. 在 serverImpl 中: 需要做 "复杂校验、业务处理、数据返回"
 *    (1)尽可能不要使用 Request 作为参数, 但是增删改查服务除外, 因为这么做比较方便
 *    (2)同时业务中出错只需抛出异常 BusinessException(CodeBindMessage.xxxxx, "更加详细的错误说明") 就可以让控制层响应异常给前端并且避免直接处理报文, 如果发现 CodeBindMessage 枚举不够使用可以自己定义
 *    (3)其他框架组件出错抛出的异常可以在全局异常拦截器 GlobalExceptionHandler 中捕获处理, 以达到自动让控制层响应异常给前端, 并且组件抛异常可以和本业务服务进行隔离
 * 5. 在 controller 中: 需要做 "用户认证、调用服务、报文处理、数据脱敏"
 *    (1)接口名和路由名保持强一致(在服务中调用其他服务即可)
 *    (2)使用 "/// xxx model ///" 进行模块名注释
 *    (3)默认只返回 200, 某些特殊的错误交给前端响应, 比如 403、404 以及对应的页面, 详细错误 code-message 在响应 JSON 中体现
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@SpringBootApplication
@EnableDubbo
@Slf4j
public class WorkUserCentreApplication {

    public static void main(String[] args) {

        var context = SpringApplication.run(WorkUserCentreApplication.class, args);
        log.debug("Spring Boot 启动成功");
        ServerConfig serverConfig = context.getBean(ServerConfig.class);
        SpringdocConfig springdocConfig = context.getBean(SpringdocConfig.class);

        String baseUrl = "http://" + serverConfig.getAddress() + ":" + serverConfig.getPort() + serverConfig.getContextPath();
        log.debug(
                "OpenAPI 启动成功: 访问 {} 即可得到在线文档, 访问 {} 即可得到文档配置",
                baseUrl + serverConfig.getContextPath(),
                baseUrl + springdocConfig.getApiDoscInfoUrl()
        );

        log.debug(
                "Sa-Token 启动成功: {}",
                SaManager.getConfig()
        );

    }

}
