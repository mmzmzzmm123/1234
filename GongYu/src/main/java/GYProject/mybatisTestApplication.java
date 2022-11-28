package GYProject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableScheduling

//@MapperScan("GYProject.mybatisTest.mapper")
public class mybatisTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(mybatisTestApplication.class, args);
    }

}


