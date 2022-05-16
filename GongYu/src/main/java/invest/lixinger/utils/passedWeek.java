package invest.lixinger.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class passedWeek {
    public static void main(String[] args) throws ParseException {
        int year8 = 8;
        int totalWeek = year8 * 52;
        Date date = new Date(System.currentTimeMillis());
        String startDateStr = "2016-1-29";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String endDateStr = formatter.format(date);
        long intervalDay = twoDayInterval(startDateStr, endDateStr);
        long intervalWeek = intervalDay / 7;
        System.out.println("总共的周数为:" + totalWeek);
        System.out.println("已过去的周数为:" + intervalWeek);
        System.out.println("剩余的周数为:" + (totalWeek - intervalWeek));
        System.out.println();
        System.out.println("已过去周数占比" + new DecimalFormat(".00%").format((float) intervalWeek / totalWeek));
        System.out.println("剩余的周数占比" + new DecimalFormat(".00%").format((float) (totalWeek - intervalWeek) / totalWeek));

    }

    public static long twoDayInterval(String start, String end) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(start);
        Date endDate = formatter.parse(end);
        return (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
    }
}
