package study.Demo.javaBASE;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
//https://huanglei.blog.csdn.net/article/details/100590012
public class _18Time {
    public static void main(String[]Args){
        //获取时间---------------------------
        // 获取日期
        LocalDate localDate = LocalDate.now();
        System.out.println("localDate="+localDate); //2021-06-14
        // 获取时间
        LocalTime localTime = LocalTime.now();
        System.out.println("localTime="+localTime); //14:26:31.093083100
        // 获取日期和时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime="+localDateTime) ;//2021-06-14T14:26:31.093083100

        //获取时间戳-------------------------------------
        long milli = Instant.now().toEpochMilli(); // 获取当前时间戳（精确到毫秒）
        long second = Instant.now().getEpochSecond(); // 获取当前时间戳（精确到秒）
        System.out.println("milli="+milli);  // output:1565932435792
        System.out.println("second="+second); // output:1565932435

        //时间格式化-----------------------------------
        // 时间格式化①
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeFormat = dateTimeFormatter.format(LocalDateTime.now());
        System.out.println(timeFormat);  // output:2019-09-07 09:18:27
        // 时间格式化②
        String timeFormat2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(timeFormat2);    // output:2019-09-07 09:19:12

        //时间转换----------------------------------
        String timeStr = "2019-10-10 06:06:06";
        LocalDateTime dateTime = LocalDateTime.parse(timeStr,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dateTime);  //2019-10-10T06:06:06

        //获得昨天此刻时间----------------------------------------
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.plusDays(-1);
        System.out.println(yesterday);  //2021-06-13T14:28:56.085123700 ，现在是2021.6.14
    }
}
