package work.cib.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 有较详细的例子，可以优先看
// https://blog.csdn.net/qq_30034925/article/details/70216525?spm=1001.2101.3001.6650.16&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-16.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-16.pc_relevant_default&utm_relevant_index=19
// 本代码地址
// https://ershi.blog.csdn.net/article/details/88974940?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2.pc_relevant_default&utm_relevant_index=5
// 正则表达式
// https://blog.csdn.net/zpz2411232428/article/details/83549502?spm=1001.2101.3001.6650.8&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-8.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-8.pc_relevant_default&utm_relevant_index=11
// https://blog.csdn.net/ymaini/article/details/82263203
public class test1 {
    @Test
    public void test1() {
        // +:重复一次或更多次
        // d:匹配数字
        Pattern p = Pattern.compile("\\d+");
        String[] str = p.split("我的QQ是:456456我的电话是:0532214我的邮箱是:aaa@aaa.com");
        System.out.println(str[0]);
        System.out.println(str[1]);
        // -----------------------
        // 返回 true
        System.out.println(Pattern.matches("\\d+", "2223"));
        // 返回 false, 需要匹配到所有字符串才能返回true,这里aa不能匹配到
        System.out.println(Pattern.matches("\\d+", "2223aa"));
        // 返回 false, 需要匹配到所有字符串才能返回true,这里bb不能匹配到
        System.out.println(Pattern.matches("\\d+", "22bb23"));
    }

    /**
     * Matcher.matches()/ Matcher.lookingAt()/ Matcher.find()
     */
    @Test
    public void test2() {
        // matches()对整个字符串进行匹配,只有整个字符串都匹配了才返回true-------------------------------------
        Pattern p1 = Pattern.compile("\\d+");
        Matcher m1 = p1.matcher("22bb23");
        // 返回false,因为bb不能被\d+匹配,导致整个字符串匹配未成功.
        m1.matches();
        Matcher m2 = p1.matcher("2223");
        // 返回true,因为\d+匹配到了整个字符串
        m2.matches();

        // lookingAt()对前面的字符串进行匹配,只有匹配到的字符串在最前面才返回true-------------------------------
        Pattern p2 = Pattern.compile("\\d+");
        Matcher m3 = p2.matcher("22bb23");
        // 返回true,因为\d+匹配到了前面的22
        m3.lookingAt();
        Matcher m4 = p2.matcher("aa2223");
        // 返回false,因为\d+不能匹配前面的aa
        m4.lookingAt();

        // find()对字符串进行匹配,匹配到的字符串可以在任何位置.----------------------
        Pattern p3 = Pattern.compile("\\d+");
        Matcher m5 = p3.matcher("22bb23");
        // 返回true
        m5.find();
        Matcher m6 = p3.matcher("aa2223");
        // 返回true
        m6.find();
        Matcher m7 = p3.matcher("aa2223bb");
        // 返回true
        m7.find();
        Matcher m8 = p3.matcher("aabb");
        // 返回false
        m8.find();
    }

    /**
     * start()返回匹配到的子字符串在字符串中的索引位置.
     * end()返回匹配到的子字符串的最后一个字符在字符串中的索引位置.
     * group()返回匹配到的子字符串
     */
    @Test
    public void test3() {
        Pattern p1 = Pattern.compile("\\d+");
        Matcher m1 = p1.matcher("aaa2223bb");
        //匹配2223
        m1.find();
        //返回3
        m1.start();
        //返回7,返回的是2223后的索引号
        m1.end();
        //返回2223
        m1.group();
        //--------------------------------------------------------------------
        Pattern p2 = Pattern.compile("\\d+");
        Matcher m2 = p2.matcher("2223bb");
        //匹配2223
        m2.lookingAt();
        //返回0,由于lookingAt()只能匹配前面的字符串,所以当使用lookingAt()匹配时,start()方法总是返回0
        m2.start();
        //返回4
        m2.end();
        //返回2223
        m2.group();
        //--------------------------------------------------------------------
        Pattern p3 = Pattern.compile("\\d+");
        Matcher m3 = p3.matcher("2223bb");
        // 匹配整个字符串
        m3.matches();
        // 返回0,原因相信大家也清楚了
        m3.start();
        //返回6,原因相信大家也清楚了,因为matches()需要匹配所有字符串
        m3.end();
        //返回2223bb
        m3.group();
        //start(int i),end(int i),group(int i)专用于分组操作, Mathcer类还有一个groupCount()用于返回有多少组.-----------------------
        Pattern p4 = Pattern.compile("([a-z]+)(\\d+)");
        Matcher m4 = p4.matcher("aaa2223bb");
        //匹配aaa2223
        m4.find();
        //返回2,因为有2组
        m4.groupCount();
        //返回0 返回第一组匹配到的子字符串在字符串中的索引号
        m4.start(1);
        //返回3
        m4.start(2);
        //返回3 返回第一组匹配到的子字符串的最后一个字符在字符串中的索引位置.
        m4.end(1);
        //返回7
        m4.end(2);
        //返回aaa,返回第一组匹配到的子字符串
        m4.group(1);
        //返回2223,返回第二组匹配到的子字符串
        m4.group(2);
    }

    // \d表示[0—9]
    // \D表示[^0—9]
    // \w表示[0—9A—Z_a—z]
    // \W表示[^0—9A—Z_a—z]
    // \s表示[\t\n\r\f]
    // \S表示[^\t\n\r\f]
    @Test
    public void test4() {
        String age = "18-24";//定义年龄范围
        String regex = "-";
        String[] strArr = age.split(regex);//分割成字符串数组

        int startAge = Integer.parseInt(strArr[0]);
        int endAge = Integer.parseInt(strArr[1]);

        int a = 20;
        if (a >= startAge && a <= endAge) {
            System.out.println("你就是我想找的");
        } else {
            System.out.println("滚");
        }

    }

    @Test
    public void test5() {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("我的QQ是:456456 我的电话是:0532214 我的邮箱是:aaa123@aaa.com");
        while (m.find()) {
            System.out.println(m.group());
            System.out.print("start:" + m.start());
            System.out.println(" end:" + m.end());
            System.out.println("-----------------");
        }
    }
    //贪婪模式
    @Test
    public void test6(){
        String str="aa<div>test1</div>bb<div>test2</div>cc";
        Pattern p1=Pattern.compile("<div>.*</div>");
        Matcher m1 = p1.matcher(str);
        Pattern p2=Pattern.compile("<div>.*?</div>");
        Matcher m2 = p2.matcher(str);
        m1.find();
        m2.find();
        System.out.println(m1.group());
        System.out.println(m2.group());
    }

}
