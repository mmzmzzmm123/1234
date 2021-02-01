package com.stdiet.custom.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 微信销售账号对象 sys_wx_sale_account
 * 
 * @author wonder
 * @date 2021-01-29
 */
@Data
public class SysWxSaleAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 账号名称 */
    @Excel(name = "账号名称")
    private String nickName;

    /** 账号id */
    @Excel(name = "账号id")
    private Long accountId;

    /** 微信号 */
    @Excel(name = "微信号")
    private String wxId;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    private String imgUrl;

    private String mediaId;

    private String remark;

    private Integer count;

}