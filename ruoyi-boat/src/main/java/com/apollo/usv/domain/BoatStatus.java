package com.apollo.usv.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 航船状态对象 usv_boat_status
 * 
 * @author lwq
 * @date 2021-07-02
 */
public class BoatStatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 航船编号 */
    @Excel(name = "航船编号")
    private String boatSn;

    /** 轨迹编号 */
    @Excel(name = "轨迹编号")
    private String routeSn;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 马达转速 */
    @Excel(name = "马达转速")
    private String motorSpeed;

    /** 电压 */
    @Excel(name = "电压")
    private String voltage;

    /** 电流 */
    @Excel(name = "电流")
    private String current;

    /** 温度 */
    @Excel(name = "温度")
    private String temperature;

    /** 电池状态 */
    @Excel(name = "电池状态")
    private String batteryStatus;

    /** 电池电量 */
    @Excel(name = "电池电量")
    private String batteryPower;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBoatSn(String boatSn) 
    {
        this.boatSn = boatSn;
    }

    public String getBoatSn() 
    {
        return boatSn;
    }
    public void setRouteSn(String routeSn) 
    {
        this.routeSn = routeSn;
    }

    public String getRouteSn() 
    {
        return routeSn;
    }
    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }
    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLatitude() 
    {
        return latitude;
    }
    public void setMotorSpeed(String motorSpeed) 
    {
        this.motorSpeed = motorSpeed;
    }

    public String getMotorSpeed() 
    {
        return motorSpeed;
    }
    public void setVoltage(String voltage) 
    {
        this.voltage = voltage;
    }

    public String getVoltage() 
    {
        return voltage;
    }
    public void setCurrent(String current) 
    {
        this.current = current;
    }

    public String getCurrent() 
    {
        return current;
    }
    public void setTemperature(String temperature) 
    {
        this.temperature = temperature;
    }

    public String getTemperature() 
    {
        return temperature;
    }
    public void setBatteryStatus(String batteryStatus) 
    {
        this.batteryStatus = batteryStatus;
    }

    public String getBatteryStatus() 
    {
        return batteryStatus;
    }
    public void setBatteryPower(String batteryPower) 
    {
        this.batteryPower = batteryPower;
    }

    public String getBatteryPower() 
    {
        return batteryPower;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("boatSn", getBoatSn())
            .append("routeSn", getRouteSn())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("motorSpeed", getMotorSpeed())
            .append("voltage", getVoltage())
            .append("current", getCurrent())
            .append("temperature", getTemperature())
            .append("batteryStatus", getBatteryStatus())
            .append("batteryPower", getBatteryPower())
            .append("time", getTime())
            .toString();
    }
}
