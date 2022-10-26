package com.ruoyi.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @User hogan
 * @Time 2022/10/24 17:14
 * @e-mail hkcugwh@163.com
 **/
public class OrderIdUtils {

    /**
     * 时间串 + 随机数
     * @return
     */
    public static String getOrderId(){
        //时间（精确到毫秒）
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String localDate = LocalDateTime.now().format(ofPattern);
        //随机数
        String randomNumeric = RandomStringUtils.randomNumeric(8);
        return new StringBuilder().append(localDate).append(randomNumeric).toString();
    }
}
