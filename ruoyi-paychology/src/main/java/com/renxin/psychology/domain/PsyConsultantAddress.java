package com.renxin.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.renxin.common.annotation.Excel;
import com.renxin.common.core.domain.BaseEntity;

/**
 * 咨询师地址对象 psy_consultant_address
 * 
 * @author renxin
 * @date 2024-06-20
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consultant_address")
public class PsyConsultantAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 地址ID */
    private Long addressId;

    /** 咨询师ID */
    @Excel(name = "咨询师ID")
    private Long consultantId;

    /** 1 默认 0 未默认 */
    @Excel(name = "1 默认 0 未默认")
    private String status;

    /** 联系人名 */
    @Excel(name = "联系人名")
    private String contactName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactTelephone;

    /** 联系地址 */
    @Excel(name = "联系地址")
    private String contactAddress;

    /** 0 未删除 1 删除 */
    private String delFlag;

    public void setAddressId(Long addressId) 
    {
        this.addressId = addressId;
    }

    public Long getAddressId() 
    {
        return addressId;
    }
    public void setConsultantId(Long consultantId) 
    {
        this.consultantId = consultantId;
    }

    public Long getConsultantId() 
    {
        return consultantId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setContactName(String contactName) 
    {
        this.contactName = contactName;
    }

    public String getContactName() 
    {
        return contactName;
    }
    public void setContactTelephone(String contactTelephone) 
    {
        this.contactTelephone = contactTelephone;
    }

    public String getContactTelephone() 
    {
        return contactTelephone;
    }
    public void setContactAddress(String contactAddress) 
    {
        this.contactAddress = contactAddress;
    }

    public String getContactAddress() 
    {
        return contactAddress;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("addressId", getAddressId())
            .append("consultantId", getConsultantId())
            .append("status", getStatus())
            .append("contactName", getContactName())
            .append("contactTelephone", getContactTelephone())
            .append("contactAddress", getContactAddress())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
