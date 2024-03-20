package com.baoli.sysmanage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 车系对象 baoli_biz_car_series
 * 
 * @author niujs
 * @date 2024-03-19
 */
public class BaoliBizCarSeries extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 一级品牌 */
    private Long brandId;

    /** 二级品牌 */
    private Long level2BrandId;

    /** 批量查询参数 */
    private List<Integer> brands;
    private List<List<Integer>> level2Brands;

    public List<Integer> getBrands() {
        return brands;
    }

    public void setBrands(List<Integer> brands) {
        this.brands = brands;
    }

    public List<List<Integer>> getLevel2Brands() {
        return level2Brands;
    }

    public void setLevel2Brands(List<List<Integer>> level2Brands) {
        this.level2Brands = level2Brands;
    }

    /** 一级品牌 */
    @Excel(name = "一级品牌")
    private String brandName;
    /** 二级品牌 */
    @Excel(name = "二级品牌")
    private String level2BrandName;
    /** 名称 */
    @Excel(name = "车系")
    private String name;

    /** 状态 */
    private String status;

    /** 汽车之家index_id */
    private String indexId;

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
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setIndexId(String indexId) 
    {
        this.indexId = indexId;
    }

    public String getIndexId() 
    {
        return indexId;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("brandId", getBrandId())
            .append("level2BrandId", getLevel2BrandId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("status", getStatus())
            .append("indexId", getIndexId())
            .toString();
    }
}
