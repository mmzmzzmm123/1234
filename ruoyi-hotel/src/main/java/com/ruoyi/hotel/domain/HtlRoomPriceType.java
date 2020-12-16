package com.ruoyi.hotel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房价类型对象 htl_room_price_type
 * 
 * @author sucheng
 * @date 2020-11-22
 */
public class HtlRoomPriceType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 酒店ID */
    private Long hotelId;

    /** 房价类别1 */
    @Excel(name = "房价类别1")
    private String type1;

    /** 是否展示类别1 */
    @Excel(name = "是否展示类别1")
    private Integer type1Visible;

    /** 房价类别2 */
    @Excel(name = "房价类别2")
    private String type2;

    /** 是否展示类别2 */
    @Excel(name = "是否展示类别2")
    private Integer type2Visible;

    /** 房价类别3 */
    @Excel(name = "房价类别3")
    private String type3;

    /** 是否展示类别3 */
    @Excel(name = "是否展示类别3")
    private Integer type3Visible;

    /** 房价类别4 */
    @Excel(name = "房价类别4")
    private String type4;

    /** 是否展示类别4 */
    @Excel(name = "是否展示类别4")
    private Integer type4Visible;

    public void setHotelId(Long hotelId) 
    {
        this.hotelId = hotelId;
    }

    public Long getHotelId() 
    {
        return hotelId;
    }
    public void setType1(String type1) 
    {
        this.type1 = type1;
    }

    public String getType1() 
    {
        return type1;
    }
    public void setType1Visible(Integer type1Visible) 
    {
        this.type1Visible = type1Visible;
    }

    public Integer getType1Visible() 
    {
        return type1Visible;
    }
    public void setType2(String type2) 
    {
        this.type2 = type2;
    }

    public String getType2() 
    {
        return type2;
    }
    public void setType2Visible(Integer type2Visible) 
    {
        this.type2Visible = type2Visible;
    }

    public Integer getType2Visible() 
    {
        return type2Visible;
    }
    public void setType3(String type3) 
    {
        this.type3 = type3;
    }

    public String getType3() 
    {
        return type3;
    }
    public void setType3Visible(Integer type3Visible) 
    {
        this.type3Visible = type3Visible;
    }

    public Integer getType3Visible() 
    {
        return type3Visible;
    }
    public void setType4(String type4) 
    {
        this.type4 = type4;
    }

    public String getType4() 
    {
        return type4;
    }
    public void setType4Visible(Integer type4Visible) 
    {
        this.type4Visible = type4Visible;
    }

    public Integer getType4Visible() 
    {
        return type4Visible;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("hotelId", getHotelId())
            .append("type1", getType1())
            .append("type1Visible", getType1Visible())
            .append("type2", getType2())
            .append("type2Visible", getType2Visible())
            .append("type3", getType3())
            .append("type3Visible", getType3Visible())
            .append("type4", getType4())
            .append("type4Visible", getType4Visible())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
