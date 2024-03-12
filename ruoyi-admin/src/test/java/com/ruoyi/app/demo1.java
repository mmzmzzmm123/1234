package com.ruoyi.app;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.Test;
import org.springframework.security.core.parameters.P;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class demo1 {

    private String getFirstEmoji(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        String regex = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find() && matcher.start() == 0) {
            return matcher.group();
        }
        return input.substring(0, 1);
    }

    @Test
    public void test1() {
        String input1 = "Hello World";
        String input2 = "😀😀Hello";
        String input3 = "1😀😀2345";
        String input4 = "\uD83C\uDF44\uD83C\uDF44哈哈😀哈";
        System.out.println(getFirstEmoji(input1)); // false
        System.out.println(getFirstEmoji(input2)); // true
        System.out.println(getFirstEmoji(input3)); // false
        System.out.println(getFirstEmoji(input4)); // true
    }



    @Test
    public void listFile() {
        String directoryPath = "/Users/qinjin/isoft/psychology/D:/ruoyi/uploadPath/backup";

        try {
            // 获取目录下的所有文件
            Path directory = Paths.get(directoryPath);
            java.util.List<Path> files = Files.list(directory).collect(java.util.stream.Collectors.toList());

            // 遍历并打印文件名
            for (Path file : files) {
                if (file.getFileName().toString().contains(".gz")) {
                    System.out.println(file.getFileName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    @Test
    public void mbti() {
        String[] res = { "E", "I", "S", "N", "T", "F", "J", "P", "O"};
        ArrayList<String> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            list.add(res[random.nextInt(9)]);
        }

        HashMap<String, Integer> countMap = new HashMap<>();
        for (String num : list) {
            if (countMap.containsKey(num)) {
                int count = countMap.get(num);
                count++;
                countMap.put(num, count);
            } else {
                countMap.put(num, 1);
            }
        }

        StringBuffer sb = new StringBuffer();
        sb.append(countMap.get("E") > countMap.get("I") ? "E" : "I");
        sb.append(countMap.get("S") > countMap.get("N") ? "S" : "N");
        sb.append(countMap.get("T") > countMap.get("F") ? "T" : "F");
        sb.append(countMap.get("J") > countMap.get("P") ? "J" : "P");
        System.out.println(sb);

//        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
//            System.out.println(entry.getKey() + "出现的次数为：" + entry.getValue());
//        }

//        System.out.println(list);
    }

    @Test
    public void randomTest() {
        Random random = new Random();
        int num = random.nextInt(8) + 1; // 生成0-7之间的随机数
        System.out.println(num);
    }

    @Test
    public void test10() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-20");
        String format = startSdf.format(c.getTime());
        System.out.println(format);
    }

    @Test
    public void test11() {
        int type = 3;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        if (type > 1) {
            calendar.add(Calendar.YEAR, type - 1);
        }

        System.out.println(calendar.getTime());
    }
}