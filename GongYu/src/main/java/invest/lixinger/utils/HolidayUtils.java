package invest.lixinger.utils;


import com.alibaba.fastjson.JSONObject;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
//https://blog.csdn.net/weixin_44299027/article/details/116028963?spm=1001.2101.3001.6650.7&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-7.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-7.pc_relevant_default&utm_relevant_index=11

public class HolidayUtils {

    public static void main(String[] args) throws ParseException {
//        System.out.println(JJR(2022, 4));
//        System.out.println(getJjr(2022, 4));
//        System.out.println(getMonthWekDay(2022, 4));
        Set<String> temps = JJR(2022, 5);
        for (String temp : temps) {
//            if(temp.substring(5,7).equals("05")){
            System.out.println(temp);
//            }
        }
        System.out.println(isBefore3Pm());
    }

    /**
     * 获取周末和节假日
     */
    public static Set<String> JJR(int year, int month) {
        // 获取所有的周末
        Set<String> monthWekDay = getMonthWekDay(year, month);
        // http://timor.tech/api/holiday     api文档地址
        Map jjr = getJjr(year);
        Integer code = (Integer) jjr.get("code");
        if (code != 0) {
            return monthWekDay;
        }
        Map<String, Map<String, Object>> holiday = (Map<String, Map<String, Object>>) jjr.get("holiday");
        Set<String> strings = holiday.keySet();
        for (String str : strings) {
            Map<String, Object> stringObjectMap = holiday.get(str);
            Integer wage = (Integer) stringObjectMap.get("wage");
            String date = (String) stringObjectMap.get("date");
            // 筛选掉补班
            if (wage.equals(1)) {
                monthWekDay.remove(date);
            } else {
                monthWekDay.add(date);
            }
        }
        TreeSet<String> sorttMonthWekDay = new TreeSet<>(((o1, o2) -> o2.compareTo(o1)));
        sorttMonthWekDay.addAll(monthWekDay);
        return sorttMonthWekDay;
    }

    /**
     * 获取节假日不含周末
     */
    private static Map getJjr(int year) {
        String url = "http://timor.tech/api/holiday/year/" + year + "/";
        OkHttpClient client = new OkHttpClient();
        Response response;
        //解密数据
        String rsa = null;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .removeHeader("User-Agent")
                .addHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)"
                ).addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            response = client.newCall(request).execute();
//            System.out.println(response);
            rsa = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(rsa, Map.class);
    }

    /**
     * 获取周末  月从0开始
     */
    public static Set<String> getMonthWekDay(int year, int month) {
        month = month - 1;

        Set<String> dateList = new HashSet<>();
        SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar(year, month, 1);
        Calendar endCalendar = new GregorianCalendar(year, month, 1);
        endCalendar.add(Calendar.MONTH, 1);
        while (true) {
            int weekday = calendar.get(Calendar.DAY_OF_WEEK);
            if (weekday == 1 || weekday == 7) {
                dateList.add(simdf.format(calendar.getTime()));
            }
            calendar.add(Calendar.DATE, 1);
            if (calendar.getTimeInMillis() >= endCalendar.getTimeInMillis()) {
                break;
            }
        }
        return dateList;
    }

    // 是否在下午三点之前，即交易日
    public static boolean isBefore3Pm() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//设置日期格式
        Date nowTime = sdf.parse(sdf.format(new Date()));

        Date beginTime = sdf.parse("00:00");
        Date endTime = sdf.parse("15:00");

        Calendar now = Calendar.getInstance();
        now.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if ((now.after(begin) && now.before(end)) || (now.after(begin) && now.before(end))) {
            return true;
        } else {
            return false;
        }
    }

}

