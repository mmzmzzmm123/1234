package com.baoli.sysmanage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 汽车型号对象 baoli_biz_car_model
 * 
 * @author niujs
 * @date 2024-03-20
 */
public class BaoliBizCarModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;
    /** 品牌 */
    @Excel(name = "品牌")
    private String fullBrandName;
    /** 车系 */
    @Excel(name = "车系")
    private String serieName;
    /** 名称 */
    @Excel(name = "车型名称")
    private String name;

    /** 一级品牌 */
    private Long brandId;

    /** 二级品牌 */
    private Long level2BrandId;

    /** 车系 */
    private Long serieId;

    private String brandName;
    /** 名称 */
    @Excel(name = "指导价")
    private Float guidePrice;

    public void setFullBrandName(String fullBrandName) {
        this.fullBrandName = fullBrandName;
    }

    public Float getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(Float guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getFullBrandName(){
        fullBrandName = brandName +" / " + level2BrandName;
        return fullBrandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLevel2BrandName() {
        return level2BrandName;
    }

    public void setLevel2BrandName(String level2BrandName) {
        this.level2BrandName = level2BrandName;
    }

    public String getSerieName() {
        return serieName;
    }

    public void setSerieName(String serieName) {
        this.serieName = serieName;
    }

    /** 二级品牌 */
    private String level2BrandName;
    /** 状态 */
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setBrandId(Long brandId) 
    {
        this.brandId = brandId;
    }

    public Long getBrandId() 
    {
        return brandId;
    }
    public void setLevel2BrandId(Long level2BrandId) 
    {
        this.level2BrandId = level2BrandId;
    }

    public Long getLevel2BrandId() 
    {
        return level2BrandId;
    }
    public void setSerieId(Long serieId) 
    {
        this.serieId = serieId;
    }

    public Long getSerieId() 
    {
        return serieId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("brandId", getBrandId())
            .append("level2BrandId", getLevel2BrandId())
            .append("serieId", getSerieId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("status", getStatus())
            .toString();
    }
}
