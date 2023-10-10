package com.ruoyi.office.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 我要加盟对象 t_join_us
 *
 * @author ruoyi
 * @date 2023-10-10
 */
public class TJoinUs extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 城市 */
    @Excel(name = "城市")
    private String city;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 是否已选好场地 */
    @Excel(name = "是否已选好场地")
    private String placeReady;

    /** 预计开店预算 */
    @Excel(name = "预计开店预算")
    private String budget;

    /** 是否到店体验过 */
    @Excel(name = "是否到店体验过")
    private String isExperience;

    /** 希望开展哪些业务 */
    @Excel(name = "希望开展哪些业务")
    private String expBusiness;

    /** 是否联系其他人了解过 */
    @Excel(name = "是否联系其他人了解过")
    private String isContact;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String address;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCity()
    {
        return city;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setPlaceReady(String placeReady)
    {
        this.placeReady = placeReady;
    }

    public String getPlaceReady()
    {
        return placeReady;
    }
    public void setBudget(String budget)
    {
        this.budget = budget;
    }

    public String getBudget()
    {
        return budget;
    }
    public void setIsExperience(String isExperience)
    {
        this.isExperience = isExperience;
    }

    public String getIsExperience()
    {
        return isExperience;
    }
    public void setExpBusiness(String expBusiness)
    {
        this.expBusiness = expBusiness;
    }

    public String getExpBusiness()
    {
        return expBusiness;
    }
    public void setIsContact(String isContact)
    {
        this.isContact = isContact;
    }

    public String getIsContact()
    {
        return isContact;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("city", getCity())
                .append("name", getName())
                .append("phone", getPhone())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("placeReady", getPlaceReady())
                .append("budget", getBudget())
                .append("isExperience", getIsExperience())
                .append("expBusiness", getExpBusiness())
                .append("isContact", getIsContact())
                .append("address", getAddress())
                .toString();
    }
}
