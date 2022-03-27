package com.jlt.csa.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 采摘任务明细对象 csa_pick_detail
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public class PickDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 采摘明细id */
    private Long id;

    /** 任务id */
    private Long taskId;

    /** 菜地品种id */
    private Long strainId;

    /** 计划重量 */
    @Excel(name = "计划重量")
    private BigDecimal planWeight;

    /** 实际重量 */
    @Excel(name = "实际重量")
    private BigDecimal resultWeight;

    /** 是否处理完毕 */
    @Excel(name = "是否处理完毕")
    private String isCompleted;

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
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setStrainId(Long strainId) 
    {
        this.strainId = strainId;
    }

    public Long getStrainId() 
    {
        return strainId;
    }
    public void setPlanWeight(BigDecimal planWeight) 
    {
        this.planWeight = planWeight;
    }

    public BigDecimal getPlanWeight() 
    {
        return planWeight;
    }
    public void setResultWeight(BigDecimal resultWeight) 
    {
        this.resultWeight = resultWeight;
    }

    public BigDecimal getResultWeight() 
    {
        return resultWeight;
    }
    public void setIsCompleted(String isCompleted) 
    {
        this.isCompleted = isCompleted;
    }

    public String getIsCompleted() 
    {
        return isCompleted;
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
            .append("taskId", getTaskId())
            .append("strainId", getStrainId())
            .append("planWeight", getPlanWeight())
            .append("resultWeight", getResultWeight())
            .append("isCompleted", getIsCompleted())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
