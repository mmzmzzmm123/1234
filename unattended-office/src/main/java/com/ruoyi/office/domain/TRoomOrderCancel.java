package com.ruoyi.office.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 预约取消设置对象 t_room_order_cancel
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
public class TRoomOrderCancel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 退单截至时间 */
    @Excel(name = "退单截至时间")
    private Long cancelMinutes;

    /** 退单比例 */
    @Excel(name = "退单比例")
    private Long refundRate;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCancelMinutes(Long cancelMinutes) 
    {
        this.cancelMinutes = cancelMinutes;
    }

    public Long getCancelMinutes() 
    {
        return cancelMinutes;
    }
    public void setRefundRate(Long refundRate) 
    {
        this.refundRate = refundRate;
    }

    public Long getRefundRate() 
    {
        return refundRate;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("cancelMinutes", getCancelMinutes())
            .append("refundRate", getRefundRate())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
