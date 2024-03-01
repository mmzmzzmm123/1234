package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车源管理对象 vehicle_source
 * 
 * @author carol
 * @date 2024-03-01
 */
public class VehicleSource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 所属车系id */
    @Excel(name = "所属车系id")
    private Long seriesId;

    /** 车辆具体名称 */
    @Excel(name = "车辆具体名称")
    private String name;

    /** 外观颜色 */
    @Excel(name = "外观颜色")
    private String color;

    /** 内饰颜色 */
    @Excel(name = "内饰颜色")
    private String innerColor;

    /** 车辆所在地 */
    @Excel(name = "车辆所在地")
    private String area;

    /** 车辆状态 */
    @Excel(name = "车辆状态")
    private String status;

    /** 车源地 */
    @Excel(name = "车源地")
    private String sourceArea;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String notes;

    /** 车龄 */
    @Excel(name = "车龄")
    private Long age;

    /** 卖家id */
    @Excel(name = "卖家id")
    private Long ownerId;

    /** 售卖价格 */
    @Excel(name = "售卖价格")
    private BigDecimal price;

    /** 车辆类型 */
    @Excel(name = "车辆类型")
    private String type;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSeriesId(Long seriesId) 
    {
        this.seriesId = seriesId;
    }

    public Long getSeriesId() 
    {
        return seriesId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setColor(String color) 
    {
        this.color = color;
    }

    public String getColor() 
    {
        return color;
    }
    public void setInnerColor(String innerColor) 
    {
        this.innerColor = innerColor;
    }

    public String getInnerColor() 
    {
        return innerColor;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setSourceArea(String sourceArea) 
    {
        this.sourceArea = sourceArea;
    }

    public String getSourceArea() 
    {
        return sourceArea;
    }
    public void setNotes(String notes) 
    {
        this.notes = notes;
    }

    public String getNotes() 
    {
        return notes;
    }
    public void setAge(Long age) 
    {
        this.age = age;
    }

    public Long getAge() 
    {
        return age;
    }
    public void setOwnerId(Long ownerId) 
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() 
    {
        return ownerId;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("seriesId", getSeriesId())
            .append("name", getName())
            .append("color", getColor())
            .append("innerColor", getInnerColor())
            .append("area", getArea())
            .append("status", getStatus())
            .append("sourceArea", getSourceArea())
            .append("notes", getNotes())
            .append("age", getAge())
            .append("ownerId", getOwnerId())
            .append("price", getPrice())
            .append("type", getType())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
