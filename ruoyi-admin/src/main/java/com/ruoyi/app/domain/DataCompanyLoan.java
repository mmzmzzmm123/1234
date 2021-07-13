package com.ruoyi.app.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业贷款信息对象 data_company_loan
 * 
 * @author genius
 * @date 2021-07-13
 */
public class DataCompanyLoan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 企业id */
    private Long companyId;

    /** 闽政通用户ID */
    @Excel(name = "闽政通用户ID")
    private String mztUserId;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String companyName;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String companyCreditCode;

    /** 省份 */
    @Excel(name = "省份")
    private String companyProvince;

    /** 市 */
    @Excel(name = "市")
    private String companyCity;

    /** 区或县 */
    @Excel(name = "区或县")
    private String companyArea;

    /** 企业划型 */
    @Excel(name = "企业划型")
    private String companyType;

    /** 所在行业 */
    @Excel(name = "所在行业")
    private String companyIndustry;

    /** 主营业务 */
    @Excel(name = "主营业务")
    private String companyBusiness;

    /** 意向银行（逗号隔开） */
    @Excel(name = "意向银行", readConverterExp = "逗=号隔开")
    private String loanBand;

    /** 贷款金额 */
    @Excel(name = "贷款金额")
    private BigDecimal loanAmount;

    /** 是否首次贷款（0是 1不是） */
    @Excel(name = "是否首次贷款", readConverterExp = "0=是,1=不是")
    private String loanFirst;

    /** 贷款用途 */
    @Excel(name = "贷款用途")
    private String loanPurpose;

    /** 联系人姓名 */
    @Excel(name = "联系人姓名")
    private String contactName;

    /** 联系人手机 */
    @Excel(name = "联系人手机")
    private String contactPhone;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setCompanyId(Long companyId) 
    {
        this.companyId = companyId;
    }

    public Long getCompanyId() 
    {
        return companyId;
    }
    public void setMztUserId(String mztUserId) 
    {
        this.mztUserId = mztUserId;
    }

    public String getMztUserId() 
    {
        return mztUserId;
    }
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }
    public void setCompanyCreditCode(String companyCreditCode) 
    {
        this.companyCreditCode = companyCreditCode;
    }

    public String getCompanyCreditCode() 
    {
        return companyCreditCode;
    }
    public void setCompanyProvince(String companyProvince) 
    {
        this.companyProvince = companyProvince;
    }

    public String getCompanyProvince() 
    {
        return companyProvince;
    }
    public void setCompanyCity(String companyCity) 
    {
        this.companyCity = companyCity;
    }

    public String getCompanyCity() 
    {
        return companyCity;
    }
    public void setCompanyArea(String companyArea) 
    {
        this.companyArea = companyArea;
    }

    public String getCompanyArea() 
    {
        return companyArea;
    }
    public void setCompanyType(String companyType) 
    {
        this.companyType = companyType;
    }

    public String getCompanyType() 
    {
        return companyType;
    }
    public void setCompanyIndustry(String companyIndustry) 
    {
        this.companyIndustry = companyIndustry;
    }

    public String getCompanyIndustry() 
    {
        return companyIndustry;
    }
    public void setCompanyBusiness(String companyBusiness) 
    {
        this.companyBusiness = companyBusiness;
    }

    public String getCompanyBusiness() 
    {
        return companyBusiness;
    }
    public void setLoanBand(String loanBand) 
    {
        this.loanBand = loanBand;
    }

    public String getLoanBand() 
    {
        return loanBand;
    }
    public void setLoanAmount(BigDecimal loanAmount) 
    {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getLoanAmount() 
    {
        return loanAmount;
    }
    public void setLoanFirst(String loanFirst) 
    {
        this.loanFirst = loanFirst;
    }

    public String getLoanFirst() 
    {
        return loanFirst;
    }
    public void setLoanPurpose(String loanPurpose) 
    {
        this.loanPurpose = loanPurpose;
    }

    public String getLoanPurpose() 
    {
        return loanPurpose;
    }
    public void setContactName(String contactName) 
    {
        this.contactName = contactName;
    }

    public String getContactName() 
    {
        return contactName;
    }
    public void setContactPhone(String contactPhone) 
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() 
    {
        return contactPhone;
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
            .append("companyId", getCompanyId())
            .append("mztUserId", getMztUserId())
            .append("companyName", getCompanyName())
            .append("companyCreditCode", getCompanyCreditCode())
            .append("companyProvince", getCompanyProvince())
            .append("companyCity", getCompanyCity())
            .append("companyArea", getCompanyArea())
            .append("companyType", getCompanyType())
            .append("companyIndustry", getCompanyIndustry())
            .append("companyBusiness", getCompanyBusiness())
            .append("loanBand", getLoanBand())
            .append("loanAmount", getLoanAmount())
            .append("loanFirst", getLoanFirst())
            .append("loanPurpose", getLoanPurpose())
            .append("remark", getRemark())
            .append("contactName", getContactName())
            .append("contactPhone", getContactPhone())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
