package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 幼儿考勤对象 by_child_checkin_detail
 *
 * @author tsbz
 * @date 2020-07-21
 */
public class ByChildCheckinDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

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
     * 幼儿编码
     */
    @Excel(name = "幼儿编码")
    private Long childid;

    /**
     * 幼儿姓名
     */
    @Excel(name = "幼儿姓名")
    private String childname;

    /**
     * 出勤类型
     */
    @Excel(name = "出勤类型")
    private String type;

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

    public void setChildid(Long childid) {
        this.childid = childid;
    }

    public Long getChildid() {
        return childid;
    }

    public void setChildname(String childname) {
        this.childname = childname;
    }

    public String getChildname() {
        return childname;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
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
                .append("schoolid", getSchoolid())
                .append("classid", getClassid())
                .append("childid", getChildid())
                .append("childname", getChildname())
                .append("type", getType())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
