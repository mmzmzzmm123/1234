package com.apollo.usv.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 水质记录对象 usv_collector_record
 * 
 * @author lwq
 * @date 2021-07-02
 */
public class CollectorRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 航船编号 */
    @Excel(name = "航船编号")
    private String sn;

    /** 溶氧值 */
    @Excel(name = "溶氧值")
    private Double dissolvedOxygen;

    /** 电导率 */
    @Excel(name = "电导率")
    private Double conductivity;

    /** 浊度值 */
    @Excel(name = "浊度值")
    private Double turbidity;

    /** PH值 */
    @Excel(name = "PH值")
    private Double ph;

    /** COD值 */
    @Excel(name = "COD值")
    private Double cod;

    /** 氨氮值 */
    @Excel(name = "氨氮值")
    private Double ammoniaNitrogen;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

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
    public void setSn(String sn) 
    {
        this.sn = sn;
    }

    public String getSn() 
    {
        return sn;
    }
    public void setDissolvedOxygen(Double dissolvedOxygen) 
    {
        this.dissolvedOxygen = dissolvedOxygen;
    }

    public Double getDissolvedOxygen() 
    {
        return dissolvedOxygen;
    }
    public void setConductivity(Double conductivity) 
    {
        this.conductivity = conductivity;
    }

    public Double getConductivity() 
    {
        return conductivity;
    }
    public void setTurbidity(Double turbidity) 
    {
        this.turbidity = turbidity;
    }

    public Double getTurbidity() 
    {
        return turbidity;
    }
    public void setPh(Double ph) 
    {
        this.ph = ph;
    }

    public Double getPh() 
    {
        return ph;
    }
    public void setCod(Double cod) 
    {
        this.cod = cod;
    }

    public Double getCod() 
    {
        return cod;
    }
    public void setAmmoniaNitrogen(Double ammoniaNitrogen) 
    {
        this.ammoniaNitrogen = ammoniaNitrogen;
    }

    public Double getAmmoniaNitrogen() 
    {
        return ammoniaNitrogen;
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
            .append("sn", getSn())
            .append("dissolvedOxygen", getDissolvedOxygen())
            .append("conductivity", getConductivity())
            .append("turbidity", getTurbidity())
            .append("ph", getPh())
            .append("cod", getCod())
            .append("ammoniaNitrogen", getAmmoniaNitrogen())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("time", getTime())
            .toString();
    }
}
