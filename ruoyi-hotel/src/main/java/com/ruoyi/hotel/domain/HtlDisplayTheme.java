package com.ruoyi.hotel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主题配置对象 htl_display_theme
 * 
 * @author sucheng
 * @date 2020-11-22
 */
public class HtlDisplayTheme extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 酒店ID */
    private Long hotelId;

    /** 主题名称 */
    @Excel(name = "主题名称")
    private String themeName;

    /** 主题背景图路径 */
    @Excel(name = "主题背景图路径")
    private String themePicturePath;

    public void setHotelId(Long hotelId) 
    {
        this.hotelId = hotelId;
    }

    public Long getHotelId() 
    {
        return hotelId;
    }
    public void setThemeName(String themeName) 
    {
        this.themeName = themeName;
    }

    public String getThemeName() 
    {
        return themeName;
    }
    public void setThemePicturePath(String themePicturePath) 
    {
        this.themePicturePath = themePicturePath;
    }

    public String getThemePicturePath() 
    {
        return themePicturePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("hotelId", getHotelId())
            .append("themeName", getThemeName())
            .append("themePicturePath", getThemePicturePath())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
