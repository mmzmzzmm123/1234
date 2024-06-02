package com.baoli.blacklist.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户黑名单对象 baoli_biz_customer_blacklist
 * 
 * @author ruoyi
 * @date 2024-05-11
 */
public class BaoliBizCustomerBlacklist extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 证件类型 */
    @Excel(name = "证件类型")
    private String certificateType;

    /** 证件号 */
    @Excel(name = "证件号")
    private String certificateNumber;

    /** 省份id */
    @Excel(name = "省份id")
    private String provinceId;

    /** 城市id */
    @Excel(name = "城市id")
    private String cityId;

    /** 企业名称 */
    @Excel(name = "省份")
    private String provinceName;

    /** 企业名称 */
    @Excel(name = "城市名称")
    private String cityName;

    /** 银行id */
    @Excel(name = "银行id")
    private Long bankId;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String phone;

    /** 加入黑名单原因 */
    @Excel(name = "加入黑名单原因")
    private String reasons;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

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

    public String getUserName()
    {
        return userName;
    }
    public void setCertificateType(String certificateType) 
    {
        this.certificateType = certificateType;
    }

    public String getCertificateType() 
    {
        return certificateType;
    }
    public void setCertificateNumber(String certificateNumber) 
    {
        this.certificateNumber = certificateNumber;
    }

    public String getCertificateNumber() 
    {
        return certificateNumber;
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
    public void setBankId(Long bankId)
    {
        this.bankId = bankId;
    }

    public Long getBankId() 
    {
        return bankId;
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
            .append("userName", getUserName())
            .append("certificateType", getCertificateType())
            .append("certificateNumber", getCertificateNumber())
            .append("provinceName", getProvinceName())
            .append("cityName", getCityName())
            .append("provinceId", getProvinceId())
            .append("cityId", getCityId())
            .append("bankId", getBankId())
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
