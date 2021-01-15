package com.ruoyi.jyykhgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 队伍建设（教研员）对象 tsbz_jyydwjs
 *
 * @author tsbz
 * @date 2021-01-15
 */
public class TsbzJyydwjs extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 所属任务
     */
    @Excel(name = "所属任务")
    private Long rwid;

    /**
     * 任务内容
     */
    @Excel(name = "任务内容")
    private String rwnrlx;

    /**
     * 主题
     */
    @Excel(name = "主题")
    private String zt;

    /**
     * 参与人员
     */
    @Excel(name = "参与人员")
    private String cyry;

    /**
     * 活动地点
     */
    @Excel(name = "活动地点")
    private String hddd;

    /**
     * 活动时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "活动时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hdsj;

    /**
     * 部门编号
     */
    @Excel(name = "部门编号")
    private Long deptId;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRwid(Long rwid) {
        this.rwid = rwid;
    }

    public Long getRwid() {
        return rwid;
    }

    public void setRwnrlx(String rwnrlx) {
        this.rwnrlx = rwnrlx;
    }

    public String getRwnrlx() {
        return rwnrlx;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getZt() {
        return zt;
    }

    public void setCyry(String cyry) {
        this.cyry = cyry;
    }

    public String getCyry() {
        return cyry;
    }

    public void setHddd(String hddd) {
        this.hddd = hddd;
    }

    public String getHddd() {
        return hddd;
    }

    public void setHdsj(Date hdsj) {
        this.hdsj = hdsj;
    }

    public Date getHdsj() {
        return hdsj;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("rwid", getRwid())
                .append("rwnrlx", getRwnrlx())
                .append("zt", getZt())
                .append("cyry", getCyry())
                .append("hddd", getHddd())
                .append("hdsj", getHdsj())
                .append("deptId", getDeptId())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
