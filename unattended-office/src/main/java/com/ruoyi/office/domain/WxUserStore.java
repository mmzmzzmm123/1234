package com.ruoyi.office.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信用户余额对象 t_wx_user_store
 * 
 * @author ruoyi
 * @date 2023-05-27
 */
@TableName("t_wx_user_store")
public class WxUserStore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 微信id */
    @Excel(name = "微信id")
    private String wxId;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long storeId;

    /** 是否会员 */
    @Excel(name = "是否会员")
    private String isMember;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 余额 */
    @Excel(name = "余额")
    private BigDecimal amount;

    /** 积分 */
    @Excel(name = "积分")
    private Long point;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWxId(String wxId) 
    {
        this.wxId = wxId;
    }

    public String getWxId() 
    {
        return wxId;
    }
    public void setStoreId(Long storeId) 
    {
        this.storeId = storeId;
    }

    public Long getStoreId() 
    {
        return storeId;
    }
    public void setIsMember(String isMember) 
    {
        this.isMember = isMember;
    }

    public String getIsMember() 
    {
        return isMember;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setPoint(Long point) 
    {
        this.point = point;
    }

    public Long getPoint() 
    {
        return point;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("wxId", getWxId())
            .append("storeId", getStoreId())
            .append("isMember", getIsMember())
            .append("phone", getPhone())
            .append("amount", getAmount())
            .append("point", getPoint())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
