package com.ruoyi.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@RestController
@MapperScan(basePackages = {"com.ruoyi.generator.mapper"})
@SpringBootApplication(scanBasePackages = {"com.ruoyi.generator"}, exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class TestApplication {
    public static void main(String[] args) {
//        System.setProperty("debug", "true");
//        System.setProperty("spring.config.location", "classpath:application.yml");
//        System.setProperty("logging.config", "classpath:logback-spring-local.xml");
//        System.setProperty("spring.profiles.active", "local");
        System.setProperty("server.port", "9080");

        SpringApplication.run(TestApplication.class, args);

//        Desktop.getDesktop().browse(new URI("http://127.0.0.1:8080/"));
    }

    @GetMapping("/pong")
    public String pong() {
        return "pong";
    }
}
