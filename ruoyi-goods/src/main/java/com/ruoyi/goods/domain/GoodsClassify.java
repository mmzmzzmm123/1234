package com.ruoyi.goods.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 货品分类对象 goods_classify
 *
 * @author lixin
 * @date 2023-02-02
 */

@Data
@TableName("goods_classify")
public class GoodsClassify extends TreeEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 分类id */
    private Long classifyId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String classifyName;

    /** 使用状态（0正常 1停用） */
    @Excel(name = "使用状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setClassifyId(Long classifyId)
    {
        this.classifyId = classifyId;
    }

    public Long getClassifyId()
    {
        return classifyId;
    }
    public void setClassifyName(String classifyName)
    {
        this.classifyName = classifyName;
    }

    public String getClassifyName()
    {
        return classifyName;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("classifyId", getClassifyId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("classifyName", getClassifyName())
            .append("orderNum", getOrderNum())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
