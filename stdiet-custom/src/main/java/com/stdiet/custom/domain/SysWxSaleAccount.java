package com.stdiet.custom.domain;

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

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setWxNickName(String wxNickName)
    {
        this.wxNickName = wxNickName;
    }

    public String getWxNickName()
    {
        return wxNickName;
    }
    public void setWxAccount(String wxAccount)
    {
        this.wxAccount = wxAccount;
    }

    public String getWxAccount()
    {
        return wxAccount;
    }
    public void setWxPhone(String wxPhone)
    {
        this.wxPhone = wxPhone;
    }

    public String getWxPhone()
    {
        return wxPhone;
    }
    public void setWxCodeUrl(String wxCodeUrl)
    {
        this.wxCodeUrl = wxCodeUrl;
    }

    public String getWxCodeUrl()
    {
        return wxCodeUrl;
    }
    public void setWxType(Integer wxType)
    {
        this.wxType = wxType;
    }

    public Integer getWxType()
    {
        return wxType;
    }
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("wxNickName", getWxNickName())
                .append("wxAccount", getWxAccount())
                .append("wxPhone", getWxPhone())
                .append("wxCodeUrl", getWxCodeUrl())
                .append("remark", getRemark())
                .append("wxType", getWxType())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("delFlag", getDelFlag())
                .toString();
    }
}