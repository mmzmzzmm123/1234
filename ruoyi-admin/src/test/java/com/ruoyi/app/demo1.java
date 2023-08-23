package com.ruoyi.app;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.Test;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class demo1 {

    @Test
    public void t1() {
        String s = "[[\"情绪压力\",\"焦虑\"],[\"情绪压力\",\"易怒\"],[\"情绪压力\",\"存在感缺失\"],[\"情绪压力\",\"抗压能力弱\"],[\"情绪压力\",\"抑郁\"],[\"亲子关系\",\"单亲\"],[\"亲子关系\",\"成长\"],[\"亲子关系\",\"原生家庭关系\"],[\"亲子关系\",\"亲子冲突\"],[\"亲子关系\",\"育儿\"]]";
        List<String> jsonArray = JSON.parseArray(s, String.class);
        HashSet<String> tab = new HashSet<>();
        HashSet<String> way = new HashSet<>();
        jsonArray.forEach(a -> {
            List<String> json = JSON.parseArray(a, String.class);
            tab.add(json.get(0));
            way.add(json.get(1));
//            b.forEach(i -> System.out.println(i));
        });
        System.out.println(tab);
        System.out.println(way.toString());

//        for (Object obj : jsonArray) {
//            JSONArray obj1 = (JSONArray) obj;
//            for (Object obj2 : obj1) {
//                System.out.println(obj2);
//            }
//        }
//        System.out.println(jsonArray);
    }

    @Test
    public void t2() {
        String str = "原生家庭关系,育儿,易怒,成长,焦虑,单亲,抗压能力弱,亲子冲突,存在感缺失,抑郁";
        String str2 = "1";
        Set<String> set = Stream.of(str.trim().split(",")).collect(Collectors.toSet());
        Set<String> set2 = Stream.of(str2.trim().split(",")).collect(Collectors.toSet());
        System.out.println("HashSet contains: " + set);
        System.out.println("HashSet contains: " + set2);
    }

    @Test
    public void t3() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -21);
        calendar.add(Calendar.MONTH, 6);
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.MONTH));
    }

    @Test
    public void t4() {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for (int i = 1; i <= 7; i++) {
            int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0) {
                System.out.println(weekDays[0]);
            } else {
                System.out.println(weekDays[w]);
            }
            calendar.add(Calendar.DATE, 1);
            System.out.println(calendar.get(Calendar.DATE));
            System.out.println(calendar.get(Calendar.MONTH));
        }
    }

    @Test
    public void validatePassword() {
        String password = "YJYxl@2023!";
        Pattern pattern = Pattern.compile("^.*(?=.{8,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$");
        Matcher matcher = pattern.matcher(password);
        System.out.println(matcher.matches());
    }
}
