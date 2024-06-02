package com.baoli.store.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 费用规则对象 baoli_biz_fee_rule
 * 
 * @author niujs
 * @date 2024-05-23
 */
public class BaoliBizFeeRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 规则标题 */
    @Excel(name = "规则标题")
    private String title;

    /** 费用类型 */
    @Excel(name = "费用类型")
    private String feeType;

    /** 银行id */
    @Excel(name = "银行id")
    private Long bankId;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    private String bankName;
    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String caculateType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String executeType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String content;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
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
    public void setCaculateType(String caculateType) 
    {
        this.caculateType = caculateType;
    }

    public String getCaculateType() 
    {
        return caculateType;
    }
    public void setExecuteType(String executeType) 
    {
        this.executeType = executeType;
    }

    public String getExecuteType() 
    {
        return executeType;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("feeType", getFeeType())
            .append("bankId", getBankId())
            .append("remark", getRemark())
            .append("caculateType", getCaculateType())
            .append("executeType", getExecuteType())
            .append("content", getContent())
            .toString();
    }
}
