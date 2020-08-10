package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 儿童学习与发展档案（教师）对象 by_child_learndevelopment_teacher
 *
 * @author tsbz
 * @date 2020-08-10
 */
public class ByChildLearndevelopmentTeacher extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 幼儿id
     */
    @Excel(name = "幼儿id")
    private Long childid;

    /**
     * 学年学期
     */
    @Excel(name = "学年学期")
    private String xnxq;

    /**
     * 观察记录
     */
    @Excel(name = "观察记录")
    private String gcjl;

    /**
     * 观察记录备注
     */
    @Excel(name = "观察记录备注")
    private String gcjlremarks;

    /**
     * 问题与方案
     */
    @Excel(name = "问题与方案")
    private String wtyfa;

    /**
     * 问题与方案备注
     */
    @Excel(name = "问题与方案备注")
    private String wtyfaremarks;

    /**
     * 评估结果
     */
    @Excel(name = "评估结果")
    private String pgjg;

    /**
     * 评估结果备注
     */
    @Excel(name = "评估结果备注")
    private String pgjgremarks;

    /**
     * 教育计划
     */
    @Excel(name = "教育计划")
    private String jyjh;

    /**
     * 教育计划备注
     */
    @Excel(name = "教育计划备注")
    private String jyjhremarks;

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

    public void setChildid(Long childid) {
        this.childid = childid;
    }

    public Long getChildid() {
        return childid;
    }

    public void setXnxq(String xnxq) {
        this.xnxq = xnxq;
    }

    public String getXnxq() {
        return xnxq;
    }

    public void setGcjl(String gcjl) {
        this.gcjl = gcjl;
    }

    public String getGcjl() {
        return gcjl;
    }

    public void setGcjlremarks(String gcjlremarks) {
        this.gcjlremarks = gcjlremarks;
    }

    public String getGcjlremarks() {
        return gcjlremarks;
    }

    public void setWtyfa(String wtyfa) {
        this.wtyfa = wtyfa;
    }

    public String getWtyfa() {
        return wtyfa;
    }

    public void setWtyfaremarks(String wtyfaremarks) {
        this.wtyfaremarks = wtyfaremarks;
    }

    public String getWtyfaremarks() {
        return wtyfaremarks;
    }

    public void setPgjg(String pgjg) {
        this.pgjg = pgjg;
    }

    public String getPgjg() {
        return pgjg;
    }

    public void setPgjgremarks(String pgjgremarks) {
        this.pgjgremarks = pgjgremarks;
    }

    public String getPgjgremarks() {
        return pgjgremarks;
    }

    public void setJyjh(String jyjh) {
        this.jyjh = jyjh;
    }

    public String getJyjh() {
        return jyjh;
    }

    public void setJyjhremarks(String jyjhremarks) {
        this.jyjhremarks = jyjhremarks;
    }

    public String getJyjhremarks() {
        return jyjhremarks;
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
                .append("childid", getChildid())
                .append("xnxq", getXnxq())
                .append("gcjl", getGcjl())
                .append("gcjlremarks", getGcjlremarks())
                .append("wtyfa", getWtyfa())
                .append("wtyfaremarks", getWtyfaremarks())
                .append("pgjg", getPgjg())
                .append("pgjgremarks", getPgjgremarks())
                .append("jyjh", getJyjh())
                .append("jyjhremarks", getJyjhremarks())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
