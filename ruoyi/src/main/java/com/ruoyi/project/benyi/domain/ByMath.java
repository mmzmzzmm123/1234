package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 游戏数学对象 by_math
 *
 * @author tsbz
 * @date 2020-07-20
 */
public class ByMath extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 学习目标
     */
    @Excel(name = "学习目标")
    private String target;

    /**
     * 年龄段表现特征
     */
    @Excel(name = "年龄段表现特征")
    private String feature;

    /**
     * 教学建议
     */
    @Excel(name = "教学建议")
    private String suggest;

    /**
     * 适用班级
     */
    @Excel(name = "适用班级")
    private Integer classtypeId;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long sort;

    /** 树状父类ID */
    private Long parentId;

    /** 树状子类 */
    private List<ByMath> children = new ArrayList<ByMath>();

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getFeature() {
        return feature;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setClasstypeId(Integer classtypeId) {
        this.classtypeId = classtypeId;
    }

    public Integer getClasstypeId() {
        return classtypeId;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSort() {
        return sort;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<ByMath> getChildren() {
        return children;
    }

    public void setChildren(List<ByMath> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("target", getTarget())
                .append("feature", getFeature())
                .append("suggest", getSuggest())
                .append("classtypeId", getClasstypeId())
                .append("sort", getSort())
                .append("createTime", getCreateTime())
                .append("parentId", getParentId())
                .toString();
    }


}
