package com.jlt.csa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 作物档案对象 csa_crop_file
 * 
 * @author 郏磊涛
 * @date 2022-03-31
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

    /** 配图 */
    @Excel(name = "配图")
    private String picture;

    /** 云配图 */
    private String pictureOss;

    /** 作物描述 */
    @Excel(name = "作物描述")
    private String description;

    /** 可种状态 */
    @Excel(name = "可种状态")
    private String status;

    /** 删除标志 */
    private String delFlag;

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
    public void setPictureOss(String pictureOss) 
    {
        this.pictureOss = pictureOss;
    }

    public String getPictureOss() 
    {
        return pictureOss;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("cropId", getCropId())
            .append("cropName", getCropName())
            .append("plantGuide", getPlantGuide())
            .append("picture", getPicture())
            .append("pictureOss", getPictureOss())
            .append("description", getDescription())
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
