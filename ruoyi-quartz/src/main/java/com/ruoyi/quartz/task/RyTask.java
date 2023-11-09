package com.ruoyi.quartz.task;

import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

import java.util.ArrayList;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
        System.out.println(StringUtils.format("yuping {}", s));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }
    
    public void ryNoParams2(){
        ArrayList<Object> arr1 = new ArrayList<>();
        ArrayList<Object> arr2 = new ArrayList<>();
        ArrayList<Object> arr3 = new ArrayList<>();
    };
}
