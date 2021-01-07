package com.stdiet.custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 订单服务暂停对象 sys_order_pause
 *
 * @author xzj
 * @date 2021-01-07
 */
public class SysOrderPause extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 订单id */
    private Long orderId;

    /** 服务暂停开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "服务暂停开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pauseStartDate;

    /** 服务暂停结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "服务暂停结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pauseEndDate;

    /** 服务暂停理由 */
    @Excel(name = "服务暂停理由")
    private String reason;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 删除标识 0未删除 1已删除，默认0 */
    private Integer delFlag;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public Long getOrderId()
    {
        return orderId;
    }
    public void setPauseStartDate(Date pauseStartDate)
    {
        this.pauseStartDate = pauseStartDate;
    }

    public Date getPauseStartDate()
    {
        return pauseStartDate;
    }
    public void setPauseEndDate(Date pauseEndDate)
    {
        this.pauseEndDate = pauseEndDate;
    }

    public Date getPauseEndDate()
    {
        return pauseEndDate;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getReason()
    {
        return reason;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("pauseStartDate", getPauseStartDate())
                .append("pauseEndDate", getPauseEndDate())
                .append("reason", getReason())
                .append("remarks", getRemarks())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("delFlag", getDelFlag())
                .toString();
    }
}