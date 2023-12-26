package com.ruoyi.common.utils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/****
 * Object对象工具
 * 
 * @author HadLuo
 * @since JDK1.7
 * @history 2018年8月8日 新建
 */
public final class Objects {

	@FunctionalInterface
	public static interface NotNullDone<T> {
		public void done(T resource);
	}

	@FunctionalInterface
	public static interface NullDone {
		public void done();
	}

	/***
	 * 判断多个str是否为空 （有一个为空就返回true）
	 * 
	 * @param strList
	 * @return
	 */
	public static boolean isEmptys(String... strList) {
		if (strList == null) {
			return true;
		}
		for (String str : strList) {
			if (StringUtils.isEmpty(str)) {
				return true;
			}
		}
		return false;
	}

	/***
	 * 判断多个集合是否为空 （有一个集合为空就返回true）
	 * 
	 * @param strList
	 * @return
	 */
	public static boolean isEmptys(Collection<?>... strList) {
		if (strList == null) {
			return true;
		}
		for (Collection<?> str : strList) {
			if (CollectionUtils.isEmpty(str)) {
				return true;
			}
		}
		return false;
	}

	/***
	 * 
	 * title: 对象不为空， 做的事情
	 *
	 * @param obj
	 * @param done
	 * @author HadLuo 2021-6-10 11:15:45
	 * @param <T>
	 */
	public static <T> void notNullDone(T obj, NotNullDone<T> done) {
		if (obj == null) {
			return;
		}
		if (obj instanceof String) {
			if (StringUtils.isEmpty(obj)) {
				return;
			}
			done.done(obj);
			return;
		}
		if (obj instanceof Collection) {
			if (CollectionUtils.isEmpty((Collection<?>) obj)) {
				return;
			}
			done.done(obj);
			return;
		}

		if (obj instanceof Map<?, ?>) {
			if (((Map<?, ?>) obj).size() == 0) {
				return;
			}
			done.done(obj);
			return;
		}
		// 数组
		try {
			if (Arrays.asList(obj).isEmpty()) {
				return;
			}
			done.done(obj);
			return;
		} catch (Exception e) {
		}
		// 不知道的类型
		done.done(obj);
	}

	/***
	 * 
	 * title: 对象为空， 做的事情
	 *
	 * @param obj
	 * @param done
	 * @author HadLuo 2021-6-10 11:15:45
	 */
	public static void nullDone(Object obj, NullDone done) {
		if (obj == null) {
			done.done();
			return;
		}
		if (obj instanceof String) {
			if (StringUtils.isEmpty(obj)) {
				done.done();
				return;
			}
			return;
		}
		if (obj instanceof Collection) {
			if (CollectionUtils.isEmpty((Collection<?>) obj)) {
				done.done();
				return;
			}
			return;
		}

		if (obj instanceof Map<?, ?>) {
			if (((Map<?, ?>) obj).size() == 0) {
				done.done();
				return;
			}
			return;
		}
		// 数组
		try {
			if (Arrays.asList(obj).isEmpty()) {
				done.done();
				return;
			}
		} catch (Exception e) {
		}
	}

	public static <T> void equalsDone(T obj1, T obj2, NullDone done) {
		if (obj1 == null || obj2 == null) {
			if (obj1 == obj2) {
				done.done();
				return;
			}
			return;
		}
		if (obj1 == obj2) {
			done.done();
			return;
		}
		if (obj1.equals(obj2)) {
			done.done();
			return;
		}
	}

	private static boolean isMoreThenTwo(String val) {
		if (val.contains(".")) {
			val = val.substring(val.indexOf(".") + 1);
			if (val.length() > 2) {
				return true;
			}
		}
		return false;
	}

	/***
	 * 四舍五入
	 * 
	 * @param number
	 * @return
	 * @author HadLuo 2019年4月9日 新建
	 */
	public static double roundOff2(Number number) {
		if (number == null) {
			return 0d;
		}
		// 判断是否是 2位小数,是的话不处理
		DecimalFormat df1 = new DecimalFormat("0.00");
		if (number instanceof BigDecimal) {
			if (isMoreThenTwo(((BigDecimal) number).doubleValue() + "")) {
				// 不止二位小数 ， 四舍五入
				return ((BigDecimal) number).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
			} else {
				return ((BigDecimal) number).doubleValue();
			}
		} else {
			if (number instanceof Double) {
				if (isMoreThenTwo(((Double) number).doubleValue() + "")) {
					// 不止二位小数 ， 四舍五入
					String str = df1.format(new BigDecimal(number.doubleValue()));
					return Double.parseDouble(str);
				} else {
					return ((Double) number);
				}
			} else if (number instanceof Float) {
				if (isMoreThenTwo(((Float) number).doubleValue() + "")) {
					// 不止二位小数 ， 四舍五入
					String str = df1.format(new BigDecimal(number.floatValue()));
					return Double.parseDouble(str);
				} else {
					return ((Float) number).doubleValue();
				}
			} else {
				String str = df1.format(new BigDecimal(number.intValue()));
				return Double.parseDouble(str);
			}
		}
	}

	public static boolean isNullOrZero(Integer val) {
		if (val == null || val == 0) {
			return true;
		}
		return false;
	}

	/***
	 * 对象为null ， 设置一个 默认值
	 * 
	 * @param targetVal
	 * @param defaultVal
	 * @return
	 * @author HadLuo 2018年8月8日 新建
	 */
	public static <T> T wrapNull(T targetVal, T defaultVal) {
		if (targetVal == null) {
			return defaultVal;
		}
		if (targetVal instanceof String && StringUtils.isEmpty(targetVal)) {
			return defaultVal;
		}
		return targetVal;
	}

	public static String wrapNull(String targetVal) {
		return wrapNull(targetVal, "");
	}

	/***
	 * 判断integer为空， 设置默认0
	 * 
	 * @param targetVal
	 * @return
	 * @author HadLuo 2018年8月8日 新建
	 */
	public static Integer wrapNull(Integer targetVal) {
		return wrapNull(targetVal, 0);
	}

	/***
	 * 判断double为空， 设置默认0
	 * 
	 * @param targetVal
	 * @return
	 * @author HadLuo 2018年8月8日 新建
	 */
	public static Double wrapNull(Double targetVal) {
		return wrapNull(targetVal, 0d);
	}

	/***
	 * 根据条件过滤 集合 中的 数据
	 * 
	 * @param src
	 * @param filter
	 * @author HadLuo 2018年9月18日 新建
	 */
	public static <T> void filter(Collection<T> src, Filter<T> filter) {
		if (filter == null || CollectionUtils.isEmpty(src)) {
			return;
		}
		List<T> temp = new ArrayList<T>();
		for (T data : src) {
			if (filter.doFilter(data)) {
				temp.add(data);
			}
		}
		for (T remove : temp) {
			src.remove(remove);
		}
	}

	public static interface Filter<T> {
		public boolean doFilter(T cur);
	}

	public static void gc(List<?> list) {
		if (list != null) {
			list.clear();
		}
	}

	private final static String[] hexArray = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/***
	 * 对指定的字符串进行MD5加密
	 */
	public static String md5(String originString) {
		try {
			// 创建具有MD5算法的信息摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
			byte[] bytes = md.digest(originString.getBytes());
			// 将得到的字节数组变成字符串返回
			String s = byteArrayToHex(bytes);
			return s.toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字节数组转换成十六进制，并以字符串的形式返回 128位是指二进制位。二进制太长，所以一般都改写成16进制，
	 * 每一位16进制数可以代替4位二进制数，所以128位二进制数写成16进制就变成了128/4=32位。
	 */
	private static String byteArrayToHex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(byteToHex(b[i]));
		}
		return sb.toString();
	}

	/**
	 * 将一个字节转换成十六进制，并以字符串的形式返回
	 */
	public static String byteToHex(byte b) {
		int n = b;
		if (n < 0)
			n = n + 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexArray[d1] + hexArray[d2];
	}

	/***
	 * 先移除，然后在融合
	 * 
	 * @return
	 */
	public static String removeMerge(String source, String separetor, List<String> removeItemList) {
		if (CollectionUtils.isEmpty(removeItemList)) {
			return source;
		}
		if (StringUtils.isEmpty(source)) {
			return source;
		}
		Set<String> ids = new HashSet<>();

		for (String item : org.apache.commons.lang3.StringUtils.split(source, separetor)) {
			if (!removeItemList.contains(item)) {
				ids.add(item);
			}
		}
		if (CollectionUtils.isEmpty(ids)) {
			return "";
		}
		List<String> ret = new ArrayList<>(ids);
		Collections.sort(ret);
		return org.apache.commons.lang3.StringUtils.join(ret.toArray(new String[] {}), separetor);
	}

	public static boolean changed(String v1, String v2) {
		if (StringUtils.isEmpty(v1) && !StringUtils.isEmpty(v2)) {
			return true;
		}
		if (StringUtils.isEmpty(v2) && !StringUtils.isEmpty(v1)) {
			return true;
		}
		if (StringUtils.isEmpty(v2) && StringUtils.isEmpty(v1)) {
			return false;
		}
		return !v1.equals(v2);
	}

	public static boolean changed(Number v1, Number v2) {
		if (v1 == null && v2 != null) {
			return true;
		}
		if (v2 == null && v1 != null) {
			return true;
		}
		if (v2 == null && v1 == null) {
			return false;
		}
		return !v1.equals(v2);
	}

}
