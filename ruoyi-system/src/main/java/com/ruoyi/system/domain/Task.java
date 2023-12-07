package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName task
 */
@TableName(value ="task")
@Data
public class Task implements Serializable {
    /**
     * 任务ID
     */
    @TableId
    private Long taskId;

    /**
     * 任务集合
     */
    private Integer tasksetId;

    /**
     * 任务主键
     */
    private String taskKey;

    /**
     * 任务密码
     */
    private String taskAuthCode;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 其他信息
     */
    private Object otherInfo;

    /**
     * 任务状态
     */
    private Integer taskStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Task other = (Task) that;
        return (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getTasksetId() == null ? other.getTasksetId() == null : this.getTasksetId().equals(other.getTasksetId()))
            && (this.getTaskKey() == null ? other.getTaskKey() == null : this.getTaskKey().equals(other.getTaskKey()))
            && (this.getTaskAuthCode() == null ? other.getTaskAuthCode() == null : this.getTaskAuthCode().equals(other.getTaskAuthCode()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOtherInfo() == null ? other.getOtherInfo() == null : this.getOtherInfo().equals(other.getOtherInfo()))
            && (this.getTaskStatus() == null ? other.getTaskStatus() == null : this.getTaskStatus().equals(other.getTaskStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getTasksetId() == null) ? 0 : getTasksetId().hashCode());
        result = prime * result + ((getTaskKey() == null) ? 0 : getTaskKey().hashCode());
        result = prime * result + ((getTaskAuthCode() == null) ? 0 : getTaskAuthCode().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOtherInfo() == null) ? 0 : getOtherInfo().hashCode());
        result = prime * result + ((getTaskStatus() == null) ? 0 : getTaskStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", tasksetId=").append(tasksetId);
        sb.append(", taskKey=").append(taskKey);
        sb.append(", taskAuthCode=").append(taskAuthCode);
        sb.append(", orderId=").append(orderId);
        sb.append(", otherInfo=").append(otherInfo);
        sb.append(", taskStatus=").append(taskStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}