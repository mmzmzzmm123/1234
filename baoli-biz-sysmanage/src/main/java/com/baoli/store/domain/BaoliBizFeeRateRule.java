package com.baoli.store.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 费率规则对象 baoli_biz_fee_rate_rule
 * 
 * @author niujs
 * @date 2024-04-18
 */
public class BaoliBizFeeRateRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 规则标题 */
    @Excel(name = "规则标题")
    private String title;

    /** 费率类型 */
    @Excel(name = "费率类型")
    private String feeRateType;

    /** 银行id */
    @Excel(name = "银行id")
    private Long bankId;

    /** 额外选项 */
    @Excel(name = "额外选项")
    private String extraOptions;

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
    public void setFeeRateType(String feeRateType) 
    {
        this.feeRateType = feeRateType;
    }

    public String getFeeRateType() 
    {
        return feeRateType;
    }
    public void setBankId(Long bankId) 
    {
        this.bankId = bankId;
    }

    public Long getBankId() 
    {
        return bankId;
    }
    public void setExtraOptions(String extraOptions) 
    {
        this.extraOptions = extraOptions;
    }

    public String getExtraOptions() 
    {
        return extraOptions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("feeRateType", getFeeRateType())
            .append("bankId", getBankId())
            .append("remark", getRemark())
            .append("extraOptions", getExtraOptions())
            .toString();
    }
}
