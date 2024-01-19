package com.ruoyi.system.components.movie.spi;

/**
 * tital:延迟速度控制器
 * 
 * @author Administrator
 *
 */
public interface DelaySpeedController {

	int calculate(int sourceVal, String playId);

}
