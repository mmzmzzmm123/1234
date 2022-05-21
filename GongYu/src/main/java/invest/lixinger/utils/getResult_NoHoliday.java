package invest.lixinger.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import static invest.lixinger.utils.HolidayUtils.JJR;


public class getResult_NoHoliday {
    public static void main(String[] args) {
        getResult_NoHoliday(2022, 4);
    }

    public static String getResult_NoHoliday(int year, int month) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Set<String> holidayAndWeekday = JJR(year, month);
        Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
//        calendar.set(2022,4,5);
        for (String temp : holidayAndWeekday) {
            if (df.format(calendar.getTime()).equals(temp)) {
                calendar.add(Calendar.DAY_OF_MONTH, -1); //当前时间减去一天，即一天前的时间
            }
        }

        System.out.println("统计的时间为===" + df.format(calendar.getTime()));
        return df.format(calendar.getTime());
    }
}
