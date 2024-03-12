package com.baoli.sysmanage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 汽车型号对象 baoli_biz_carmodel
 * 
 * @author niujs
 * @date 2024-03-12
 */
public class BaoliBizCarmodel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 品牌id */
    @Excel(name = "品牌id")
    private String brandId;

    /** 品牌名称 */
    @Excel(name = "品牌名称")
    private String brandName;

    /** 型号名称 */
    @Excel(name = "型号名称")
    private String name;

    /** 简述 */
    @Excel(name = "简述")
    private String pedar;

    /** 最低价格 */
    @Excel(name = "最低价格")
    private Integer minprice;

    /** 最高价格 */
    @Excel(name = "最高价格")
    private Integer maxprice;

    /** 图片名称 */
    @Excel(name = "图片名称")
    private String imgname;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String imgpath;

    /** 备注 */
    @Excel(name = "备注")
    private String explain;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBrandId(String brandId) 
    {
        this.brandId = brandId;
    }

    public String getBrandId() 
    {
        return brandId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPedar(String pedar) 
    {
        this.pedar = pedar;
    }

    public String getPedar() 
    {
        return pedar;
    }
    public void setMinprice(Integer minprice) 
    {
        this.minprice = minprice;
    }

    public Integer getMinprice() 
    {
        return minprice;
    }
    public void setMaxprice(Integer maxprice) 
    {
        this.maxprice = maxprice;
    }

    public Integer getMaxprice() 
    {
        return maxprice;
    }
    public void setImgname(String imgname) 
    {
        this.imgname = imgname;
    }

    public String getImgname() 
    {
        return imgname;
    }
    public void setImgpath(String imgpath) 
    {
        this.imgpath = imgpath;
    }

    public String getImgpath() 
    {
        return imgpath;
    }
    public void setExplain(String explain) 
    {
        this.explain = explain;
    }

    public String getExplain() 
    {
        return explain;
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
            .append("brandId", getBrandId())
            .append("name", getName())
            .append("pedar", getPedar())
            .append("minprice", getMinprice())
            .append("maxprice", getMaxprice())
            .append("imgname", getImgname())
            .append("imgpath", getImgpath())
            .append("explain", getExplain())
            .toString();
    }
}
