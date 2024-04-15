package com.baoli.store.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商户费用计划对象 baoli_biz_fee_schema
 * 
 * @author niujs
 * @date 2024-04-15
 */
public class BaoliBizFeeSchema extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 商户id */
    @Excel(name = "商户id")
    private Long storeId;

    /** 费用类型（字典） */
    @Excel(name = "费用类型", readConverterExp = "字=典")
    private String feeType;

    /** 签约银行 */
    @Excel(name = "签约银行")
    private Long bankId;

    /** 生效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 失效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "失效时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 序号 */
    @Excel(name = "序号")
    private Long orderNum;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStoreId(Long storeId) 
    {
        this.storeId = storeId;
    }

    public Long getStoreId() 
    {
        return storeId;
    }
    public void setFeeType(String feeType) 
    {
        this.feeType = feeType;
    }

    public String getFeeType() 
    {
        return feeType;
    }
    public void setBankId(Long bankId) 
    {
        this.bankId = bankId;
    }

    public Long getBankId() 
    {
        return bankId;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setOrderNum(Long orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum() 
    {
        return orderNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("storeId", getStoreId())
            .append("feeType", getFeeType())
            .append("bankId", getBankId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("orderNum", getOrderNum())
            .toString();
    }
}
