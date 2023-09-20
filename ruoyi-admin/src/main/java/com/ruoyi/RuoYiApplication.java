package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({ "com.ruoyi.*" })
public class RuoYiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("          ██    \n" +
                "         ░██    \n" +
                "  ██████ ░██  ██\n" +
                " ██░░░░██░██ ██ \n" +
                "░██   ░██░████  \n" +
                "░██   ░██░██░██ \n" +
                "░░██████ ░██░░██\n" +
                " ░░░░░░  ░░  ░░ ");
    }
}
