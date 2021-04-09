package com.edgelight.ed.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业账号对象 el_enterprise_info
 * 
 * @author limingcai
 * @date 2021-04-09
 */
public class EnterpriseInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long id;

    /** 企业账号 */
    @Excel(name = "企业账号")
    private String account;

    /** 密码 */
    @Excel(name = "密码")
    private String pwd;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String name;

    /** 企业类型 */
    @Excel(name = "企业类型")
    private String type;

    /** 企业规模（人） */
    @Excel(name = "企业规模", readConverterExp = "人=")
    private Long personScale;

    /** 注册资金 */
    @Excel(name = "注册资金")
    private Long registeredCapital;

    /** 注册日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registeredDate;

    /** 占地面积 */
    @Excel(name = "占地面积")
    private Long areaCovered;

    /** 法人 */
    @Excel(name = "法人")
    private String legalPerson;

    /** 邮箱地址 */
    @Excel(name = "邮箱地址")
    private String email;

    /** 电话号码 */
    @Excel(name = "电话号码")
    private String phoneNo;

    /** 街道 */
    @Excel(name = "街道")
    private Long street;

    /** 是否重点企业 */
    @Excel(name = "是否重点企业")
    private Integer isKeypoint;

    /** 省 */
    private Long province;

    /** 市 */
    private Long city;

    /** 区县 */
    private Long district;

    /** 地址 */
    private String address;

    /** 企业介绍 */
    private String introduce;

    /** LOGOl图片 */
    private String logoImage;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    private Date createdAt;

    /** 更新人 */
    private String updatedBy;

    /** 更新时间 */
    private Date updatedAt;

    /** 删除人 */
    private String deletedBy;

    /** 删除标示 */
    private Integer isDeleted;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAccount(String account) 
    {
        this.account = account;
    }

    public String getAccount() 
    {
        return account;
    }
    public void setPwd(String pwd) 
    {
        this.pwd = pwd;
    }

    public String getPwd() 
    {
        return pwd;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setPersonScale(Long personScale) 
    {
        this.personScale = personScale;
    }

    public Long getPersonScale() 
    {
        return personScale;
    }
    public void setRegisteredCapital(Long registeredCapital) 
    {
        this.registeredCapital = registeredCapital;
    }

    public Long getRegisteredCapital() 
    {
        return registeredCapital;
    }
    public void setRegisteredDate(Date registeredDate) 
    {
        this.registeredDate = registeredDate;
    }

    public Date getRegisteredDate() 
    {
        return registeredDate;
    }
    public void setAreaCovered(Long areaCovered) 
    {
        this.areaCovered = areaCovered;
    }

    public Long getAreaCovered() 
    {
        return areaCovered;
    }
    public void setLegalPerson(String legalPerson) 
    {
        this.legalPerson = legalPerson;
    }

    public String getLegalPerson() 
    {
        return legalPerson;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setPhoneNo(String phoneNo) 
    {
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() 
    {
        return phoneNo;
    }
    public void setStreet(Long street) 
    {
        this.street = street;
    }

    public Long getStreet() 
    {
        return street;
    }
    public void setIsKeypoint(Integer isKeypoint) 
    {
        this.isKeypoint = isKeypoint;
    }

    public Integer getIsKeypoint() 
    {
        return isKeypoint;
    }
    public void setProvince(Long province) 
    {
        this.province = province;
    }

    public Long getProvince() 
    {
        return province;
    }
    public void setCity(Long city) 
    {
        this.city = city;
    }

    public Long getCity() 
    {
        return city;
    }
    public void setDistrict(Long district) 
    {
        this.district = district;
    }

    public Long getDistrict() 
    {
        return district;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setIntroduce(String introduce) 
    {
        this.introduce = introduce;
    }

    public String getIntroduce() 
    {
        return introduce;
    }
    public void setLogoImage(String logoImage) 
    {
        this.logoImage = logoImage;
    }

    public String getLogoImage() 
    {
        return logoImage;
    }
    public void setCreatedBy(String createdBy) 
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() 
    {
        return createdBy;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }
    public void setUpdatedBy(String updatedBy) 
    {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() 
    {
        return updatedBy;
    }
    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }
    public void setDeletedBy(String deletedBy) 
    {
        this.deletedBy = deletedBy;
    }

    public String getDeletedBy() 
    {
        return deletedBy;
    }
    public void setIsDeleted(Integer isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("account", getAccount())
            .append("pwd", getPwd())
            .append("name", getName())
            .append("type", getType())
            .append("personScale", getPersonScale())
            .append("registeredCapital", getRegisteredCapital())
            .append("registeredDate", getRegisteredDate())
            .append("areaCovered", getAreaCovered())
            .append("legalPerson", getLegalPerson())
            .append("email", getEmail())
            .append("phoneNo", getPhoneNo())
            .append("street", getStreet())
            .append("isKeypoint", getIsKeypoint())
            .append("province", getProvince())
            .append("city", getCity())
            .append("district", getDistrict())
            .append("address", getAddress())
            .append("introduce", getIntroduce())
            .append("logoImage", getLogoImage())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .append("deletedBy", getDeletedBy())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
