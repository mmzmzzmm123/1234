package com.ruoyi.office.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商家用户店铺对象 t_store
 * 
 * @author ruoyi
 * @date 2023-05-29
 */
public class TStore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 店铺名称 */
    @Excel(name = "店铺名称")
    private String name;

    /** 店铺地址 */
    @Excel(name = "店铺地址")
    private String address;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 营业开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "营业开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 营业结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "营业结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date stopTime;

    /** 提前预约天数 */
    @Excel(name = "提前预约天数")
    private Long preDays;

    /** wifi */
    @Excel(name = "wifi")
    private String wifi;

    /** 设备控制 */
    @Excel(name = "设备控制")
    private Long equipId;

    /** 所属商圈 */
    @Excel(name = "所属商圈")
    private String busiDistrict;

    /** 地铁线路 */
    @Excel(name = "地铁线路")
    private String subway;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** logo */
    @Excel(name = "logo")
    private String logo;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
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
    public void setPreDays(Long preDays) 
    {
        this.preDays = preDays;
    }

    public Long getPreDays() 
    {
        return preDays;
    }
    public void setWifi(String wifi) 
    {
        this.wifi = wifi;
    }

    public String getWifi() 
    {
        return wifi;
    }
    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }
    public void setBusiDistrict(String busiDistrict) 
    {
        this.busiDistrict = busiDistrict;
    }

    public String getBusiDistrict() 
    {
        return busiDistrict;
    }
    public void setSubway(String subway) 
    {
        this.subway = subway;
    }

    public String getSubway() 
    {
        return subway;
    }
    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLatitude() 
    {
        return latitude;
    }
    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }
    public void setLogo(String logo) 
    {
        this.logo = logo;
    }

    public String getLogo() 
    {
        return logo;
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
            .append("id", getId())
            .append("userId", getUserId())
            .append("name", getName())
            .append("address", getAddress())
            .append("phone", getPhone())
            .append("startTime", getStartTime())
            .append("stopTime", getStopTime())
            .append("preDays", getPreDays())
            .append("wifi", getWifi())
            .append("equipId", getEquipId())
            .append("busiDistrict", getBusiDistrict())
            .append("subway", getSubway())
            .append("latitude", getLatitude())
            .append("longitude", getLongitude())
            .append("logo", getLogo())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
