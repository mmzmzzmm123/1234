package com.ruoyi.gauge.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心理测评订单信息对象 psy_order
 * 
 * @author ruoyi
 * @date 2022-10-12
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PsyOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 测评编号 */
    @Excel(name = "测评编号")
    private Long gaugeId;

    /** 订单状态(1-创建,2-完成,3-关闭) */
    @Excel(name = "订单状态(1-创建,2-完成,3-关闭)")
    private Integer orderStatus;

    /** 应付金额 */
    @Excel(name = "应付金额")
    private BigDecimal amount;

    /** 测评完成情况(1-已完成，2-未完成) */
    @Excel(name = "测评完成情况(1-已完成，2-未完成)")
    private Integer gaugeStatus;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    /**
     * 量表标题
     */
    private String gaugeTitle;

    /**
     * 量表简介
     */
    private String gaugeDes;

    /**
     * 总得分
     */
    private String score;

    /**
     * 总得分对应图片
     */
    private String resultUrl;

    public String getGaugeDes() {
        return gaugeDes;
    }

    public String getUserId() {return userId; }

    public void setGaugeDes(String gaugeDes) {
        this.gaugeDes = gaugeDes;
    }

    public String getGaugeTitle() {
        return gaugeTitle;
    }

    public void setGaugeTitle(String gaugeTitle) {
        this.gaugeTitle = gaugeTitle;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setGaugeId(Long gaugeId) 
    {
        this.gaugeId = gaugeId;
    }

    public Long getGaugeId() 
    {
        return gaugeId;
    }
    public void setOrderStatus(Integer orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderStatus() 
    {
        return orderStatus;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setGaugeStatus(Integer gaugeStatus) 
    {
        this.gaugeStatus = gaugeStatus;
    }

    public Integer getGaugeStatus() 
    {
        return gaugeStatus;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("gaugeId", getGaugeId())
            .append("orderStatus", getOrderStatus())
            .append("amount", getAmount())
            .append("gaugeStatus", getGaugeStatus())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .toString();
    }
}
