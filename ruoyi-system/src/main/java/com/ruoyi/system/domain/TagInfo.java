package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * tag信息对象 tag_info
 * 
 * @author derek
 * @date 2023-11-14
 */
public class TagInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long tagInfoId;

    /** RFID id */
    @Excel(name = "RFID id")
    private String rfidId;

    /** 部件代号 */
    @Excel(name = "部件代号")
    private String partNumber;

    /** 序列号 */
    @Excel(name = "序列号")
    private String serialNumber;

    /** 展示日期 */
    @Excel(name = "展示日期")
    private String displayDate;

    /** 展示名称 */
    @Excel(name = "展示名称")
    private String displayName;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 原产地证 */
    @Excel(name = "原产地证")
    private String coo;

    /** 状态：1-待入库，2-入库中，3-已入库，4-出库中，5-已出库 */
    @Excel(name = "状态：1-待入库，2-入库中，3-已入库，4-出库中，5-已出库")
    private Long status;

    public void setTagInfoId(Long tagInfoId) 
    {
        this.tagInfoId = tagInfoId;
    }

    public Long getTagInfoId() 
    {
        return tagInfoId;
    }
    public void setRfidId(String rfidId) 
    {
        this.rfidId = rfidId;
    }

    public String getRfidId() 
    {
        return rfidId;
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
    public void setDisplayDate(String displayDate) 
    {
        this.displayDate = displayDate;
    }

    public String getDisplayDate() 
    {
        return displayDate;
    }
    public void setDisplayName(String displayName) 
    {
        this.displayName = displayName;
    }

    public String getDisplayName() 
    {
        return displayName;
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
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tagInfoId", getTagInfoId())
            .append("rfidId", getRfidId())
            .append("partNumber", getPartNumber())
            .append("serialNumber", getSerialNumber())
            .append("displayDate", getDisplayDate())
            .append("displayName", getDisplayName())
            .append("quantity", getQuantity())
            .append("coo", getCoo())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
