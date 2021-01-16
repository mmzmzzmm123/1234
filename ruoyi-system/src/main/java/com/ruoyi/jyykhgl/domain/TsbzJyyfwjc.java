package com.ruoyi.jyykhgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务基层（教研员）对象 tsbz_jyyfwjc
 *
 * @author tsbz
 * @date 2021-01-16
 */
public class TsbzJyyfwjc extends BaseEntity {
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
     * 服务学校
     */
    @Excel(name = "服务学校")
    private String fwxx;

    /**
     * 服务时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "服务时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fwsj;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String bz;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

    /**
     * 部门编号
     */
    @Excel(name = "部门编号")
    private Long deptId;

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

    public void setFwxx(String fwxx) {
        this.fwxx = fwxx;
    }

    public String getFwxx() {
        return fwxx;
    }

    public void setFwsj(Date fwsj) {
        this.fwsj = fwsj;
    }

    public Date getFwsj() {
        return fwsj;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getBz() {
        return bz;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("rwid", getRwid())
                .append("rwnrlx", getRwnrlx())
                .append("zt", getZt())
                .append("fwxx", getFwxx())
                .append("fwsj", getFwsj())
                .append("bz", getBz())
                .append("createUserid", getCreateUserid())
                .append("deptId", getDeptId())
                .append("createTime", getCreateTime())
                .toString();
    }
}
