package com.onethinker.weatherinfo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.onethinker.common.annotation.Excel;
import com.onethinker.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 天气预报最新结果对象 t_weather_info
 *
 * @author yangyouqi
 * @date 2024-03-08
 */
@Data
@TableName("t_weather_info")
public class WeatherInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表主键
     */
    private Long id;

    /**
     * 是否可见，0发送失败，1发送成功
     */
    @Excel(name = "是否可见，0发送失败，1发送成功")
    private Long enabled;

    /**
     * 权重
     */
    @Excel(name = "权重")
    private Long weight;

    /**
     * 请求服务权限标识
     */
    @Excel(name = "请求服务权限标识")
    private String key;

    /**
     * 城市编码
     */
    @Excel(name = "城市编码")
    private String city;

    /**
     * 气象类型
     */
    @Excel(name = "气象类型")
    private String extensions;

    /**
     * 请求结果
     */
    @Excel(name = "请求结果")
    private String sendResult;


}
