package com.ruoyi.office.domain;

import java.time.LocalTime;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商家用户店铺对象 t_store
 * 
 * @author ruoyi
 * @date 2023-05-29
 */
@Data
public class TStore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 店铺名称 */
    @Excel(name = "店铺名称")
    private String name;

    /** 店铺地址 */
    @Excel(name = "店铺地址")
    private String address;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 营业开始时间 */
    @JsonFormat(pattern = "HH:mm")
    @Excel(name = "营业开始时间", width = 30, dateFormat = "HH:mm")
    private LocalTime startTime;

    /** 营业结束时间 */
    @JsonFormat(pattern = "HH:mm")
    @Excel(name = "营业结束时间", width = 30, dateFormat = "HH:mm")
    private LocalTime stopTime;

    /** 提前预约天数 */
    @Excel(name = "提前预约天数")
    private Long preDays;

    /** wifi */
    @Excel(name = "wifi")
    private String wifi;

    /** 设备控制 */
    @Excel(name = "设备控制")
    private Long equipId;

    /** 所属商圈 */
    @Excel(name = "所属商圈")
    private String busiDistrict;

    /** 地铁线路 */
    @Excel(name = "地铁线路")
    private String subway;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** logo */
    @Excel(name = "logo")
    private String logo;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 通通锁用户名 */
    @Excel(name = "通通锁用户名")
    private String ttlockUname;

    /** 通通锁密码 */
    @Excel(name = "通通锁密码")
    private String ttlockPwd;
}
