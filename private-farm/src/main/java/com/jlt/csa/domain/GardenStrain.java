package com.jlt.csa.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 菜地品种对象 csa_garden_strain
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public class GardenStrain extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜地品种id */
    private Long id;

    /** 菜地 */
    private Long gardenId;

    /** 种植任务 */
    @Excel(name = "种植任务")
    private Long taskId;

    /** 作物id */
    @Excel(name = "作物id")
    private Long cropId;

    /** 作物阶段 */
    @Excel(name = "作物阶段")
    private Long cropPhaseId;

    /** 平方米 */
    @Excel(name = "平方米")
    private BigDecimal m2;

    /** 产出重量 */
    @Excel(name = "产出重量")
    private BigDecimal outputWeight;

    /** 是否确定 */
    @Excel(name = "是否确定")
    private String isDefinite;

    /** 是否种植 */
    @Excel(name = "是否种植")
    private String isPlanted;

    /** 是否拉秧 */
    @Excel(name = "是否拉秧")
    private String isEnded;

    /** 确定时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "确定时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date definiteDate;

    /** 种植日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "种植日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date plantedDate;

    /** 拉秧日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拉秧日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endedDate;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志 */
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
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setCropId(Long cropId) 
    {
        this.cropId = cropId;
    }

    public Long getCropId() 
    {
        return cropId;
    }
    public void setCropPhaseId(Long cropPhaseId) 
    {
        this.cropPhaseId = cropPhaseId;
    }

    public Long getCropPhaseId() 
    {
        return cropPhaseId;
    }
    public void setM2(BigDecimal m2) 
    {
        this.m2 = m2;
    }

    public BigDecimal getM2() 
    {
        return m2;
    }
    public void setOutputWeight(BigDecimal outputWeight) 
    {
        this.outputWeight = outputWeight;
    }

    public BigDecimal getOutputWeight() 
    {
        return outputWeight;
    }
    public void setIsDefinite(String isDefinite) 
    {
        this.isDefinite = isDefinite;
    }

    public String getIsDefinite() 
    {
        return isDefinite;
    }
    public void setIsPlanted(String isPlanted) 
    {
        this.isPlanted = isPlanted;
    }

    public String getIsPlanted() 
    {
        return isPlanted;
    }
    public void setIsEnded(String isEnded) 
    {
        this.isEnded = isEnded;
    }

    public String getIsEnded() 
    {
        return isEnded;
    }
    public void setDefiniteDate(Date definiteDate) 
    {
        this.definiteDate = definiteDate;
    }

    public Date getDefiniteDate() 
    {
        return definiteDate;
    }
    public void setPlantedDate(Date plantedDate) 
    {
        this.plantedDate = plantedDate;
    }

    public Date getPlantedDate() 
    {
        return plantedDate;
    }
    public void setEndedDate(Date endedDate) 
    {
        this.endedDate = endedDate;
    }

    public Date getEndedDate() 
    {
        return endedDate;
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
            .append("taskId", getTaskId())
            .append("cropId", getCropId())
            .append("cropPhaseId", getCropPhaseId())
            .append("m2", getM2())
            .append("outputWeight", getOutputWeight())
            .append("isDefinite", getIsDefinite())
            .append("isPlanted", getIsPlanted())
            .append("isEnded", getIsEnded())
            .append("definiteDate", getDefiniteDate())
            .append("plantedDate", getPlantedDate())
            .append("endedDate", getEndedDate())
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
