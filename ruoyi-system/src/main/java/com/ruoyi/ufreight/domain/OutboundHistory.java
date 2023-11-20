package com.ruoyi.ufreight.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出库流水对象 t_outbound_history
 * 
 * @author derek
 * @date 2023-11-20
 */
public class OutboundHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long outboundId;

    /** 部件编号(sku id) */
    @Excel(name = "部件编号(sku id)")
    private String partNumber;

    /** 货架条码 */
    @Excel(name = "货架条码")
    private String shelfBarcode;

    /** 出库数量 */
    @Excel(name = "出库数量")
    private Long quantity;

    public void setOutboundId(Long outboundId) 
    {
        this.outboundId = outboundId;
    }

    public Long getOutboundId() 
    {
        return outboundId;
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
            .append("outboundId", getOutboundId())
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
