package com.ruoyi.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@RestController
@MapperScan(basePackages = { "com.ruoyi.generator.mapper", "com.slabbridge.core.dao" })
@SpringBootApplication(scanBasePackages = { "com.ruoyi.generator",
                                            "com.slabbridge.core" }, exclude = { org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class })
public class TestApplication {

    public static void main(String[] args) {
        // System.setProperty("debug", "true");
        // System.setProperty("spring.config.location", "classpath:application.yml");
        // System.setProperty("logging.config", "classpath:logback-spring-local.xml");
        // System.setProperty("spring.profiles.active", "local");
        System.setProperty("server.port", "9080");

        ConfigurableApplicationContext ctx = SpringApplication.run(TestApplication.class, args);
        // for (String name : ctx.getBeanDefinitionNames()) {
        // System.out.println("==============" + name);
        // }

        // Desktop.getDesktop().browse(new URI("http://127.0.0.1:8080/"));
    }

    @GetMapping("/pong")
    public String pong() {
        return "pong";
    }

}
