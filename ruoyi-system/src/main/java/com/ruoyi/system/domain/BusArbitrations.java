package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仲裁对象 bus_arbitrations
 * 
 * @author ruoyi
 * @date 2024-06-21
 */
public class BusArbitrations extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 仲裁ID */
    private Long arbitrationId;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long orderId;

    /** 请求仲裁的用户ID */
    @Excel(name = "请求仲裁的用户ID")
    private Long requestedBy;

    /** 仲裁原因 */
    @Excel(name = "仲裁原因")
    private String reason;

    /** 仲裁状态 */
    @Excel(name = "仲裁状态")
    private Integer status;

    /** 仲裁结果 */
    @Excel(name = "仲裁结果")
    private String result;

    /** 仲裁请求时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "仲裁请求时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 仲裁更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "仲裁更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    public void setArbitrationId(Long arbitrationId) 
    {
        this.arbitrationId = arbitrationId;
    }

    public Long getArbitrationId() 
    {
        return arbitrationId;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setRequestedBy(Long requestedBy) 
    {
        this.requestedBy = requestedBy;
    }

    public Long getRequestedBy() 
    {
        return requestedBy;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }
    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("arbitrationId", getArbitrationId())
            .append("orderId", getOrderId())
            .append("requestedBy", getRequestedBy())
            .append("reason", getReason())
            .append("status", getStatus())
            .append("result", getResult())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
