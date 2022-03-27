package com.jlt.csa.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 打理任务对象 csa_tend_task
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public class TendTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务id */
    private Long id;

    /** 菜地id */
    @Excel(name = "菜地id")
    private Long gardenId;

    /** 任务类型 */
    private String taskType;

    /** 任务子类型 */
    private String taskSubtype;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String description;

    /** 任务阶段 */
    @Excel(name = "任务阶段")
    private String executionPhase;

    /** 金币花费 */
    @Excel(name = "金币花费")
    private BigDecimal coins;

    /** 任务产生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务产生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskTime;

    /** 反馈时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date feedbackTime;

    /** 反馈结果 */
    @Excel(name = "反馈结果")
    private String feedbackResult;

    /** 执行人 */
    @Excel(name = "执行人")
    private Long executor;

    /** 反馈人 */
    @Excel(name = "反馈人")
    private Long feedbacker;

    /** 是否处理完毕 */
    @Excel(name = "是否处理完毕")
    private String isCompleted;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGardenId(Long gardenId) 
    {
        this.gardenId = gardenId;
    }

    public Long getGardenId() 
    {
        return gardenId;
    }
    public void setTaskType(String taskType) 
    {
        this.taskType = taskType;
    }

    public String getTaskType() 
    {
        return taskType;
    }
    public void setTaskSubtype(String taskSubtype) 
    {
        this.taskSubtype = taskSubtype;
    }

    public String getTaskSubtype() 
    {
        return taskSubtype;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setExecutionPhase(String executionPhase) 
    {
        this.executionPhase = executionPhase;
    }

    public String getExecutionPhase() 
    {
        return executionPhase;
    }
    public void setCoins(BigDecimal coins) 
    {
        this.coins = coins;
    }

    public BigDecimal getCoins() 
    {
        return coins;
    }
    public void setTaskTime(Date taskTime) 
    {
        this.taskTime = taskTime;
    }

    public Date getTaskTime() 
    {
        return taskTime;
    }
    public void setFeedbackTime(Date feedbackTime) 
    {
        this.feedbackTime = feedbackTime;
    }

    public Date getFeedbackTime() 
    {
        return feedbackTime;
    }
    public void setFeedbackResult(String feedbackResult) 
    {
        this.feedbackResult = feedbackResult;
    }

    public String getFeedbackResult() 
    {
        return feedbackResult;
    }
    public void setExecutor(Long executor) 
    {
        this.executor = executor;
    }

    public Long getExecutor() 
    {
        return executor;
    }
    public void setFeedbacker(Long feedbacker) 
    {
        this.feedbacker = feedbacker;
    }

    public Long getFeedbacker() 
    {
        return feedbacker;
    }
    public void setIsCompleted(String isCompleted) 
    {
        this.isCompleted = isCompleted;
    }

    public String getIsCompleted() 
    {
        return isCompleted;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gardenId", getGardenId())
            .append("taskType", getTaskType())
            .append("taskSubtype", getTaskSubtype())
            .append("description", getDescription())
            .append("executionPhase", getExecutionPhase())
            .append("coins", getCoins())
            .append("taskTime", getTaskTime())
            .append("feedbackTime", getFeedbackTime())
            .append("feedbackResult", getFeedbackResult())
            .append("executor", getExecutor())
            .append("feedbacker", getFeedbacker())
            .append("isCompleted", getIsCompleted())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
