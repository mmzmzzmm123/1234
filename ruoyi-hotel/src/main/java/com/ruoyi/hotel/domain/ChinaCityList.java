package com.ruoyi.hotel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 中国城市列对象 china_city_list
 * 
 * @author sucheng
 * @date 2020-12-28
 */
public class ChinaCityList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 地区/城市ID */
    private Long locationId;

    /** 地区/城市英文名称 */
    @Excel(name = "地区/城市英文名称")
    private String locationNameEn;

    /** 地区/城市名称 */
    @Excel(name = "地区/城市名称")
    private String locationName;

    /** 该地区/城市所属国家代码 */
    @Excel(name = "该地区/城市所属国家代码")
    private String countryCode;

    /** 该地区/城市所属国家英文名称 */
    @Excel(name = "该地区/城市所属国家英文名称")
    private String countryEn;

    /** 该地区/城市所属国家名称 */
    @Excel(name = "该地区/城市所属国家名称")
    private String country;

    /** 该地区/城市所属一级行政区域英文名称 */
    @Excel(name = "该地区/城市所属一级行政区域英文名称")
    private String adm1En;

    /** 该地区/城市所属一级行政区域名称 */
    @Excel(name = "该地区/城市所属一级行政区域名称")
    private String adm1;

    /** 该地区/城市的上级行政区划名称英文 */
    @Excel(name = "该地区/城市的上级行政区划名称英文")
    private String adm2En;

    /** 该地区/城市的上级行政区划名称 */
    @Excel(name = "该地区/城市的上级行政区划名称")
    private String adm2;

    /** 地区/城市纬度 */
    @Excel(name = "地区/城市纬度")
    private String latitude;

    /** 地区/城市经度 */
    @Excel(name = "地区/城市经度")
    private String longitude;

    /** 地区/城市地址码 */
    @Excel(name = "地区/城市地址码")
    private String adCode;

    public void setLocationId(Long locationId) 
    {
        this.locationId = locationId;
    }

    public Long getLocationId() 
    {
        return locationId;
    }
    public void setLocationNameEn(String locationNameEn) 
    {
        this.locationNameEn = locationNameEn;
    }

    public String getLocationNameEn() 
    {
        return locationNameEn;
    }
    public void setLocationName(String locationName) 
    {
        this.locationName = locationName;
    }

    public String getLocationName() 
    {
        return locationName;
    }
    public void setCountryCode(String countryCode) 
    {
        this.countryCode = countryCode;
    }

    public String getCountryCode() 
    {
        return countryCode;
    }
    public void setCountryEn(String countryEn) 
    {
        this.countryEn = countryEn;
    }

    public String getCountryEn() 
    {
        return countryEn;
    }
    public void setCountry(String country) 
    {
        this.country = country;
    }

    public String getCountry() 
    {
        return country;
    }
    public void setAdm1En(String adm1En) 
    {
        this.adm1En = adm1En;
    }

    public String getAdm1En() 
    {
        return adm1En;
    }
    public void setAdm1(String adm1) 
    {
        this.adm1 = adm1;
    }

    public String getAdm1() 
    {
        return adm1;
    }
    public void setAdm2En(String adm2En) 
    {
        this.adm2En = adm2En;
    }

    public String getAdm2En() 
    {
        return adm2En;
    }
    public void setAdm2(String adm2) 
    {
        this.adm2 = adm2;
    }

    public String getAdm2() 
    {
        return adm2;
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
    public void setAdCode(String adCode) 
    {
        this.adCode = adCode;
    }

    public String getAdCode() 
    {
        return adCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("locationId", getLocationId())
            .append("locationNameEn", getLocationNameEn())
            .append("locationName", getLocationName())
            .append("countryCode", getCountryCode())
            .append("countryEn", getCountryEn())
            .append("country", getCountry())
            .append("adm1En", getAdm1En())
            .append("adm1", getAdm1())
            .append("adm2En", getAdm2En())
            .append("adm2", getAdm2())
            .append("latitude", getLatitude())
            .append("longitude", getLongitude())
            .append("adCode", getAdCode())
            .toString();
    }
}
