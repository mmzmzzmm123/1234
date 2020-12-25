package com.ruoyi.hotel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主题配置对象 htl_theme_config
 * 
 * @author sucheng
 * @date 2020-12-25
 */
public class HtlThemeConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 酒店ID */
    private Long hotelId;

    /** 主题ID */
    @Excel(name = "主题ID")
    private String themeId;

    public void setHotelId(Long hotelId) 
    {
        this.hotelId = hotelId;
    }

    public Long getHotelId() 
    {
        return hotelId;
    }
    public void setThemeId(String themeId) 
    {
        this.themeId = themeId;
    }

    public String getThemeId() 
    {
        return themeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("hotelId", getHotelId())
            .append("themeId", getThemeId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
