package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 游戏数学方案对象 by_math_plan
 *
 * @author tsbz
 * @date 2020-07-21
 */
public class ByMathPlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 数学游戏方案编号
     */
    private Long id;

    /**
     * 数学编号
     */
    @Excel(name = "数学编号")
    private Long mathid;

    /**
     * 游戏名称
     */
    @Excel(name = "游戏名称")
    private String name;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;

    /**
     * 游戏形式
     */
    @Excel(name = "游戏形式")
    private String type;

    /**
     * 游戏目标
     */
    @Excel(name = "游戏目标")
    private String target;

    /**
     * 游戏准备
     */
    @Excel(name = "游戏准备")
    private String prepare;

    /**
     * 游戏过程
     */
    @Excel(name = "游戏过程")
    private String process;

    /**
     * 本周其它目标
     */
    @Excel(name = "本周其它目标")
    private String otherTarget;

    /**
     * $column.columnComment
     */
    @Excel(name = "建议")
    private String suggest;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date createtime;



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setMathid(Long mathid) {
        this.mathid = mathid;
    }

    public Long getMathid() {
        return mathid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSort() {
        return sort;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setPrepare(String prepare) {
        this.prepare = prepare;
    }

    public String getPrepare() {
        return prepare;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getProcess() {
        return process;
    }

    public void setOtherTarget(String otherTarget) {
        this.otherTarget = otherTarget;
    }

    public String getOtherTarget() {
        return otherTarget;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getSuggest() {
        return suggest;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("mathid", getMathid())
                .append("name", getName())
                .append("sort", getSort())
                .append("type", getType())
                .append("target", getTarget())
                .append("prepare", getPrepare())
                .append("process", getProcess())
                .append("otherTarget", getOtherTarget())
                .append("suggest", getSuggest())
                .append("createtime", getCreatetime())
                .toString();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
