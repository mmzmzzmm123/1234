package com.ruoyi.system.product.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品对象 t_product
 * 
 * @author ruoyi
 * @date 2024-04-19
 */
public class TProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String name;

    /** 商品图片 */
    @Excel(name = "商品图片")
    private String image;

    /** 商品价格 */
    @Excel(name = "商品价格")
    private BigDecimal price;

    /** 商品隐藏价格 */
    @Excel(name = "商品隐藏价格")
    private BigDecimal hidePrice;

    /** 商品回收价 */
    @Excel(name = "商品回收价")
    private BigDecimal recoveredPrice;

    /** 上架状态 */
    @Excel(name = "上架状态")
    private Integer status;

    /** 商品描述 */
    @Excel(name = "商品描述")
    private String description;

    /** 版本号 */
    @Excel(name = "版本号")
    private Integer version;

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
    public void setImage(String image) 
    {
        this.image = image;
    }

    public String getImage() 
    {
        return image;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setHidePrice(BigDecimal hidePrice) 
    {
        this.hidePrice = hidePrice;
    }

    public BigDecimal getHidePrice() 
    {
        return hidePrice;
    }
    public void setRecoveredPrice(BigDecimal recoveredPrice) 
    {
        this.recoveredPrice = recoveredPrice;
    }

    public BigDecimal getRecoveredPrice() 
    {
        return recoveredPrice;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setVersion(Integer version) 
    {
        this.version = version;
    }

    public Integer getVersion() 
    {
        return version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("image", getImage())
            .append("price", getPrice())
            .append("hidePrice", getHidePrice())
            .append("recoveredPrice", getRecoveredPrice())
            .append("status", getStatus())
            .append("description", getDescription())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("version", getVersion())
            .toString();
    }
}
