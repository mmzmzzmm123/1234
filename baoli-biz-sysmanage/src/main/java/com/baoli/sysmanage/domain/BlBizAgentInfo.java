package com.baoli.sysmanage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 经销商档案对象 bl_biz_agent_info
 * 
 * @author niujs
 * @date 2024-03-12
 */
public class BlBizAgentInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 经销商名称 */
    @Excel(name = "经销商名称")
    private String agentName;

    /** 品牌id */
    @Excel(name = "品牌id")
    private String brand;

    private String[] brands;
    /** 品牌名称 */
    @Excel(name = "品牌名称")
    private String brandName;
    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;

    /** 集团名称 */
    @Excel(name = "集团名称")
    private String groupName;

    /** 回访联系人 */
    @Excel(name = "回访联系人")
    private String contacts;

    /** 回访联系人职务 */
    @Excel(name = "回访联系人职务")
    private String contactsPosition;

    /** 回访联系人电话 */
    @Excel(name = "回访联系人电话")
    private String contactsPhone;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAgentName(String agentName) 
    {
        this.agentName = agentName;
    }

    public String getAgentName() 
    {
        return agentName;
    }
    public void setBrand(String brand) 
    {
        this.brand = brand;
    }

    public String getBrand() 
    {
        return brand;
    }
    public void setSubmitTime(Date submitTime) 
    {
        this.submitTime = submitTime;
    }

    public Date getSubmitTime() 
    {
        return submitTime;
    }
    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

    public String getGroupName() 
    {
        return groupName;
    }
    public void setContacts(String contacts) 
    {
        this.contacts = contacts;
    }

    public String getContacts() 
    {
        return contacts;
    }
    public void setContactsPosition(String contactsPosition) 
    {
        this.contactsPosition = contactsPosition;
    }

    public String getContactsPosition() 
    {
        return contactsPosition;
    }
    public void setContactsPhone(String contactsPhone) 
    {
        this.contactsPhone = contactsPhone;
    }

    public String getContactsPhone() 
    {
        return contactsPhone;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String[] getBrands() {
        return brands;
    }

    public void setBrands(String[] brands) {
        this.brands = brands;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("agentName", getAgentName())
            .append("brand", getBrand())
            .append("submitTime", getSubmitTime())
            .append("groupName", getGroupName())
            .append("contacts", getContacts())
            .append("contactsPosition", getContactsPosition())
            .append("contactsPhone", getContactsPhone())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
