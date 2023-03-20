package com.ruoyi.course.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程订单对象 cour_order
 * 
 * @author ruoyi
 * @date 2023-03-17
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 订单状态（0-创建 1-完成 2-关闭） */
    @Excel(name = "订单状态", readConverterExp = "0=-创建,1=-完成,2=-关闭")
    private Integer status;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private String userId;

    /** 应付金额 */
    @Excel(name = "应付金额")
    private BigDecimal amount;

    /** 课程编号 */
    @Excel(name = "课程编号")
    private String courseId;

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
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }


    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setCourseId(String courseId) 
    {
        this.courseId = courseId;
    }

    public String getCourseId() 
    {
        return courseId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("createTime", getCreateTime())
            .append("status", getStatus())
            .append("userId", getUserId())
            .append("amount", getAmount())
            .append("courseId", getCourseId())
            .toString();
    }
}
