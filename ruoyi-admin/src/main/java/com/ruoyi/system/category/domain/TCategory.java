package com.ruoyi.system.category.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品分类对象 t_category
 *
 * @author ruoyi
 * @date 2024-04-19
 */
@ApiModel(value = "TCategory", description = "商品分类")
public class TCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @ApiModelProperty("主键id")
    private Long id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    @ApiModelProperty("分类名称")
    private String name;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
