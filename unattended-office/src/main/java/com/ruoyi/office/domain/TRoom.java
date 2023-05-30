package com.ruoyi.office.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 店铺房间对象 t_room
 * 
 * @author ruoyi
 * @date 2023-05-29
 */
public class TRoom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    private Long storeId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** wifi */
    @Excel(name = "wifi")
    private String wifi;

    /** 包厢控制 */
    @Excel(name = "包厢控制")
    private String equipCode;

    /** 桌台控制 */
    @Excel(name = "桌台控制")
    private String tableCode;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 技术时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "技术时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date stopTime;

    /** 状态(空闲/已预约/使用中/预约超时未使用/不可用) */
    @Excel(name = "状态(空闲/已预约/使用中/预约超时未使用/不可用)")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStoreId(Long storeId)
    {
        this.storeId = storeId;
    }

    public Long getStoreId()
    {
        return storeId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setWifi(String wifi) 
    {
        this.wifi = wifi;
    }

    public String getWifi() 
    {
        return wifi;
    }
    public void setEquipCode(String equipCode) 
    {
        this.equipCode = equipCode;
    }

    public String getEquipCode() 
    {
        return equipCode;
    }
    public void setTableCode(String tableCode) 
    {
        this.tableCode = tableCode;
    }

    public String getTableCode() 
    {
        return tableCode;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setStopTime(Date stopTime) 
    {
        this.stopTime = stopTime;
    }

    public Date getStopTime() 
    {
        return stopTime;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("storeId", getStoreId())
            .append("name", getName())
            .append("wifi", getWifi())
            .append("equipCode", getEquipCode())
            .append("tableCode", getTableCode())
            .append("startTime", getStartTime())
            .append("stopTime", getStopTime())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
