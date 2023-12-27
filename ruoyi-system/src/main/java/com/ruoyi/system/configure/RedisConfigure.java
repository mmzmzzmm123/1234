package com.ruoyi.system.configure;

import org.springframework.stereotype.Component;
import com.ruoyi.common.config.dymic.RedisRefreshScope;
import lombok.Data;

@RedisRefreshScope
@Component
@Data
public class RedisConfigure {

	/**
	 * 超时 退款 的 分钟数
	 */
	private int refundTimeOutSec = 60 * 5;

}
