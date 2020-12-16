package com.ruoyi.hotel.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 酒店信息对象 htl_hotel_info
 * 
 * @author sucheng
 * @date 2020-11-22
 */
public class HtlHotelInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 酒店ID */
    private Long hotelId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 酒店名称 */
    @Excel(name = "酒店名称")
    private String hotelName;

    /** LOGO文件路径 */
    @Excel(name = "LOGO文件路径")
    private String logoPath;

    /** 城市编码 */
    @Excel(name = "城市编码")
    private Long cityId;

    /** 二维码文件路径 */
    @Excel(name = "二维码文件路径")
    private String qrCodePath;

    /** 公告信息 */
    @Excel(name = "公告信息")
    private String notice;

    /** 预定电话 */
    @Excel(name = "预定电话")
    private String reservationPhone;

    /** 服务项目 */
    @Excel(name = "服务项目")
    private String serviceItems;

    /** 生效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectiveTime;

    /** 失效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "失效时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    public void setHotelId(Long hotelId) 
    {
        this.hotelId = hotelId;
    }

    public Long getHotelId() 
    {
        return hotelId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setHotelName(String hotelName) 
    {
        this.hotelName = hotelName;
    }

    public String getHotelName() 
    {
        return hotelName;
    }
    public void setLogoPath(String logoPath) 
    {
        this.logoPath = logoPath;
    }

    public String getLogoPath() 
    {
        return logoPath;
    }
    public void setCityId(Long cityId) 
    {
        this.cityId = cityId;
    }

    public Long getCityId() 
    {
        return cityId;
    }
    public void setQrCodePath(String qrCodePath) 
    {
        this.qrCodePath = qrCodePath;
    }

    public String getQrCodePath() 
    {
        return qrCodePath;
    }
    public void setNotice(String notice) 
    {
        this.notice = notice;
    }

    public String getNotice() 
    {
        return notice;
    }
    public void setReservationPhone(String reservationPhone) 
    {
        this.reservationPhone = reservationPhone;
    }

    public String getReservationPhone() 
    {
        return reservationPhone;
    }
    public void setServiceItems(String serviceItems) 
    {
        this.serviceItems = serviceItems;
    }

    public String getServiceItems() 
    {
        return serviceItems;
    }
    public void setEffectiveTime(Date effectiveTime) 
    {
        this.effectiveTime = effectiveTime;
    }

    public Date getEffectiveTime() 
    {
        return effectiveTime;
    }
    public void setExpireTime(Date expireTime) 
    {
        this.expireTime = expireTime;
    }

    public Date getExpireTime() 
    {
        return expireTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("hotelId", getHotelId())
            .append("userId", getUserId())
            .append("hotelName", getHotelName())
            .append("logoPath", getLogoPath())
            .append("cityId", getCityId())
            .append("qrCodePath", getQrCodePath())
            .append("notice", getNotice())
            .append("reservationPhone", getReservationPhone())
            .append("serviceItems", getServiceItems())
            .append("effectiveTime", getEffectiveTime())
            .append("expireTime", getExpireTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
