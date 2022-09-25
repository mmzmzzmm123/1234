package GYProject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("GYProject.mybatisTest.mapper")
public class mybatisTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(mybatisTestApplication.class, args);
    }

}


