package com.ruoyi.system.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VehicleSourceVO {

    /**
     * 车辆名称
     */
    private String name;
    /**
     * 指导价
     */
    private BigDecimal guidPrice;
    /**
     * 售价
     */
    private BigDecimal price;
    /**
     * 外观颜色
     */
    private String color;
    /**
     * 内饰颜色
     */
    private String innerColor;
    /**
     * 车辆状态
     */
    private String status;
    /**
     * 所在地区
     */
    private String area;
    /**
     * 状态 裸车/
     */
    private String type;
    /**
     * 备注
     */
    private String notes;
    /**
     * 车源地
     */
    private String sourceArea;
    /**
     * 车龄
     */
    private Long age;
    /**
     * 卖家id
     */
    private Long ownerId;
    /**
     * 图片url数组
     */
    private List<String> urls;
    /**
     * 车系名称
     */
    private String series;
    /**
     * 卖家电话
     */
    private String phone;
    /**
     * 卖家头像
     */
    private String avatar;
    /**
     * 微信号
     */
    private String wxNum;
    /**
     * 卖家昵称
     */
    private String nickName;
}
