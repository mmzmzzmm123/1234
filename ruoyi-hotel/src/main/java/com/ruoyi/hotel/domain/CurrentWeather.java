package com.ruoyi.hotel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 实况天气对象 current_weather
 * 
 * @author sucheng
 * @date 2020-12-30
 */
public class CurrentWeather extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 地区/城市ID */
    private Long locationId;

    /** 地区/城市名称 */
    @Excel(name = "地区/城市名称")
    private String locationName;

    /** 该地区/城市所属一级行政区域名称 */
    @Excel(name = "该地区/城市所属一级行政区域名称")
    private String adm1;

    /** 该地区/城市的上级行政区划名称 */
    @Excel(name = "该地区/城市的上级行政区划名称")
    private String adm2;

    /** 实况温度，默认单位：摄氏度 */
    @Excel(name = "实况温度，默认单位：摄氏度")
    private String temp;

    /** 实况体感温度，默认单位：摄氏度 */
    @Excel(name = "实况体感温度，默认单位：摄氏度")
    private String feelsLike;

    /** 当前天气状况和图标的代码 */
    @Excel(name = "当前天气状况和图标的代码")
    private String icon;

    /** 实况天气状况的文字描述 */
    @Excel(name = "实况天气状况的文字描述")
    private String text;

    /** 实况风向360角度 */
    @Excel(name = "实况风向360角度")
    private String wind360;

    /** 实况风向 */
    @Excel(name = "实况风向")
    private String windDir;

    /** 实况风力等级 */
    @Excel(name = "实况风力等级")
    private String windScale;

    /** 实况风速，公里/小时 */
    @Excel(name = "实况风速，公里/小时")
    private String windSpeed;

    /** 实况相对湿度，百分比数值 */
    @Excel(name = "实况相对湿度，百分比数值")
    private String humidity;

    /** 实况降水量，默认单位：毫米 */
    @Excel(name = "实况降水量，默认单位：毫米")
    private String precip;

    /** 实况大气压强，默认单位：百帕 */
    @Excel(name = "实况大气压强，默认单位：百帕")
    private String pressure;

    /** 实况能见度，默认单位：公里 */
    @Excel(name = "实况能见度，默认单位：公里")
    private String vis;

    /** 实况云量，百分比数值 */
    @Excel(name = "实况云量，百分比数值")
    private String cloud;

    /** 实况露点温度 */
    @Excel(name = "实况露点温度")
    private String dew;

    public void setLocationId(Long locationId) 
    {
        this.locationId = locationId;
    }

    public Long getLocationId() 
    {
        return locationId;
    }
    public void setLocationName(String locationName) 
    {
        this.locationName = locationName;
    }

    public String getLocationName() 
    {
        return locationName;
    }
    public void setAdm1(String adm1) 
    {
        this.adm1 = adm1;
    }

    public String getAdm1() 
    {
        return adm1;
    }
    public void setAdm2(String adm2) 
    {
        this.adm2 = adm2;
    }

    public String getAdm2() 
    {
        return adm2;
    }
    public void setTemp(String temp) 
    {
        this.temp = temp;
    }

    public String getTemp() 
    {
        return temp;
    }
    public void setFeelsLike(String feelsLike) 
    {
        this.feelsLike = feelsLike;
    }

    public String getFeelsLike() 
    {
        return feelsLike;
    }
    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }
    public void setText(String text) 
    {
        this.text = text;
    }

    public String getText() 
    {
        return text;
    }
    public void setWind360(String wind360) 
    {
        this.wind360 = wind360;
    }

    public String getWind360() 
    {
        return wind360;
    }
    public void setWindDir(String windDir) 
    {
        this.windDir = windDir;
    }

    public String getWindDir() 
    {
        return windDir;
    }
    public void setWindScale(String windScale) 
    {
        this.windScale = windScale;
    }

    public String getWindScale() 
    {
        return windScale;
    }
    public void setWindSpeed(String windSpeed) 
    {
        this.windSpeed = windSpeed;
    }

    public String getWindSpeed() 
    {
        return windSpeed;
    }
    public void setHumidity(String humidity) 
    {
        this.humidity = humidity;
    }

    public String getHumidity() 
    {
        return humidity;
    }
    public void setPrecip(String precip) 
    {
        this.precip = precip;
    }

    public String getPrecip() 
    {
        return precip;
    }
    public void setPressure(String pressure) 
    {
        this.pressure = pressure;
    }

    public String getPressure() 
    {
        return pressure;
    }
    public void setVis(String vis) 
    {
        this.vis = vis;
    }

    public String getVis() 
    {
        return vis;
    }
    public void setCloud(String cloud) 
    {
        this.cloud = cloud;
    }

    public String getCloud() 
    {
        return cloud;
    }
    public void setDew(String dew) 
    {
        this.dew = dew;
    }

    public String getDew() 
    {
        return dew;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("locationId", getLocationId())
            .append("locationName", getLocationName())
            .append("adm1", getAdm1())
            .append("adm2", getAdm2())
            .append("temp", getTemp())
            .append("feelsLike", getFeelsLike())
            .append("icon", getIcon())
            .append("text", getText())
            .append("wind360", getWind360())
            .append("windDir", getWindDir())
            .append("windScale", getWindScale())
            .append("windSpeed", getWindSpeed())
            .append("humidity", getHumidity())
            .append("precip", getPrecip())
            .append("pressure", getPressure())
            .append("vis", getVis())
            .append("cloud", getCloud())
            .append("dew", getDew())
            .toString();
    }
}
