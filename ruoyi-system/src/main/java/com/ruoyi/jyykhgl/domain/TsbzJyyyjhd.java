package com.ruoyi.jyykhgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 研究活动（教研员）对象 tsbz_jyyyjhd
 *
 * @author tsbz
 * @date 2021-01-15
 */
public class TsbzJyyyjhd extends BaseEntity {
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
     * 活动名称
     */
    @Excel(name = "活动名称")
    private String hdmc;

    /**
     * 活动主题
     */
    @Excel(name = "活动主题")
    private String hdzt;

    /**
     * 活动时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "活动时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hdsj;

    /**
     * 活动地点
     */
    @Excel(name = "活动地点")
    private String hddd;

    /**
     * 附件
     */
    @Excel(name = "附件")
    private String filepath;

    /**
     * 附件名称
     */
    @Excel(name = "附件名称")
    private String filename;

    /**
     * 部门
     */
    @Excel(name = "部门")
    private Long deptId;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

    private TsbzJyykhrw tsbzJyykhrw;

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

    public void setHdmc(String hdmc) {
        this.hdmc = hdmc;
    }

    public String getHdmc() {
        return hdmc;
    }

    public void setHdzt(String hdzt) {
        this.hdzt = hdzt;
    }

    public String getHdzt() {
        return hdzt;
    }

    public void setHdsj(Date hdsj) {
        this.hdsj = hdsj;
    }

    public Date getHdsj() {
        return hdsj;
    }

    public void setHddd(String hddd) {
        this.hddd = hddd;
    }

    public String getHddd() {
        return hddd;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
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

    public void setTsbzJyykhrw(TsbzJyykhrw tsbzJyykhrw) {
        this.tsbzJyykhrw = tsbzJyykhrw;
    }

    public TsbzJyykhrw getTsbzJyykhrw() {
        return tsbzJyykhrw;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("rwid", getRwid())
                .append("rwnrlx", getRwnrlx())
                .append("hdmc", getHdmc())
                .append("hdzt", getHdzt())
                .append("hdsj", getHdsj())
                .append("hddd", getHddd())
                .append("filepath", getFilepath())
                .append("filename", getFilename())
                .append("deptId", getDeptId())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("tsbzJyykhrw", getTsbzJyykhrw())
                .toString();
    }
}
