package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 幼儿园一日流程评估得分项对象 by_dayflowassessmentitem
 *
 * @author tsbz
 * @date 2021-04-05
 */
public class ByDayflowassessmentitem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 所属评估环节
     */
    @Excel(name = "所属评估环节")
    private Long pid;

    /**
     * 标准编号
     */
    @Excel(name = "标准编号")
    private Long item;

    /**
     * 得分
     */
    @Excel(name = "得分")
    private Double value;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getPid() {
        return pid;
    }

    public void setItem(Long item) {
        this.item = item;
    }

    public Long getItem() {
        return item;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("pid", getPid())
                .append("item", getItem())
                .append("value", getValue())
                .append("remark", getRemark())
                .append("createTime", getCreateTime())
                .toString();
    }
}
