package work.cib.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	// 新增1，用于比对String 日期是否符合预定格式
	/**
	 * 声明一个日志记录器
	 */

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 判断是否是对应的格式的日期字符串
	 */
	public static boolean isRightDateStr(String dateStr, String datePattern) {
		DateFormat dateFormat = new SimpleDateFormat(datePattern);
		try {
			// 采用严格的解析方式，防止类似 “2017-05-35” 类型的字符串通过
			dateFormat.setLenient(false);
			dateFormat.parse(dateStr);
			Date date = (Date) dateFormat.parse(dateStr);
			// 重复比对一下，防止类似 “2017-5-15” 类型的字符串通过
			String newDateStr = dateFormat.format(date);
			if (dateStr.equals(newDateStr)) {
				return true;
			} else {
				logger.error("字符串dateStr:{}， 不是严格的 datePattern:{} 格式的字符串", dateStr, datePattern);
				return false;
			}
		} catch (ParseException e) {
			logger.error("format error: 字符串dateStr:{}，不能按照 datePattern:{} 样式转换", dateStr, datePattern);
			return false;
		}
	}

	//

	/**
	 * 自定义
	 */
	public static String now2String(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * yyyy-MM-dd HH:mm:ss (String)
	 */
	public static String now2String1() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * yyyyMMddHHmmss (String)
	 */
	public static String now2String2() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	/**
	 * yyyyMMddHHmmssnnnn (String)
	 */
	public static String now2String3() {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		return time.replace("-", "").replace(" ", "").replace(":", "").replace(".", "");
	}

	/**
	 * yyyy-MM-dd (Date)
	 */
	public static Date nowDate1() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串yyyy-MM-dd HH:mm:ss转Date
	 */
	public static Date string2Date1(String stringDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatter.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date string2Date3(String stringDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return formatter.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字符串yyyy-MM-dd转Date
	 */
	public static Date string2Date2(String stringDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date yyyymmddWithBarToDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date yyyymmddToDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		try {
			return formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Date转字符串(自定义)
	 */
	public static String date2String(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * Date转字符串yyyy-MM-dd
	 */
	public static String date2String1(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String date2yyyymmddWithBar(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String date2yyyymmdd(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * 前后自定义天数日期
	 */
	public static Date getAroundDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	/**
	 * 前后自定义月数日期yyyyMM
	 */
	public static String getAroundMonth(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, day);
		return date2String(calendar.getTime(), "yyyyMM");
	}

	/**
	 * 前后自定义月数最后一天String yyyy-MM-dd
	 */
	public static String getMonthEnd(int m) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + (m + 1));
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		return date2String1(calendar.getTime());
	}

	// 判断是否为工作日
	public static boolean isWorkDay(String nowDate, List<String> holidays, List<String> workDays) {

		Calendar cal = Calendar.getInstance();

		Date flag = DateUtil.string2Date2(nowDate);

		boolean ch = true;// 工作日标记
		cal.setTime(flag);// 当前操作时间
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		// 休息日判断
		if (week == 0 || week == 6) {
			ch = false;
			// 双休日为工作日
			for (String str : holidays) {
				if (DateUtil.string2Date2(str).compareTo(cal.getTime()) == 0) {
					ch = true;
					break;
				}
			}
		} else {
			for (String str : workDays) {
				if (DateUtil.string2Date2(str).compareTo(cal.getTime()) == 0) {
					ch = false;
					break;
				}
			}
		}

		return ch;

	}

	/**
	 * 计算从开始时间至后延天数,出去周日,周六,节假日,工作时间调休应该后延多少天
	 * 
	 * @param startDay          yyyy-MM-dd
	 * @param holidays
	 * @param workDays
	 * @param delayDays:往后延迟多少天
	 */
	 public static String getDays(String startDay,List<String> holidays,List<String> workDays,int delayDays){
	    	//计算后移多少天
	    	int counts = 0;
	    	String stuff = startDay.substring(10);
	    	Calendar cal = Calendar.getInstance();
	    	Date flag = DateUtil.string2Date2(startDay);
	    	String endTime = null;
	    	for(int i=1;i<=delayDays;i++){
	    		boolean ch = true;
	    		cal.setTime(flag);
	    		cal.add(Calendar.DAY_OF_MONTH, +i);
	    		if(i==delayDays) endTime = DateUtil.date2String1(cal.getTime())+stuff;
	    		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
	    		if(week == 0 || week == 6){
	    			//logger.info("周六,周日:{}",date2String(cal.getTime(), "yyyy-MM-dd"));
	    			counts ++;
	    			ch = false;
	    			continue;
	    		}
	    		if(ch){
	    			for(String str : holidays){
	        			if(DateUtil.string2Date2(str).compareTo(cal.getTime()) == 0){
	        				//logger.info("工作日为节假日:{}",date2String(cal.getTime(), "yyyy-MM-dd"));
	        				counts ++;
	        				ch = false;
	        				break;
	        			}
	        		}
	    		}
	    		if(ch){
	    			for(String str : workDays){
	        			if(DateUtil.string2Date2(str).compareTo(cal.getTime()) == 0){
	        				logger.info("节假日为工作日:{}",date2String(cal.getTime(), "yyyy-MM-dd"));
	        				counts ++;
	        				ch = false;
	        				break;
	        			}
	        		}
	    		}
	    		
	    	}
	    	if(counts != 0){
	    		//counts = counts + getDays(endTime, holidays, workDays, counts);
	    		endTime =  getDays(endTime, holidays, workDays, counts);
	    	}
	    	return endTime;
	    }

	/**
	 * 日期加减
	 */
	public static String getCountDay(String nowDay, int num) {
		String endTime = null;
		try {
			SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd");
			Date d = sj.parse(nowDay);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.add(Calendar.DATE, num);
			endTime = sj.format(calendar.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return endTime;
	}

	/**
	 * 从开始时间 往后推移 days个自然日
	 */
	public static String elapseNatureDays(Date startDate, int days) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		start.set(Calendar.DATE, start.get(Calendar.DATE) + days);
		return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 计算开始时间往前 移days 个自然日
	 */
	public static String getDateBefore(Date startDate, int days) {

		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		start.set(Calendar.DATE, start.get(Calendar.DATE) - days);
		return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 获取最近的上一个21号
	 */
	public static String getbefore21(Date startDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		while (start.get(Calendar.DAY_OF_MONTH) != 21) {
			start.set(Calendar.DATE, start.get(Calendar.DATE) - 1);
		}

		return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 返回上一个按季付息日期
	 */
	public static String getBeforeSean21(Date startDate) {
		String[] season_date = { "03-21", "06-21", "09-21", "12-21" };
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		String MM_dd = DateUtil.date2String(startDate, "MM-dd");
		while (!MM_dd.equals(season_date[0]) && !MM_dd.equals(season_date[1]) && !MM_dd.equals(season_date[2])
				&& !MM_dd.equals(season_date[3])) {
			start.set(Calendar.DATE, start.get(Calendar.DATE) - 1);
			MM_dd = DateUtil.date2String(start.getTime(), "MM-dd");
		}

		return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 返回下一个 按季 计息日
	 */
	public static String getAfterSean21(Date startDate) {
		String[] season_date = { "03-21", "06-21", "09-21", "12-21" };
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		String MM_dd = DateUtil.date2String(startDate, "MM-dd");
		while (!MM_dd.equals(season_date[0]) && !MM_dd.equals(season_date[1]) && !MM_dd.equals(season_date[2])
				&& !MM_dd.equals(season_date[3])) {
			start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
			MM_dd = DateUtil.date2String(start.getTime(), "MM-dd");
		}

		return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 返回上一个按半年计息日
	 */
	public static String getBeforeHalfYear21(Date startDate) {
		String[] season_date = { "06-21", "12-21" };
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		String MM_dd = DateUtil.date2String(startDate, "MM-dd");
		while (!MM_dd.equals(season_date[0]) && !MM_dd.equals(season_date[1])) {
			start.set(Calendar.DATE, start.get(Calendar.DATE) - 1);
			MM_dd = DateUtil.date2String(start.getTime(), "MM-dd");
		}

		return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 返回下一个按半年 计息日
	 */
	public static String getAfterHalfYear21(Date startDate) {
		String[] season_date = { "06-21", "12-21" };
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		String MM_dd = DateUtil.date2String(startDate, "MM-dd");
		while (!MM_dd.equals(season_date[0]) && !MM_dd.equals(season_date[1])) {
			start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
			MM_dd = DateUtil.date2String(start.getTime(), "MM-dd");
		}

		return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 返回上一个 年计息日
	 */
	public static String getBeforeYear21(Date startDate) {
		String[] season_date = { "12-21" };
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		String MM_dd = DateUtil.date2String(startDate, "MM-dd");
		while (!MM_dd.equals(season_date[0])) {
			start.set(Calendar.DATE, start.get(Calendar.DATE) - 1);
			MM_dd = DateUtil.date2String(start.getTime(), "MM-dd");
		}

		return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 返回下一个按年计息日
	 */
	public static String getAfterYear21(Date startDate) {
		String[] season_date = { "12-21" };
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		String MM_dd = DateUtil.date2String(startDate, "MM-dd");
		while (!MM_dd.equals(season_date[0])) {
			start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
			MM_dd = DateUtil.date2String(start.getTime(), "MM-dd");
		}

		return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 返回本次满月计息日
	 */
	public static String getNextFullMonthDate(Date startDate, Date nowDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);

		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);

		int start_year = start.get(Calendar.YEAR);
		int start_month = start.get(Calendar.MONTH);

		int now_year = now.get(Calendar.YEAR);
		int now_month = now.get(Calendar.MONTH);

		int monthNum = (now_year - start_year) * 12 + (now_month - start_month);

		if (monthNum == 0) {
			start.add(Calendar.MONTH, 1);
			return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");
		}

		String monthDay_start = DateUtil.date2String(startDate, "dd");

		String monthDay_now = DateUtil.date2String(nowDate, "dd");

		if (monthDay_now.compareTo(monthDay_start) == 1) {
			monthNum += 1;
		}
		start.add(Calendar.MONTH, monthNum);

		return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");

	}

	/**
	 * 返回上一个满月计息周期
	 */
	public static String getUpFullMonthDate(Date startDate, Date nowDate) {
		String nextFullMonthDate = getNextFullMonthDate(startDate, nowDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.string2Date2(nextFullMonthDate));
		cal.add(Calendar.MONTH, -1);
		return DateUtil.date2String(cal.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 返回本次 按季付息 日期
	 * @param cacuType  满季、半年，整年 类型
	 */
	public static String getNextFullSeasonDate(Date startDate, Date nowDate, int cacuType) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);

		Calendar now = Calendar.getInstance();
		now.setTime(nowDate);

		int start_year = start.get(Calendar.YEAR);
		int start_month = start.get(Calendar.MONTH);

		int now_year = now.get(Calendar.YEAR);
		int now_month = now.get(Calendar.MONTH);

		int monthNum = (now_year - start_year) * 12 + (now_month - start_month);

		String monthDay_start = DateUtil.date2String(startDate, "dd");

		String monthDay_now = DateUtil.date2String(nowDate, "dd");

		int seasonTimes = monthNum / cacuType;

		int seasonOver = monthNum % cacuType;

		if (seasonOver != 0) {
			seasonTimes += 1;
		}

		if (seasonOver == 0) {
			if (monthDay_now.compareTo(monthDay_start) == 1) {
				seasonTimes += 1;
			}
		}

		start.add(Calendar.MONTH, seasonTimes * cacuType);

		return DateUtil.date2String(start.getTime(), "yyyy-MM-dd");

	}

	/**
	 * 返回上一个满季付息日
	 */
	public static String getUpFullSeasonDate(Date startDate, Date nowDate, int cacuType) {
		String nextFullSeasonDate = getNextFullSeasonDate(startDate, nowDate, cacuType);
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.string2Date2(nextFullSeasonDate));
		cal.add(Calendar.MONTH, -cacuType);
		String day = DateUtil.date2String(startDate, "dd");
		if ("31".equals(day) && cal.getActualMaximum(Calendar.DATE) == 31) {
			return DateUtil.date2String(cal.getTime(), "yyyy-MM-dd").substring(0, 8) + "31";
		}

		return DateUtil.date2String(cal.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 0 当前季度到期 1 当前后第1季度到期 2 当季后第2季度到期 3 当季后第3季度到期
	 * @param nowDate         今天日期
	 * @param contractEndTime 存入的日期
	 * @param type            合同到期日期
	 */
	public static boolean getContractEndTime(String nowDate, String contractEndTime, String type) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (contractEndTime.indexOf("/") > -1) {
				contractEndTime = contractEndTime.replaceAll("/", "-");
			}
			Date nDate = simpleDateFormat.parse(nowDate);
			Date cDate = simpleDateFormat.parse(contractEndTime);
			if (!StringUtil.isNullValue(type)) {
				if (nDate.getTime() <= cDate.getTime()) {
					Calendar now = Calendar.getInstance();
					now.setTime(nDate);
					int month = now.get(Calendar.MONTH) + 1;
					int year = now.get(Calendar.YEAR);
					String startDate = "";
					String endDate = "";
					if (month == 1 || month == 2 || month == 3) {
						startDate = String.valueOf(year).concat("-01-01");
						endDate = String.valueOf(year).concat("-03-31");
					} else if (month == 4 || month == 5 || month == 6) {
						startDate = String.valueOf(year).concat("-04-01");
						endDate = String.valueOf(year).concat("-06-30");
					} else if (month == 7 || month == 8 || month == 9) {
						startDate = String.valueOf(year).concat("-07-01");
						endDate = String.valueOf(year).concat("-09-30");
					} else if (month == 10 || month == 11 || month == 12) {
						startDate = String.valueOf(year).concat("-10-01");
						endDate = String.valueOf(year).concat("-12-31");
					}
					Date startD = simpleDateFormat.parse(startDate);
					Date endD = simpleDateFormat.parse(endDate);
					Calendar startC = Calendar.getInstance();
					Calendar endC = Calendar.getInstance();
					if ("0".equals(type)) {
						if (cDate.getTime() <= endD.getTime() && cDate.getTime() > startD.getTime()) {
							return true;
						} else {
							return false;
						}
					} else if ("1".equals(type)) {
						startC.setTime(startD);
						startC.add(Calendar.MONTH, 3);
						endC.setTime(endD);
						endC.add(Calendar.MONTH, 3);
						System.out.println(startDate);
						System.out.println(endDate);
						System.out.println(simpleDateFormat.format(startC.getTime()));
						System.out.println(simpleDateFormat.format(endC.getTime()));
						if (cDate.getTime() <= endC.getTime().getTime()
								&& cDate.getTime() >= startC.getTime().getTime()) {
							return true;
						} else {
							return false;
						}
					} else if ("2".equals(type)) {
						startC.setTime(startD);
						startC.add(Calendar.MONTH, 6);
						endC.setTime(endD);
						endC.add(Calendar.MONTH, 6);
						System.out.println(startDate);
						System.out.println(endDate);
						System.out.println(simpleDateFormat.format(startC.getTime()));
						System.out.println(simpleDateFormat.format(endC.getTime()));
						if (cDate.getTime() <= endC.getTime().getTime()
								&& cDate.getTime() >= startC.getTime().getTime()) {
							return true;
						} else {
							return false;
						}
					} else if ("3".equals(type)) {
						startC.setTime(startD);
						startC.add(Calendar.MONTH, 9);
						endC.setTime(endD);
						endC.add(Calendar.MONTH, 9);
						System.out.println(startDate);
						System.out.println(endDate);
						System.out.println(simpleDateFormat.format(startC.getTime()));
						System.out.println(simpleDateFormat.format(endC.getTime()));
						if (cDate.getTime() <= endC.getTime().getTime()
								&& cDate.getTime() >= startC.getTime().getTime()) {
							return true;
						} else {
							return false;
						}
					}
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 获取传入日期的下一季度
	 */
	public static Integer getNextSeason(Date date) {
		Integer[] season = { 1, 2, 3, 4 };

		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		int i = cal.get(Calendar.MONTH) + 1;

		if (i < 4) {
			return season[1];
		}

		if (i < 7) {
			return season[2];
		}

		if (i < 10) {
			return season[3];
		}

		if (i <= 12) {
			return season[0];
		}

		return 0;
	}
	/**
	 * localDateTime 获取时间戳
	 */
	public static long getMills(LocalDateTime localDateTime)
	{
		return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}
	/**
     * 获取当前的时间戳
     */
    public static Timestamp nowTimeStamp()
    {
    	return new Timestamp(new Date().getTime());
    }
	public static void main(String[] args) {
		/*
		 * long start = System.currentTimeMillis();
		 * System.out.println(getBeforeHalfYear21(DateUtil.
		 * string2Date1("2020-06-21 23:23:23")));
		 * System.out.println(getAfterHalfYear21(DateUtil.
		 * string2Date1("2020-01-01 23:23:23"))); long end = System.currentTimeMillis();
		 * System.out.println(end - start);
		 */

		// System.out.println(getNextFullMonthDate(DateUtil.string2Date2("2020-01-31"),
		// DateUtil.string2Date2("2020-02-27")));

		// System.out.println(getUpFullMonthDate(DateUtil.string2Date2("2019-02-28"),
		// DateUtil.string2Date2("2020-02-27")));
		// System.out.println(getNextFullSeasonDate(DateUtil.string2Date2("2020-01-31"),
		// DateUtil.string2Date2("2027-08-01"),6));
		// System.out.println(getContractEndTime("2020-10-14","2021-04-14","2"));
		// System.out.println(getUpFullSeasonDate(DateUtil.string2Date2("2020-01-31"),
		// DateUtil.string2Date2("2027-08-01"),6));
		/*
		 * Calendar cal = Calendar.getInstance();
		 * cal.setTime(DateUtil.string2Date2("2020-10-31"));
		 * System.out.println(cal.getActualMaximum(Calendar.DATE));
		 * cal.add(Calendar.MONTH, -3);
		 * System.out.println(DateUtil.date2String(cal.getTime(), "yyyy-MM-dd"));
		 */
		System.out.println(getNextSeason(DateUtil.string2Date3("2019-12-12 22:11")));

	}
}
