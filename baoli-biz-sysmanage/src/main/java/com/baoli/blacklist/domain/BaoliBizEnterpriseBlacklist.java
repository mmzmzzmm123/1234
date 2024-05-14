package com.baoli.blacklist.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业黑名单对象 baoli_biz_enterprise_blacklist
 * 
 * @author ruoyi
 * @date 2024-05-13
 */
public class BaoliBizEnterpriseBlacklist extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String enterpriseName;

    /** 省份id */
    @Excel(name = "省份id")
    private String provinceId;

    /** 企业名称 */
    @Excel(name = "省份")
    private String provinceName;

    /** 企业名称 */
    @Excel(name = "城市名称")
    private String cityName;

    /** 城市id */
    @Excel(name = "城市id")
    private String cityId;

    /** 法人 */
    @Excel(name = "法人")
    private String legalPerson;

    /** 企业地址 */
    @Excel(name = "企业地址")
    private String enterpriseAddress;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String phone;

    /** 加入黑名单原因 */
    @Excel(name = "加入黑名单原因")
    private String reasons;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEnterpriseName(String enterpriseName) 
    {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseName() 
    {
        return enterpriseName;
    }
    public void setProvinceId(String provinceId) 
    {
        this.provinceId = provinceId;
    }

    public String getProvinceId() 
    {
        return provinceId;
    }
    public void setCityId(String cityId) 
    {
        this.cityId = cityId;
    }

    public String getCityId() 
    {
        return cityId;
    }
    public void setLegalPerson(String legalPerson) 
    {
        this.legalPerson = legalPerson;
    }

    public String getLegalPerson() 
    {
        return legalPerson;
    }
    public void setEnterpriseAddress(String enterpriseAddress) 
    {
        this.enterpriseAddress = enterpriseAddress;
    }

    public String getEnterpriseAddress() 
    {
        return enterpriseAddress;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setReasons(String reasons) 
    {
        this.reasons = reasons;
    }

    public String getReasons() 
    {
        return reasons;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("enterpriseName", getEnterpriseName())
            .append("provinceId", getProvinceId())
            .append("cityId", getCityId())
            .append("legalPerson", getLegalPerson())
            .append("provinceName", getProvinceName())
            .append("cityName", getCityName())
            .append("enterpriseAddress", getEnterpriseAddress())
            .append("phone", getPhone())
            .append("reasons", getReasons())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
