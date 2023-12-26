package com.ruoyi.system.components;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.App;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.system.domain.dto.ApplyAmountFrozenDTO;
import com.ruoyi.system.service.MerchantAmountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderTools {

	public static String orderId() {
		return Ids.getId().toUpperCase();
	}

	public static String frozen(long price, String merchantId, String orderId) {
		final MerchantAmountService service = App.getBean(MerchantAmountService.class);
		ApplyAmountFrozenDTO dto = new ApplyAmountFrozenDTO();
		dto.setAmount(price);
		dto.setMerchantId(merchantId);
		dto.setOrderId(orderId);
		try {
			String frozenId = service.applyAmountFrozen(dto);
			log.info("MerchantAmountService.applyAmountFrozen {} {}", dto, frozenId);
			return frozenId;
		} catch (Throwable e) {
			String trace = Ids.getId();
			log.error("MerchantAmountService.applyAmountFrozen_error {} {}", trace, dto, e);
			throw new ServiceException("冻结用户资金失败 trace: " + trace);
		}
	}

}
