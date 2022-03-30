package com.jlt.csa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 作物生长阶段对象 csa_crop_phase
 * 
 * @author 郏磊涛
 * @date 2022-03-30
 */
public class CropPhase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 阶段id */
    private Long phaseId;

    /** 作物 */
    @Excel(name = "作物")
    private Long cropId;

    /** 阶段名称 */
    @Excel(name = "阶段名称")
    private String phaseName;

    /** 产出 */
    @Excel(name = "产出")
    private String production;

    /** 代表图片 */
    @Excel(name = "代表图片")
    private String picture;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setPhaseId(Long phaseId) 
    {
        this.phaseId = phaseId;
    }

    public Long getPhaseId() 
    {
        return phaseId;
    }
    public void setCropId(Long cropId) 
    {
        this.cropId = cropId;
    }

    public Long getCropId() 
    {
        return cropId;
    }
    public void setPhaseName(String phaseName) 
    {
        this.phaseName = phaseName;
    }

    public String getPhaseName() 
    {
        return phaseName;
    }
    public void setProduction(String production) 
    {
        this.production = production;
    }

    public String getProduction() 
    {
        return production;
    }
    public void setPicture(String picture) 
    {
        this.picture = picture;
    }

    public String getPicture() 
    {
        return picture;
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
            .append("phaseId", getPhaseId())
            .append("cropId", getCropId())
            .append("phaseName", getPhaseName())
            .append("production", getProduction())
            .append("picture", getPicture())
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
