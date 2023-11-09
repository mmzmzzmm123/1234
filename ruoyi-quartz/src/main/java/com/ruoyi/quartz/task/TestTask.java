package com.ruoyi.quartz.task;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 *定时任务调度测试
 *
 */


@Component("testTask")
public class TestTask {
    public void testRead(){
        LocalDate now = LocalDate.now();
        System.out.println("测试rouyi定时任务" + now);
    }
}
