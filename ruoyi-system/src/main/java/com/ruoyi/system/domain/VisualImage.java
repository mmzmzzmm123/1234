package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.Min;

/**
 * 图片管理。管理上传的图片对象 visual_image
 *
 * @author yangsj
 * @date 2021-10-28
 */
public class VisualImage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 原始文件名 */
    @Excel(name = "原始文件名")
    private String originName;

    /** 新生成的文件名 */
    @Excel(name = "新生成的文件名")
    private String newName;

    /** 沿宽度切割的个数 */
    @Excel(name = "沿宽度切割的个数")
    @Min(value = 1)
    private Integer width;

    /** 沿高度切割的个数 */
    @Excel(name = "沿高度切割的个数")
    @Min(value = 1)
    private Integer height;

    /** 描述信息 */
    @Excel(name = "描述信息")
    private String descri;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOriginName(String originName)
    {
        this.originName = originName;
    }

    public String getOriginName()
    {
        return originName;
    }
    public void setNewName(String newName)
    {
        this.newName = newName;
    }

    public String getNewName()
    {
        return newName;
    }
    public void setWidth(Integer width)
    {
        this.width = width;
    }

    public Integer getWidth()
    {
        return width;
    }
    public void setHeight(Integer height)
    {
        this.height = height;
    }

    public Integer getHeight()
    {
        return height;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("originName", getOriginName())
            .append("newName", getNewName())
            .append("width", getWidth())
            .append("height", getHeight())
            .append("descri", getDescri())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
