package com.ruoyi.common.config;

import java.util.HashMap;
import java.util.Map;

public class ErrInfoConfig {

	public static Map<Integer, String> err = new HashMap<>();

	static {
		err.put(10000, "未知异常，Err: %s");
		err.put(11000, "参数异常， 字段: %s");
		err.put(11001, "商品参数异常");
		err.put(11002, "商品未上架");
		err.put(11003, "SKU列表为空");
		err.put(11004, "SKU价格为空，SKU-ID: %s");
		err.put(11005, "用户信息为空");
		err.put(11006, "服务已下架");
		err.put(11007, "此规格服务已下架");


	}

	public static String get(int code) {
		return err.get(code);
	}

	public static String getDynmic(int code, String... param) {
		String val = err.get(code);
		return String.format(val, param);
	}

}
