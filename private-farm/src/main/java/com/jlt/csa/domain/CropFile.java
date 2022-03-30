package com.jlt.csa.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 作物档案对象 csa_crop_file
 * 
 * @author 郏磊涛
 * @date 2022-03-30
 */
public class CropFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 作物档案id */
    private Long cropId;

    /** 作物名称 */
    @Excel(name = "作物名称")
    private String cropName;

    /** 种植指南 */
    private String plantGuide;

    /** 代表图片 */
    @Excel(name = "代表图片")
    private String picture;

    /** 作物描述 */
    @Excel(name = "作物描述")
    private String description;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志 */
    private String delFlag;

    /** 作物生长阶段信息 */
    private List<CropPhase> cropPhaseList;

    public void setCropId(Long cropId) 
    {
        this.cropId = cropId;
    }

    public Long getCropId() 
    {
        return cropId;
    }
    public void setCropName(String cropName) 
    {
        this.cropName = cropName;
    }

    public String getCropName() 
    {
        return cropName;
    }
    public void setPlantGuide(String plantGuide) 
    {
        this.plantGuide = plantGuide;
    }

    public String getPlantGuide() 
    {
        return plantGuide;
    }
    public void setPicture(String picture) 
    {
        this.picture = picture;
    }

    public String getPicture() 
    {
        return picture;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
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

    public List<CropPhase> getCropPhaseList()
    {
        return cropPhaseList;
    }

    public void setCropPhaseList(List<CropPhase> cropPhaseList)
    {
        this.cropPhaseList = cropPhaseList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("cropId", getCropId())
            .append("cropName", getCropName())
            .append("plantGuide", getPlantGuide())
            .append("picture", getPicture())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("cropPhaseList", getCropPhaseList())
            .toString();
    }
}
