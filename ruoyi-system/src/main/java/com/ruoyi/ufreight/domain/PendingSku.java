package com.ruoyi.ufreight.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 待处理sku对象 t_pending_sku
 * 
 * @author derek
 * @date 2023-11-20
 */
public class PendingSku extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long pendingId;

    /** RFID EPC */
    @Excel(name = "RFID EPC")
    private String rfidEpc;

    /** 部件代号 */
    @Excel(name = "部件代号")
    private String partNumber;

    /** 序列号 */
    @Excel(name = "序列号")
    private String serialNumber;

    /** 入库日期 */
    @Excel(name = "入库日期")
    private String inDate;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 原产地证 */
    @Excel(name = "原产地证")
    private String coo;

    public void setPendingId(Long pendingId) 
    {
        this.pendingId = pendingId;
    }

    public Long getPendingId() 
    {
        return pendingId;
    }
    public void setRfidEpc(String rfidEpc) 
    {
        this.rfidEpc = rfidEpc;
    }

    public String getRfidEpc() 
    {
        return rfidEpc;
    }
    public void setPartNumber(String partNumber) 
    {
        this.partNumber = partNumber;
    }

    public String getPartNumber() 
    {
        return partNumber;
    }
    public void setSerialNumber(String serialNumber) 
    {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() 
    {
        return serialNumber;
    }
    public void setInDate(String inDate) 
    {
        this.inDate = inDate;
    }

    public String getInDate() 
    {
        return inDate;
    }
    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }
    public void setCoo(String coo) 
    {
        this.coo = coo;
    }

    public String getCoo() 
    {
        return coo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("pendingId", getPendingId())
            .append("rfidEpc", getRfidEpc())
            .append("partNumber", getPartNumber())
            .append("serialNumber", getSerialNumber())
            .append("inDate", getInDate())
            .append("quantity", getQuantity())
            .append("coo", getCoo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
