package com.stdiet.custom.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 微信账号对象 sys_wx_sale_account
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
@Data
public class SysWxSaleAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 微信昵称 */
    @Excel(name = "微信昵称")
    private String wxNickName;

    /** 微信号 */
    @Excel(name = "微信号")
    private String wxAccount;

    /** 微信手机号 */
    @Excel(name = "微信手机号")
    private String wxPhone;

    /** 二维码图片 */
    private String wxCodeUrl;

    /** 微信类型 */
    private Integer wxType;

    /** 删除标识 0未删除 1已删除，默认0 */
    private Integer delFlag;

    private Integer status;

    private Integer useCount;
}