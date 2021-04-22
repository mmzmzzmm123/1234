package com.stdiet.custom.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 微信用户记录对象 sys_wx_user_log
 *
 * @author wonder
 * @date 2020-11-29
 */
@Data
public class SysWxUserLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 微信openid */
    private String openid;

    /** 体重 */
    @Excel(name = "体重")
    private BigDecimal weight;

    /** 微信appid */
    @Excel(name = "微信appid")
    private String appid;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 睡觉时间 */
    @Excel(name = "睡觉时间")
    private String sleepTime;

    /** 起床时间 */
    @Excel(name = "起床时间")
    private String wakeupTime;

    /** 运动情况（Y是 N否） */
    @Excel(name = "运动情况", readConverterExp = "Y=是,N=否")
    private String sport;

    /** 用户头像 */
    @Excel(name = "用户头像")
    private String avatarUrl;

    /** 饮食情况（Y是 N否） */
    @Excel(name = "饮食情况", readConverterExp = "Y=是,N=否")
    private String diet;

    /** 熬夜失眠（Y是 N否） */
    @Excel(name = "熬夜失眠", readConverterExp = "Y=是,N=否")
    private String insomnia;

    /** 排便情况（Y是 N否） */
    @Excel(name = "排便情况", readConverterExp = "Y=是,N=否")
    private String defecation;

    /** 饮水量 */
    @Excel(name = "饮水量")
    private Long water;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date logTime;

    //客户ID
    private Long customerId;

    //建议
    private String remark;

    /**
     * 非持久化字段，客户姓名
     */
    @Excel(name = "姓名")
    private String customerName;

    //营养师ID
    private Long nutritionistId;

    /**
     * 非持久化字段，营养师
     */
    @Excel(name = "营养师")
    private String nutritionist;

    /** 情绪 */
    @Excel(name = "情绪")
    private String emotion;

    /** 食谱之外的食物 */
    @Excel(name = "食谱之外的食物")
    private String slyEatFood;

    /** 是否便秘（Y是 N否） */
    @Excel(name = "是否便秘", readConverterExp = "Y=是,N=否")
    private String constipation;

    /** 食材描述 */
    @Excel(name = "食材描述")
    private String ingredientDescribe;

    /** 早餐照片 */
    @Excel(name = "早餐照片")
    private String breakfastImages;

    /** 午餐照片 */
    @Excel(name = "午餐照片")
    private String lunchImages;

    /** 午餐照片 */
    @Excel(name = "午餐照片")
    private String dinnerImages;

    /** 加餐照片 */
    @Excel(name = "加餐照片")
    private String extraMealImages;

    /** 体型对比照 */
    @Excel(name = "体型对比照")
    private String bodyImages;

    /** 服务建议 */
    @Excel(name = "服务建议")
    private String suggest;

    /** 目标体重 */
    @Excel(name = "目标体重")
    private BigDecimal targetWeight;

    /** 执行评分，五分制 */
    @Excel(name = "执行评分，五分制")
    private BigDecimal executionScore;

    /** 点评 */
    @Excel(name = "点评")
    private String comment;

    /** 删除标识 0未删除 1已删除 */
    private Long delFlag;

    //售后营养师ID
    private Long afterNutritionistId;

    //售后营养师
    @Excel(name = "售后营养师")
    private String afterNutritionist;

    //图片预览路径
    private Map<String, List<String>> imagesUrl;
}