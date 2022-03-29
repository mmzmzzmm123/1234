package com.ruoyi.pay.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 支付相关对象 sys_pay
 * 
 * @author ruoyi
 * @date 2022-03-29
 */
public class SysPay extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 支付id */
    private Long id;

    /** 支付名称 */
    @Excel(name = "支付名称")
    private String name;

    /** 支付状态 */
    @Excel(name = "支付状态")
    private String state;

    /** 金额 */
    @Excel(name = "金额")
    private String figure;

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
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setFigure(String figure) 
    {
        this.figure = figure;
    }

    public String getFigure() 
    {
        return figure;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("state", getState())
            .append("figure", getFigure())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
