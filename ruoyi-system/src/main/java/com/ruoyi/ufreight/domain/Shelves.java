package com.ruoyi.ufreight.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库货架对象 t_shelves
 * 
 * @author derek
 * @date 2023-11-20
 */
public class Shelves extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long shelfId;

    /** 条码 */
    @Excel(name = "条码")
    private String barcode;

    /** 位置 */
    @Excel(name = "位置")
    private String location;

    public void setShelfId(Long shelfId) 
    {
        this.shelfId = shelfId;
    }

    public Long getShelfId() 
    {
        return shelfId;
    }
    public void setBarcode(String barcode) 
    {
        this.barcode = barcode;
    }

    public String getBarcode() 
    {
        return barcode;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("shelfId", getShelfId())
            .append("barcode", getBarcode())
            .append("location", getLocation())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
