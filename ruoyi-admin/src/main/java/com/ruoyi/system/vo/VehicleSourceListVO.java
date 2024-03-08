package com.ruoyi.system.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 车源列表返回VO
 */
@Data
public class VehicleSourceListVO {

    /**
     * 车源id
     */
    private Long id;

    /**
     * 车源名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 外观颜色
     */
    private String color;
    /**
     * 地区
     */
    private String area;
    /**
     * 状态
     */
    private String status;
    /**
     * 指导价
     */
    private BigDecimal guidPrice;
    /**
     * 车系图片
     */
    private String url;

}
