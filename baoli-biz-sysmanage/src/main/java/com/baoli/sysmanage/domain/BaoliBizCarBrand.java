package com.baoli.sysmanage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 汽车品牌对象 baoli_biz_car_brand
 * 
 * @author niujs
 * @date 2024-03-18
 */
public class BaoliBizCarBrand extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 品牌id */
    private Long id;
    private Long brandId;
    /** 品牌首字母 */
    @Excel(name = "品牌首字母")
    private String letter;

    /** 品牌名称 */
    @Excel(name = "品牌名称")
    private String name;
    private String brandName;

    /** 上级品牌id */
    @Excel(name = "上级品牌")
    private String parentName;
    /** 上级品牌id */
    private Long parentId;

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
    public void setLetter(String letter) 
    {
        this.letter = letter;
    }

    public String getLetter() 
    {
        return letter;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("letter", getLetter())
            .append("parentId", getParentId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("status", getStatus())
            .append("indexId", getIndexId())
            .toString();
    }
}
