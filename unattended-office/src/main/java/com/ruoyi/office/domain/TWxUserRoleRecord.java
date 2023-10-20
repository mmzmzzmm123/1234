package com.ruoyi.office.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信用户邀请授权记录对象 t_wx_user_role_record
 *
 * @author ruoyi
 * @date 2023-10-20
 */
public class TWxUserRoleRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 微信用户id */
    @Excel(name = "微信用户id")
    private Long wxUserId;

    /** 商户id */
    @Excel(name = "商户id")
    private Long merchantId;

    /** 门店 id */
    @Excel(name = "门店 id")
    private Long storeId;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 角色 */
    @Excel(name = "角色")
    private String role;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setWxUserId(Long wxUserId)
    {
        this.wxUserId = wxUserId;
    }

    public Long getWxUserId()
    {
        return wxUserId;
    }
    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }
    public void setStoreId(Long storeId)
    {
        this.storeId = storeId;
    }

    public Long getStoreId()
    {
        return storeId;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setRole(String role)
    {
        this.role = role;
    }

    public String getRole()
    {
        return role;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("wxUserId", getWxUserId())
                .append("merchantId", getMerchantId())
                .append("storeId", getStoreId())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("role", getRole())
                .toString();
    }
}
