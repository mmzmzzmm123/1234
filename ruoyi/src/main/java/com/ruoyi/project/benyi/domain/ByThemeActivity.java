package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 主题整合活动对象 by_theme_activity
 *
 * @author tsbz
 * @date 2020-07-01
 */
public class ByThemeActivity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 所属主题
     */
    @Excel(name = "所属主题")
    private Long themeid;

    /**
     * 活动名称
     */
    @Excel(name = "活动名称")
    private String name;

    /**
     * 活动形式
     */
    @Excel(name = "活动形式")
    private String type;

    /**
     * 活动领域
     */
    @Excel(name = "活动领域")
    private String field;

    /**
     * 活动目标
     */
    @Excel(name = "活动目标")
    private String target;

    /**
     * 活动材料
     */
    @Excel(name = "活动材料")
    private String data;

    /**
     * 活动过程
     */
    @Excel(name = "活动过程")
    private String process;

    /**
     * 活动建议
     */
    @Excel(name = "活动建议")
    private String proposal;

    /**
     * 活动反思
     */
    @Excel(name = "活动反思")
    private String reflect;

    /**
     * 活动附录
     */
    @Excel(name = "活动附录")
    private String appendix;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long sort;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setThemeid(Long themeid) {
        this.themeid = themeid;
    }

    public Long getThemeid() {
        return themeid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getProcess() {
        return process;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public String getProposal() {
        return proposal;
    }

    public void setReflect(String reflect) {
        this.reflect = reflect;
    }

    public String getReflect() {
        return reflect;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSort() {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("themeid", getThemeid())
                .append("name", getName())
                .append("type", getType())
                .append("field", getField())
                .append("target", getTarget())
                .append("data", getData())
                .append("process", getProcess())
                .append("proposal", getProposal())
                .append("reflect", getReflect())
                .append("appendix", getAppendix())
                .append("sort", getSort())
                .append("createTime", getCreateTime())
                .toString();
    }
}