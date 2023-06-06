package com.ruoyi.office.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantUserVo {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 微信app id */
    @Excel(name = "微信openId")
    private String openId;

    /** uinion id */
    @Excel(name = "uinion id")
    private String unionId;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 分享码 */
    @Excel(name = "分享码")
    private String shareCode;

    /** 微信code */
    @Excel(name = "微信code")
    private String wxCode;

    /** 邀请人手机号 */
    @Excel(name = "邀请人手机号")
    private String inviterPhone;

    /** 是否会员 */
    @Excel(name = "是否会员")
    private String isMember;

    /** 商家id(sys_user) */
    @Excel(name = "商家id(sys_user)")
    private Long userId;

    /** 余额 */
    @Excel(name = "余额")
    private BigDecimal amount;

    /** 积分 */
    @Excel(name = "积分")
    private Long point;


    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 登录ip */
    @Excel(name = "登录ip")
    private String loginIp;

    /** 登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date loginDate;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;
}
