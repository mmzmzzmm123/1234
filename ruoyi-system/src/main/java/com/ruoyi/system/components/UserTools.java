package com.ruoyi.system.components;

import org.apache.commons.lang3.StringUtils;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;

public class UserTools {

	public static AjaxResult checkMerchantInfo(LoginUser loginUser) {

		if (null == loginUser) {
			return AjaxResult.error(ErrInfoConfig.get(11005));
		}
		if (null == loginUser.getMerchantInfo()) {
			return AjaxResult.error(ErrInfoConfig.get(11011));
		}
		if (StringUtils.isEmpty(loginUser.getMerchantInfo().getMerchantId())) {
			return AjaxResult.error(ErrInfoConfig.get(11011));
		}
		return AjaxResult.success(loginUser.getMerchantInfo());
	}
}
