package invest.lixinger.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import static invest.lixinger.utils.HolidayUtils.JJR;
import static invest.lixinger.utils.HolidayUtils.isBefore3Pm;


public class getResult_NoHoliday {
    public static void main(String[] args) throws ParseException {
        getResult_NoHoliday(2023);
    }

    public static String getResult_NoHoliday(int year) throws ParseException {
        Calendar calendar = Calendar.getInstance();
//        int year=calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Set<String> holidayAndWeekday = JJR(year, month);
//        Calendar calendar=Calendar.getInstance();
        if (isBefore3Pm()) {
            calendar.add(Calendar.DAY_OF_MONTH, -1); //当前时间减去一天，即一天前的时间
        }
        calendar = removeHoliday(calendar, holidayAndWeekday);
        System.out.println("统计的时间为===" + sdf.format(calendar.getTime()));

        return sdf.format(calendar.getTime());
    }

    private static Calendar removeHoliday(Calendar calendar, Set<String> holidayAndWeekday) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (String temp : holidayAndWeekday) {
            if (sdf.format(calendar.getTime()).equals(temp)) {
                calendar.add(Calendar.DAY_OF_MONTH, -1); //当前时间减去一天，即一天前的时间
            }
        }
        return calendar;
    }
}
