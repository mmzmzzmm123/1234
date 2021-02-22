package com.gox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author gox
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
//@EnableCaching
public class GoxApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(GoxApplication.class, args);
    }
}
