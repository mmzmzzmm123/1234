package com.ruoyi.hotel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主题详情对象 htl_theme_info
 * 
 * @author sucheng
 * @date 2020-12-25
 */
public class HtlThemeInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主题ID */
    private Long themeId;

    /** 主题名称 */
    @Excel(name = "主题名称")
    private String themeName;

    /** 主题版式（0：横版；1：竖版） */
    @Excel(name = "主题版式", readConverterExp = "0=：横版；1：竖版")
    private Integer themeLayout;

    /** 主题背景图路径 */
    @Excel(name = "主题背景图路径")
    private String themePicturePath;

    public void setThemeId(Long themeId) 
    {
        this.themeId = themeId;
    }

    public Long getThemeId() 
    {
        return themeId;
    }
    public void setThemeName(String themeName) 
    {
        this.themeName = themeName;
    }

    public String getThemeName() 
    {
        return themeName;
    }
    public void setThemeLayout(Integer themeLayout) 
    {
        this.themeLayout = themeLayout;
    }

    public Integer getThemeLayout() 
    {
        return themeLayout;
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
            .append("themeId", getThemeId())
            .append("themeName", getThemeName())
            .append("themeLayout", getThemeLayout())
            .append("themePicturePath", getThemePicturePath())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
