package com.ruoyi.common.utils;

import org.apache.commons.lang3.StringUtils;

public class Env {

	public static boolean isLocal() {
		return !StringUtils.isEmpty(System.getProperty("env")) && System.getProperty("env").equals("local");
	}
}
