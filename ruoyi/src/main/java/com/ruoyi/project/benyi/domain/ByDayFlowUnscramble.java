package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 一日流程解读对象 by_day_flow_unscramble
 *
 * @author tsbz
 * @date 2020-06-04
 */
public class ByDayFlowUnscramble extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 标识
     */
    private Long id;

    /**
     * 标准id
     */
    @Excel(name = "标准id")
    private Long standardId;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long sort;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStandardId(Long standardId) {
        this.standardId = standardId;
    }

    public Long getStandardId() {
        return standardId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSort() {
        return sort;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("standardId", getStandardId())
                .append("content", getContent())
                .append("sort", getSort())
                .append("remark", getRemark())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
