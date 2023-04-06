package com.ruoyi.course.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

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
    private Integer id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 订单编号 */
    @Excel(name = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 订单状态（0-创建 1-完成 2-关闭） */
    @Excel(name = "订单状态", readConverterExp = "0=-创建,1=-完成,2=-关闭")
    private Integer status;

    /** 用户编号 */
    @Excel(name = "用户ID")
    private Integer userId;

    /** 应付金额 */
    @Excel(name = "应付金额")
    private BigDecimal amount;

    /** 课程ID */
    @Excel(name = "课程ID")
    private Integer courseId;

    /** 课程信息 */
    @Excel(name = "课程信息")
    private CourCourse courseInfo;
    public void setId(int id)
    {
        this.id = id;
    }

    public Integer getId()
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

    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }


    public Integer getStatus() 
    {
        return status;
    }
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getUserId()
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
    public void setCourseId(Integer courseId)
    {
        this.courseId = courseId;
    }

    public Integer getCourseId()
    {
        return courseId;
    }

    public CourCourse getCourseInfo() { return courseInfo; }
    public void setCourseInfo(CourCourse courseMap){
        this.courseInfo = courseMap;
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
