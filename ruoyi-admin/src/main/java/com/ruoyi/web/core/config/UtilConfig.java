package com.ruoyi.web.core.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Configuration
public class UtilConfig {

    /*获取当前时间方法，年月日*/
    public Date GetDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        try {
            return sdf.parse(format);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void Test(){
        System.out.println(GetDate());
    }
}
