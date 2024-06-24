package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 接单者图片存储对象 order_assignment_images
 * 
 * @author ruoyi
 * @date 2024-06-21
 */
public class OrderAssignmentImages extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图片ID */
    private Long imageId;

    /** 接单记录ID */
    @Excel(name = "接单记录ID")
    private Long assignmentId;

    /** 接单者用户ID */
    @Excel(name = "接单者用户ID")
    private Long userId;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long orderId;

    /** 图片URL */
    @Excel(name = "图片URL")
    private String imageUrl;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadedAt;

    public void setImageId(Long imageId) 
    {
        this.imageId = imageId;
    }

    public Long getImageId() 
    {
        return imageId;
    }
    public void setAssignmentId(Long assignmentId) 
    {
        this.assignmentId = assignmentId;
    }

    public Long getAssignmentId() 
    {
        return assignmentId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }
    public void setUploadedAt(Date uploadedAt) 
    {
        this.uploadedAt = uploadedAt;
    }

    public Date getUploadedAt() 
    {
        return uploadedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("imageId", getImageId())
            .append("assignmentId", getAssignmentId())
            .append("userId", getUserId())
            .append("orderId", getOrderId())
            .append("imageUrl", getImageUrl())
            .append("uploadedAt", getUploadedAt())
            .toString();
    }
}
