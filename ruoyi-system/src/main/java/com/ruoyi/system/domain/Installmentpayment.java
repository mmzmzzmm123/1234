package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 查询对象 installmentpayment
 * 
 * @author ruoyi
 * @date 2023-12-25
 */
public class Installmentpayment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 收款人 */
    @Excel(name = "收款人")
    private String payee;

    /** 支付期限 */
    @Excel(name = "支付期限")
    private String payment;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private Integer amountpaid;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    private Date paytime;

    /** 关联srchecksheet表id */
    @Excel(name = "关联srchecksheet表id")
    private Long srchecksheetId;

    /** 预留字段1 */
    private String reserve1;

    /** 预留字段2 */
    private String reserve2;

    /** 预留字段3 */
    private Long reserve3;

    /** 预留字段4 */
    private Long reserve4;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPayee(String payee) 
    {
        this.payee = payee;
    }

    public String getPayee() 
    {
        return payee;
    }
    public void setPayment(String payment) 
    {
        this.payment = payment;
    }

    public String getPayment() 
    {
        return payment;
    }
    public void setAmountpaid(Integer amountpaid)
    {
        this.amountpaid = amountpaid;
    }

    public Integer getAmountpaid()
    {
        return amountpaid;
    }
    public void setPaytime(Date paytime) 
    {
        this.paytime = paytime;
    }

    public Date getPaytime() 
    {
        return paytime;
    }
    public void setSrchecksheetId(Long srchecksheetId) 
    {
        this.srchecksheetId = srchecksheetId;
    }

    public Long getSrchecksheetId() 
    {
        return srchecksheetId;
    }
    public void setReserve1(String reserve1) 
    {
        this.reserve1 = reserve1;
    }

    public String getReserve1() 
    {
        return reserve1;
    }
    public void setReserve2(String reserve2) 
    {
        this.reserve2 = reserve2;
    }

    public String getReserve2() 
    {
        return reserve2;
    }
    public void setReserve3(Long reserve3) 
    {
        this.reserve3 = reserve3;
    }

    public Long getReserve3() 
    {
        return reserve3;
    }
    public void setReserve4(Long reserve4) 
    {
        this.reserve4 = reserve4;
    }

    public Long getReserve4() 
    {
        return reserve4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("payee", getPayee())
            .append("payment", getPayment())
            .append("amountpaid", getAmountpaid())
            .append("paytime", getPaytime())
            .append("srchecksheetId", getSrchecksheetId())
            .append("reserve1", getReserve1())
            .append("reserve2", getReserve2())
            .append("reserve3", getReserve3())
            .append("reserve4", getReserve4())
            .toString();
    }
}
