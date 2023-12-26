package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 invoice
 * 
 * @author ruoyi
 * @date 2023-12-24
 */
public class Invoice extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private String stoName;
    private String stoUnit;
    private String stoType;
    private Double minStoMoney;
    private Double maxStoMoney;
    private Double minInvoiceMoney;
    private Double maxInvoiceMoney;
    private Integer isFen;

    public Integer getIsFen() {
        return isFen;
    }

    public void setIsFen(Integer isFen) {
        this.isFen = isFen;
    }

    public Double getMinStoMoney() {
        return minStoMoney;
    }

    public void setMinStoMoney(Double minStoMoney) {
        this.minStoMoney = minStoMoney;
    }

    public Double getMaxStoMoney() {
        return maxStoMoney;
    }

    public void setMaxStoMoney(Double maxStoMoney) {
        this.maxStoMoney = maxStoMoney;
    }

    public Double getMinInvoiceMoney() {
        return minInvoiceMoney;
    }

    public void setMinInvoiceMoney(Double minInvoiceMoney) {
        this.minInvoiceMoney = minInvoiceMoney;
    }

    public Double getMaxInvoiceMoney() {
        return maxInvoiceMoney;
    }

    public void setMaxInvoiceMoney(Double maxInvoiceMoney) {
        this.maxInvoiceMoney = maxInvoiceMoney;
    }

    public String getStoName() {
        return stoName;
    }

    public void setStoName(String stoName) {
        this.stoName = stoName;
    }

    public String getStoUnit() {
        return stoUnit;
    }

    public void setStoUnit(String stoUnit) {
        this.stoUnit = stoUnit;
    }

    public String getStoType() {
        return stoType;
    }

    public void setStoType(String stoType) {
        this.stoType = stoType;
    }

    /** id */
    private Integer id;

    /** 入库单id */
    @Excel(name = "入库单id")
    private String stoId;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal stoMoney;

    /** 备注 */
    @Excel(name = "备注")
    private String stoRemark;

    /** 入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date stoDate;

    /** 录入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date invoiceDate;

    /** 发票号 */
    @Excel(name = "发票号")
    private Long invoiceId;

    /** 核销人 */
    @Excel(name = "核销人")
    private Long userId;

    /** 状态值0:正常1:删除 */
    @Excel(name = "状态值0:正常1:删除")
    private Integer status;

    /** 公司：101.赛尔，201.鑫晖，203.晶晖 */
    @Excel(name = "公司：101.赛尔，201.鑫晖，203.晶晖")
    private Integer deptId;

    /** 开票状态 0:未开票,1:已开票 */
    @Excel(name = "开票状态 0:未开票,1:已开票")
    private Integer kpStatus;

    /** 报销金额 */
    @Excel(name = "报销金额")
    private BigDecimal invoicePrice;

    /** 报销单类型 */
    @Excel(name = "报销单类型")
    private Integer invoiceType;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setStoId(String stoId) 
    {
        this.stoId = stoId;
    }

    public String getStoId() 
    {
        return stoId;
    }
    public void setStoMoney(BigDecimal stoMoney) 
    {
        this.stoMoney = stoMoney;
    }

    public BigDecimal getStoMoney() 
    {
        return stoMoney;
    }
    public void setStoRemark(String stoRemark) 
    {
        this.stoRemark = stoRemark;
    }

    public String getStoRemark() 
    {
        return stoRemark;
    }
    public void setStoDate(Date stoDate) 
    {
        this.stoDate = stoDate;
    }

    public Date getStoDate() 
    {
        return stoDate;
    }
    public void setInvoiceDate(Date invoiceDate) 
    {
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceDate() 
    {
        return invoiceDate;
    }
    public void setInvoiceId(Long invoiceId) 
    {
        this.invoiceId = invoiceId;
    }

    public Long getInvoiceId() 
    {
        return invoiceId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setDeptId(Integer deptId) 
    {
        this.deptId = deptId;
    }

    public Integer getDeptId() 
    {
        return deptId;
    }
    public void setKpStatus(Integer kpStatus) 
    {
        this.kpStatus = kpStatus;
    }

    public Integer getKpStatus() 
    {
        return kpStatus;
    }
    public void setInvoicePrice(BigDecimal invoicePrice) 
    {
        this.invoicePrice = invoicePrice;
    }

    public BigDecimal getInvoicePrice() 
    {
        return invoicePrice;
    }
    public void setInvoiceType(Integer invoiceType) 
    {
        this.invoiceType = invoiceType;
    }

    public Integer getInvoiceType() 
    {
        return invoiceType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("stoId", getStoId())
            .append("stoMoney", getStoMoney())
            .append("stoRemark", getStoRemark())
            .append("stoDate", getStoDate())
            .append("invoiceDate", getInvoiceDate())
            .append("invoiceId", getInvoiceId())
            .append("userId", getUserId())
            .append("status", getStatus())
            .append("deptId", getDeptId())
            .append("kpStatus", getKpStatus())
            .append("invoicePrice", getInvoicePrice())
            .append("invoiceType", getInvoiceType())
            .toString();
    }
}
