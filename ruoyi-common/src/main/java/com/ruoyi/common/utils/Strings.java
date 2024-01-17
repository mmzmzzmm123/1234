package com.ruoyi.common.utils;

import java.io.File;
import java.net.URL;
import org.springframework.util.StringUtils;

public class Strings {

	/***
	 * 拿到 执行堆栈
	 * 
	 * @return
	 */
	public static String getStack(int maxLine) {
		StringBuilder sb = new StringBuilder();
		int line = 0;
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (StackTraceElement element : stackTrace) {
			if (element == null) {
				continue;
			}
			if (element.toString().contains("java.lang.Thread.getStackTrace")) {
				continue;
			}
			sb.append(element.toString()).append("\r\n");
			line++;
			if (line > maxLine) {
				break;
			}
		}
		return sb.toString();
	}

	/***
	 * 截取 字符串， 使其最大字符数 为maxLen 长度
	 * 
	 * @param resource
	 * @param maxLen
	 * @return
	 */
	public static String subMax(String resource, int maxLen) {
		if (StringUtils.isEmpty(resource)) {
			return resource;
		}
		if (maxLen <= 0) {
			return "";
		}

		if (resource.length() <= maxLen) {
			return resource;
		}

		return resource.substring(0, maxLen);
	}

	public static String extractFileName(String urlString) {
		try {
			URL url = new URL(urlString);
			String filePart = url.getFile();
			String name = new File(filePart).getName();
			if (name.contains(".")) {
				return name.substring(0, name.indexOf("."));
			}
			return name;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urlString;
	}
}
