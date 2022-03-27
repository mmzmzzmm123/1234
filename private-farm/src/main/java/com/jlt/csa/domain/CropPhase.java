package com.jlt.csa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 作物生长阶段对象 csa_crop_phase
 * 
 * @author JiaLeitao
 * @date 2022-03-26
 */
public class CropPhase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 阶段id */
    private Long id;

    /** 作物id */
    private Long cropId;

    /** 阶段名称 */
    @Excel(name = "阶段名称")
    private String name;

    /** 产出 */
    @Excel(name = "产出")
    private String production;

    /** 代表图片 */
    @Excel(name = "代表图片")
    private String picture;

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
    public void setCropId(Long cropId) 
    {
        this.cropId = cropId;
    }

    public Long getCropId() 
    {
        return cropId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
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
            .append("id", getId())
            .append("cropId", getCropId())
            .append("name", getName())
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
