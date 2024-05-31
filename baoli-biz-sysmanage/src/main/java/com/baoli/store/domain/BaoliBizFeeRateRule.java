package com.baoli.store.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.function.DoubleBinaryOperator;

/**
 * 对店费率规则对象 baoli_biz_fee_rate_rule
 * 
 * @author niujs
 * @date 2024-05-10
 */
public class BaoliBizFeeRateRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 规则标题 */
    @Excel(name = "规则标题")
    private String title;

    /** 费率类型 */
    @Excel(name = "费率类型")
    private String feeRateType;

    private String bankId;

    /** 品牌 */
    @Excel(name = "品牌")
    private Long brandId;
    private Long level2BrandId;
    private Long seriesId;
    /** 型号 */
    @Excel(name = "型号")
    private Long modelId;

    /** 补贴类型 */
    @Excel(name = "补贴类型")
    private String subsidyType;

    /** 规则内容 */
    @Excel(name = "规则内容")
    //private String content;

    private String brandName;
    private String level2BrandName;
    private String modelName;
    private String bankName;
    private String seriesName;
    private String period; // 期数
    private Double totalFeeRate; // 总费率
    private Double rebateRate; // 返佣比率
    private Double cardPersonRate; // 持卡人费率
    private Double storeFeeRate; // 商户费率 （总费率 - 持卡人费率）
    private Date startTime;
    private Date finishTime;
    private String executeType;

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Double getTotalFeeRate() {
        return totalFeeRate;
    }

    public void setTotalFeeRate(Double totalFeeRate) {
        this.totalFeeRate = totalFeeRate;
    }

    public Double getRebateRate() {
        return rebateRate;
    }

    public void setRebateRate(Double rebateRate) {
        this.rebateRate = rebateRate;
    }

    public Double getCardPersonRate() {
        return cardPersonRate;
    }

    public void setCardPersonRate(Double cardPersonRate) {
        this.cardPersonRate = cardPersonRate;
    }

    public Double getStoreFeeRate() {
        return storeFeeRate;
    }

    public void setStoreFeeRate(Double storeFeeRate) {
        this.storeFeeRate = storeFeeRate;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLevel2BrandName() {
        return level2BrandName;
    }

    public void setLevel2BrandName(String level2BrandName) {
        this.level2BrandName = level2BrandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getLevel2BrandId() {
        return level2BrandId;
    }

    public void setLevel2BrandId(Long level2BrandId) {
        this.level2BrandId = level2BrandId;
    }

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
    public void setBankId(String bankId)
    {
        this.bankId = bankId;
    }

    public String getBankId()
    {
        return bankId;
    }
    public void setBrandId(Long brandId) 
    {
        this.brandId = brandId;
    }

    public Long getBrandId() 
    {
        return brandId;
    }
    public void setModelId(Long modelId) 
    {
        this.modelId = modelId;
    }

    public Long getModelId() 
    {
        return modelId;
    }
    public void setSubsidyType(String subsidyType) 
    {
        this.subsidyType = subsidyType;
    }

    public String getSubsidyType() 
    {
        return subsidyType;
    }
//    public void setContent(String content)
//    {
//        this.content = content;
//    }
//
//    public String getContent()
//    {
//        return content;
//    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("feeRateType", getFeeRateType())
            .append("bankId", getBankId())
            .append("remark", getRemark())
            .append("brandId", getBrandId())
            .append("modelId", getModelId())
            .toString();
    }
}
