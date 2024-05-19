package com.onethinker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author yangyouqi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = {"com.onethinker", "com.onethinker"})
public class OtAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(OtAdminApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  OneThinker项目启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
