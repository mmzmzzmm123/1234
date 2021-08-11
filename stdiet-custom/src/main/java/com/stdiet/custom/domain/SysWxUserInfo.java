package com.stdiet.custom.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stdiet.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 微信用户对象 sys_wx_user_info
 *
 * @author wonder
 * @date 2020-11-28
 */
@Data
public class SysWxUserInfo {

    /**
     * 微信openid
     */
    private String openid;

    private Long cusId;

    private String customerId;

    private Integer height;

    private Integer age;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 微信appid
     */
    @Excel(name = "微信appid")
    private String appid;

    /**
     * 电话
     */
    @Excel(name = "电话")
    private String phone;

    /**
     * 用户头像
     */
    @Excel(name = "用户头像")
    private String avatarUrl;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /**
     * 城市
     */
    @Excel(name = "城市")
    private String city;

    /**
     * 省份
     */
    @Excel(name = "省份")
    private String province;

    private String language;

    /**
     * 国家
     */
    @Excel(name = "国家")
    private String country;

    /** 健康宣言 */
    @Excel(name = "健康宣言")
    private String healthManifesto;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;

}