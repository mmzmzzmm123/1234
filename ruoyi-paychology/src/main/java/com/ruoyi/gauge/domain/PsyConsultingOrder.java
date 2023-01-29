package com.ruoyi.gauge.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 咨询订单对象 psy_consulting_order
 * 
 * @author yangchuang
 * @date 2023-01-29
 */
public class PsyConsultingOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    /** 咨询师id */
    @Excel(name = "咨询师id")
    private Long consultantId;

    /** 0不限 1面对面 2视频 3语音 */
    @Excel(name = "0不限 1面对面 2视频 3语音")
    private Integer type;

    /** 咨询时长 */
    @Excel(name = "咨询时长")
    private Integer consultingTime;

    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date subscribeTime;

    /** 应付费用 */
    @Excel(name = "应付费用")
    private Long price;

    /** 订单状态0待预约1代付款 2代咨询 3已完成 */
    @Excel(name = "订单状态0待预约1代付款 2代咨询 3已完成")
    private Integer status;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNum;

    /** 下单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下单时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setConsultantId(Long consultantId) 
    {
        this.consultantId = consultantId;
    }

    public Long getConsultantId() 
    {
        return consultantId;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }
    public void setConsultingTime(Integer consultingTime) 
    {
        this.consultingTime = consultingTime;
    }

    public Integer getConsultingTime() 
    {
        return consultingTime;
    }
    public void setSubscribeTime(Date subscribeTime) 
    {
        this.subscribeTime = subscribeTime;
    }

    public Date getSubscribeTime() 
    {
        return subscribeTime;
    }
    public void setPrice(Long price) 
    {
        this.price = price;
    }

    public Long getPrice() 
    {
        return price;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setOrderNum(String orderNum) 
    {
        this.orderNum = orderNum;
    }

    public String getOrderNum() 
    {
        return orderNum;
    }
    public void setOrderTime(Date orderTime) 
    {
        this.orderTime = orderTime;
    }

    public Date getOrderTime() 
    {
        return orderTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("consultantId", getConsultantId())
            .append("type", getType())
            .append("consultingTime", getConsultingTime())
            .append("subscribeTime", getSubscribeTime())
            .append("price", getPrice())
            .append("status", getStatus())
            .append("orderNum", getOrderNum())
            .append("orderTime", getOrderTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
