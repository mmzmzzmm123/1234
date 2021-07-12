package com.apollo.usv.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 充电桩对象 usv_charging_pile
 * 
 * @author lwq
 * @date 2021-07-02
 */
public class ChargingPile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long chargingPileId;

    /** 编号 */
    @Excel(name = "编号")
    private String chargingRecordSn;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 维护人 */
    @Excel(name = "维护人")
    private String nickName;

    public void setChargingPileId(Long chargingPileId) 
    {
        this.chargingPileId = chargingPileId;
    }

    public Long getChargingPileId() 
    {
        return chargingPileId;
    }
    public void setChargingRecordSn(String chargingRecordSn) 
    {
        this.chargingRecordSn = chargingRecordSn;
    }

    public String getChargingRecordSn() 
    {
        return chargingRecordSn;
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
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("chargingPileId", getChargingPileId())
            .append("chargingRecordSn", getChargingRecordSn())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("nickName", getNickName())
            .toString();
    }
}
