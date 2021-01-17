package com.ruoyi.jyykhgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 计划总结（教研员）对象 tsbz_jyyjhzj
 *
 * @author tsbz
 * @date 2021-01-15
 */
public class TsbzJyyjhzj extends BaseEntity {
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
     * 学年
     */
    @Excel(name = "学年")
    private String xn;

    /**
     * 学期
     */
    @Excel(name = "学期")
    private String xq;

    /**
     * 附件
     */
    @Excel(name = "附件")
    private String filepath;

    /**
     * 部门编号
     */
    @Excel(name = "部门编号")
    private Long deptId;

    /**
     * 填报人
     */
    @Excel(name = "填报人")
    private Long createUserid;

    /**
     * 附件名称
     */
    @Excel(name = "附件名称")
    private String filename;

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

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getXn() {
        return xn;
    }

    public void setXq(String xq) {
        this.xq = xq;
    }

    public String getXq() {
        return xq;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
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

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
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
                .append("xn", getXn())
                .append("xq", getXq())
                .append("filepath", getFilepath())
                .append("deptId", getDeptId())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("filename", getFilename())
                .append("tsbzJyykhrw", getTsbzJyykhrw())
                .toString();
    }
}
