package com.ruoyi.project.benyi.domain;

import com.ruoyi.project.system.domain.ByClass;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 儿童学习与发展档案（家长）对象 by_child_learndevelopment_family
 *
 * @author tsbz
 * @date 2020-08-10
 */
public class ByChildLearndevelopmentFamily extends BaseEntity {
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
     * 学校编码
     */
    @Excel(name = "学校编码")
    private Long schoolid;

    /**
     * 班级编码
     */
    @Excel(name = "班级编码")
    private String classid;

    /**
     * 学年学期
     */
    @Excel(name = "学年学期")
    private String xnxq;

    /**
     * 作品照片
     */
    @Excel(name = "作品照片")
    private String zpimgs;

    /**
     * 作品照片备注
     */
    @Excel(name = "作品照片备注")
    private String zpimgremarks;

    /**
     * 生活照片
     */
    @Excel(name = "生活照片")
    private String shimgs;

    /**
     * 生活照片备注
     */
    @Excel(name = "生活照片备注")
    private String shimgsremarks;

    /**
     * 有趣事件
     */
    @Excel(name = "有趣事件")
    private String yqsj;

    /**
     * 有趣事件备注
     */
    @Excel(name = "有趣事件备注")
    private String yqsjremarks;

    /**
     * 教师评语
     */
    @Excel(name = "教师评语")
    private String jspy;

    /**
     * 教师评语备注
     */
    @Excel(name = "教师评语备注")
    private String jspyremarks;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    public ByClass getByClass() {
        return byClass;
    }

    public void setByClass(ByClass byClass) {
        this.byClass = byClass;
    }

    private ByClass byClass;

    public ByChild getByChild() {
        return byChild;
    }

    public void setByChild(ByChild byChild) {
        this.byChild = byChild;
    }

    private ByChild byChild;

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

    public void setSchoolid(Long schoolid) {
        this.schoolid = schoolid;
    }

    public Long getSchoolid() {
        return schoolid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid() {
        return classid;
    }

    public void setXnxq(String xnxq) {
        this.xnxq = xnxq;
    }

    public String getXnxq() {
        return xnxq;
    }

    public void setZpimgs(String zpimgs) {
        this.zpimgs = zpimgs;
    }

    public String getZpimgs() {
        return zpimgs;
    }

    public void setZpimgremarks(String zpimgremarks) {
        this.zpimgremarks = zpimgremarks;
    }

    public String getZpimgremarks() {
        return zpimgremarks;
    }

    public void setShimgs(String shimgs) {
        this.shimgs = shimgs;
    }

    public String getShimgs() {
        return shimgs;
    }

    public void setShimgsremarks(String shimgsremarks) {
        this.shimgsremarks = shimgsremarks;
    }

    public String getShimgsremarks() {
        return shimgsremarks;
    }

    public void setYqsj(String yqsj) {
        this.yqsj = yqsj;
    }

    public String getYqsj() {
        return yqsj;
    }

    public void setYqsjremarks(String yqsjremarks) {
        this.yqsjremarks = yqsjremarks;
    }

    public String getYqsjremarks() {
        return yqsjremarks;
    }

    public void setJspy(String jspy) {
        this.jspy = jspy;
    }

    public String getJspy() {
        return jspy;
    }

    public void setJspyremarks(String jspyremarks) {
        this.jspyremarks = jspyremarks;
    }

    public String getJspyremarks() {
        return jspyremarks;
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
                .append("schoolid", getSchoolid())
                .append("classid", getClassid())
                .append("xnxq", getXnxq())
                .append("zpimgs", getZpimgs())
                .append("zpimgremarks", getZpimgremarks())
                .append("shimgs", getShimgs())
                .append("shimgsremarks", getShimgsremarks())
                .append("yqsj", getYqsj())
                .append("yqsjremarks", getYqsjremarks())
                .append("jspy", getJspy())
                .append("jspyremarks", getJspyremarks())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("byClass", getByClass())
                .append("byChild", getByChild())
                .toString();
    }
}
