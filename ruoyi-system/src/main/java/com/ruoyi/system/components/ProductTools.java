package com.ruoyi.system.components;

import com.ruoyi.common.core.domain.AjaxResult;

public class ProductTools {

	public static AjaxResult checkNormal(Long producetId) {
		if (producetId == null) {
			return AjaxResult.error("商品ID为空");
		}
		// 查询商品
		return null;
		
	}
}
