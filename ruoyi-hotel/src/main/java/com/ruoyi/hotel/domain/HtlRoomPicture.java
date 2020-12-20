package com.ruoyi.hotel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房间图片对象 htl_room_picture
 * 
 * @author sucheng
 * @date 2020-12-20
 */
public class HtlRoomPicture extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图片ID */
    private Long pictureId;

    /** 酒店ID */
    @Excel(name = "酒店ID")
    private Long hotelId;

    /** 图片存储路径 */
    @Excel(name = "图片存储路径")
    private String picturePath;

    /** 图片名称 */
    @Excel(name = "图片名称")
    private String pictureName;

    /** 排列序号 */
    @Excel(name = "排列序号")
    private Integer orderNum;

    /** 状态（0：启用；1：停用） */
    @Excel(name = "状态", readConverterExp = "0=：启用；1：停用")
    private Integer status;

    public void setPictureId(Long pictureId) 
    {
        this.pictureId = pictureId;
    }

    public Long getPictureId() 
    {
        return pictureId;
    }
    public void setHotelId(Long hotelId) 
    {
        this.hotelId = hotelId;
    }

    public Long getHotelId() 
    {
        return hotelId;
    }
    public void setPicturePath(String picturePath) 
    {
        this.picturePath = picturePath;
    }

    public String getPicturePath() 
    {
        return picturePath;
    }
    public void setPictureName(String pictureName) 
    {
        this.pictureName = pictureName;
    }

    public String getPictureName() 
    {
        return pictureName;
    }
    public void setOrderNum(Integer orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() 
    {
        return orderNum;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("pictureId", getPictureId())
            .append("hotelId", getHotelId())
            .append("picturePath", getPicturePath())
            .append("pictureName", getPictureName())
            .append("orderNum", getOrderNum())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
