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

    private Long brandId;
    private Long modelId;

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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getBankId()
    {
        return bankId;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("feeRateType", getFeeRateType())
            .append("bankId", getBankId())
            .append("remark", getRemark())
            .toString();
    }
}
