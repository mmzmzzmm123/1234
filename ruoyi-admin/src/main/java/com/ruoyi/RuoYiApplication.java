package com.ruoyi;

import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.service.impl.IntoGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.ruoyi.common.config.dymic.EnableRedisConfigure;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.service.OrderService;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableRedisConfigure
@Import(FastJsonHttpMessageConverter.class)
@EnableScheduling
public class RuoYiApplication implements ApplicationRunner
{
    @Autowired
    IntoGroupService intoGroupService;
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  若依启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupId("1211");
        groupInfo.setGroupSerialNo("12121");
        CalledDTO calledDTO = new CalledDTO();
        calledDTO.setOptSerNo("20240119023701516613610300787");
        calledDTO.setResultCode(0);
        intoGroupService.intoGroupCallback(groupInfo,calledDTO);
    }
}
