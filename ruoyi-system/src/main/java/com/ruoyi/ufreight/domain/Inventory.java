package com.ruoyi.ufreight.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存对象 t_inventory
 * 
 * @author derek
 * @date 2023-11-20
 */
public class Inventory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long inventoryId;

    /** 部件编号(sku id) */
    @Excel(name = "部件编号(sku id)")
    private String partNumber;

    /** 货架条码 */
    @Excel(name = "货架条码")
    private String shelfBarcode;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    public void setInventoryId(Long inventoryId) 
    {
        this.inventoryId = inventoryId;
    }

    public Long getInventoryId() 
    {
        return inventoryId;
    }
    public void setPartNumber(String partNumber) 
    {
        this.partNumber = partNumber;
    }

    public String getPartNumber() 
    {
        return partNumber;
    }
    public void setShelfBarcode(String shelfBarcode) 
    {
        this.shelfBarcode = shelfBarcode;
    }

    public String getShelfBarcode() 
    {
        return shelfBarcode;
    }
    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("inventoryId", getInventoryId())
            .append("partNumber", getPartNumber())
            .append("shelfBarcode", getShelfBarcode())
            .append("quantity", getQuantity())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
