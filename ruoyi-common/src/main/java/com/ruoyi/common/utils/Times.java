package com.ruoyi.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * 线程安全的 时间 操作 工具
 *
 * @author HadLuo
 * @since JDK1.7
 * @history 2020年3月19日 新建
 */
public class Times {

	private static final String STANDDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/***
	 * 确保线程安全
	 */
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(STANDDARD_FORMAT);
		}
	};

	/***
	 * 判断 start 到 end 是否间隔 大于 hours 小时
	 *
	 * @param start
	 * @param end
	 * @param hours
	 * @return
	 * @author HadLuo 2019年3月20日 新建
	 */
	public static boolean isMoreThanHours(Date start, Date end, int hours) {
		long cha = end.getTime() - start.getTime();
		double result = cha * 1.0 / (1000 * 60 * 60);
		if (result <= hours) {
			return false; // 说明小于24小时
		} else {
			return true;
		}
	}

	/***
	 * title: 判断 start 到 end 是否间隔 大于 seconds 秒
	 *
	 * @param start
	 * @param end
	 * @param hours
	 * @return
	 * @author HadLuo 2019年3月20日 新建
	 */
	public static boolean isMoreThanSeconds(Date start, Date end, int seconds) {
		long cha = end.getTime() - start.getTime();
		double result = cha * 1.0 / (1000);
		if (result <= seconds) {
			return false; // 说明小于24小时
		} else {
			return true;
		}
	}

	public static Object convertFormat(Object date) {
		Date temp;
		if (date instanceof Date) {
			temp = (Date) date;
		} else {
			return date;
		}
		return format(temp);
	}

	/***
	 * 格式化时间
	 *
	 * @param date
	 * @return
	 * @author HadLuo 2018年6月15日 新建
	 */
	public static Date parse(String source) {
		try {
			return sdf().parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 格式化时间
	 *
	 * @param date
	 * @return
	 * @author HadLuo 2018年6月15日 新建
	 */
	public static Date parse(String source, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 格式化时间
	 *
	 * @param date
	 * @return
	 * @author HadLuo 2018年6月15日 新建
	 */
	public static String format(Date date) {
		if (date == null) {
			return "";
		}
		return sdf().format(date);
	}

	private static DateFormat sdf() {
		DateFormat sdf = threadLocal.get();
		if (null == sdf) {
			sdf = new SimpleDateFormat(STANDDARD_FORMAT);
			threadLocal.set(sdf);
		}
		return sdf;
	}

	/***
	 * 格式化时间
	 *
	 * @param date
	 * @return
	 * @author HadLuo 2018年6月15日 新建
	 */
	public static String format(Date date, String partten) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(partten).format(date);
	}

	/**
	 * 获取指定日期往前(num<0)或者往后的日期(num>0)
	 *
	 * @param num
	 * @return
	 */
	public static String getDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);

		return sdf().format(calendar.getTime());
	}

	/**
	 * 获取指定日期往前(num<0)或者往后的日期(num>0)
	 *
	 * @param num
	 * @return
	 */
	public static Date getDayReturnDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期往前(num<0)或者往后的日期(num>0)
	 *
	 * @param num
	 * @return
	 */
	public static Date getMin(Date date, int min) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, min);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期往前(num<0)或者往后的日期(num>0)
	 *
	 * @param num
	 * @return
	 */
	public static Date getSecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期往前(hour<0)或者往后的日期(hour>0)
	 *
	 * @param num
	 * @return
	 */
	public static String getHourFormatString(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		return sdf().format(calendar.getTime());
	}

	/**
	 * 获取指定日期往前(hour<0)或者往后的日期(hour>0)
	 *
	 * @param num
	 * @return
	 */
	public static Date getHourFormatDate(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}

	/***
	 * 是否过了当前时间
	 *
	 * @param time
	 * @return true-过了 false-没有
	 * @author HadLuo 2019年3月27日 新建
	 */
	public static boolean passNow(Date time) {
		if (time == null) {
			return false;
		}
		if (time.getTime() <= System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	/**
	 * 获取指定日期 00:00:00
	 * 
	 * @return
	 */
	public static Date getFirstDate(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		return day.getTime();
	}

	/**
	 * 获取指定日期 23:59:59
	 * 
	 * @return
	 */
	public static Date getLastDate(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		day.set(Calendar.HOUR_OF_DAY, 23);
		day.set(Calendar.MINUTE, 59);
		day.set(Calendar.SECOND, 59);
		day.set(Calendar.MILLISECOND, 999);
		return day.getTime();
	}

	/***
	 * 
	 * title: 判断 两个时间的 时分秒 是否当前时间内
	 *
	 * @param start
	 * @param end
	 * @return
	 * @author HadLuo 2020-9-24 11:46:30
	 */
	public static boolean betweenHMS(Date start, Date end) {
		@SuppressWarnings("deprecation")
		long s1 = start.getHours() * 3600 + start.getMinutes() * 60 + start.getSeconds();
		@SuppressWarnings("deprecation")
		long s2 = end.getHours() * 3600 + end.getMinutes() * 60 + end.getSeconds();
		Date now = new Date();
		@SuppressWarnings("deprecation")
		long s3 = now.getHours() * 3600 + now.getMinutes() * 60 + now.getSeconds();
		if (s3 >= s1 && s3 <= s2) {
			return true;
		}
		return false;
	}

	/***
	 * 
	 * title: 判断 两个时间的 时分秒 是否当前时间内
	 *
	 * @param start
	 * @param end
	 * @return
	 * @author HadLuo 2020-12-14 15:11:21
	 */
	public static boolean betweenHMS(String start, String end) {
		String s1 = start, s2 = end;
		if (!s1.contains("-")) {
			s1 = "2020-02-02 " + s1;
		}
		if (!s2.contains("-")) {
			s2 = "2020-02-02 " + s2;
		}
		if (len(s1, ':') == 1) {
			s1 = s1 + ":00";
		}
		if (len(s2, ':') == 1) {
			s2 = s2 + ":00";
		}
		return Times.betweenHMS(Times.parse(s1), Times.parse(s2));
	}

	private static int len(String str, char ch) {
		int c = 0;
		for (int i = 0; i < str.length(); i++) {
			if (ch == str.charAt(i)) {
				c++;
			}
		}
		return c;
	}

	/***
	 * 
	 * title: 获取当前时间到凌晨0点的秒差
	 *
	 * @return
	 * @author HadLuo 2020-9-25 8:38:37
	 */
	public static int getSecondsTobeforedawn() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		// 改成这样就好了
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) ((cal.getTimeInMillis() - System.currentTimeMillis()) / 1000);
	}

	public static long now() {
		return System.currentTimeMillis();
	}

	/***
	 * 
	 * title: 获取 前一天 的 0点时间
	 *
	 * @return
	 * @author HadLuo 2021-3-16 10:34:15
	 */
	public static Date lastDayZero() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		calendar.add(Calendar.DATE, -1);
		// 获取从ES拿取聊天信息的时间
		try {
			return sp.parse(sp.format(calendar.getTime()));
		} catch (ParseException e) {
		}
		return null;
	}

	/***
	 * 
	 * title: 获取今天的0点
	 *
	 * @return
	 * @author HadLuo 2021-3-16 10:34:15
	 */
	public static Date todayZero() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		calendar.add(Calendar.DATE, 0);
		// 获取从ES拿取聊天信息的时间
		try {
			return sp.parse(sp.format(calendar.getTime()));
		} catch (ParseException e) {
		}
		return null;
	}

	/***
	 * 
	 * title: 获取两个时间天 相差的天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 * @author HadLuo 2021-3-21 14:20:54
	 */
	public static long dayDiff(String date1, String date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = sdf.parse(date1);
			Date d2 = sdf.parse(date2);
			return (d2.getTime() - d1.getTime() + 1000000) / (3600 * 24 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/***
	 * 
	 * title: 获取两个时间天 相差的天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 * @author HadLuo 2021-3-21 14:20:54
	 */
	public static long dayDiff(Date d1, Date d2) {
		return (d2.getTime() - d1.getTime() + 1000000) / (3600 * 24 * 1000);
	}

	/***
	 * 
	 * title: 按分钟 分隔时间 段
	 *
	 * @param minuteSegment 分钟
	 * @param dBegin        时间段的起始时间
	 * @param dEnd          时间段的结束时间
	 * @return
	 * @author HadLuo 2021-4-27 10:56:37
	 */
	public static List<Date> splitDate(int minuteSegment, Date dBegin, Date dEnd) {
		Set<Date> listDate = new HashSet<>();
		listDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);
		while (calEnd.after(calBegin)) {
			calBegin.add(Calendar.MINUTE, minuteSegment);
			if (calEnd.after(calBegin))
				listDate.add(calBegin.getTime());
			else
				listDate.add(calEnd.getTime());
		}
		return new ArrayList<>(listDate);
	}

	/***
	 * 
	 * title: 获取 date的小时
	 *
	 * @param time
	 * @return
	 * @author HadLuo 2021-4-27 10:57:50
	 */
	public static int getHour(Date time) {
		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
		c.setTime(time);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static Date getMonth(int month) {
		Date result = null;
		Calendar calendar = Calendar.getInstance();
		// 获取当前日期
		Date currentDate = calendar.getTime();

		for (int i = 0; i < Math.abs(month); i++) {
			// 将日期减去一个月
			if (month < 0) {
				calendar.add(Calendar.MONTH, -1);
			} else {
				calendar.add(Calendar.MONTH, 1);
			}
			// 获取修改后的日期
			result = calendar.getTime();
		}
		// 恢复到当前日期
		calendar.setTime(currentDate);
		if (result == null) {
			return new Date();
		}
		return result;
	}

	public static boolean isToday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date()); // 当前日期

		Calendar dateToCheckCalendar = Calendar.getInstance();
		dateToCheckCalendar.setTime(date); // 待判断的日期

		return calendar.get(Calendar.YEAR) == dateToCheckCalendar.get(Calendar.YEAR)
				&& calendar.get(Calendar.MONTH) == dateToCheckCalendar.get(Calendar.MONTH)
				&& calendar.get(Calendar.DAY_OF_MONTH) == dateToCheckCalendar.get(Calendar.DAY_OF_MONTH);
	}

	public static String date() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return format.format(date);
	}
}