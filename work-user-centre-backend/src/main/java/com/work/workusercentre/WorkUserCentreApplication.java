// ./src/main/java/com/work/workusercentre/WorkUserCentreApplication.java: 启动文件
package com.work.workusercentre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 启动类
 *
 * @author ljp
 */
@SpringBootApplication
@MapperScan("com.work.workusercentre.mapper") // 启用 MyBatisPlus 扫描 ./src/Mapper/ 中的映射
public class WorkUserCentreApplication {
    public static void main(String[] args) {
        /* var context = */ SpringApplication.run(WorkUserCentreApplication.class, args);
    }
}
