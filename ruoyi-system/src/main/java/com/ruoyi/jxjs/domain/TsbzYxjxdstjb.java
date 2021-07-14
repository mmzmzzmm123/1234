package com.ruoyi.jxjs.domain;

import com.ruoyi.jxzxkhgl.domain.TsbzDsjbxx;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 优秀见习导师推荐对象 tsbz_yxjxdstjb
 *
 * @author tsbz
 * @date 2021-07-03
 */
public class TsbzYxjxdstjb extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 优秀导师
     */
//    @Excel(name = "优秀导师")
    private Long dsid;

    public String getDsxm() {
        return dsxm;
    }

    public void setDsxm(String dsxm) {
        this.dsxm = dsxm;
    }

    /**
     * 优秀导师
     */
    @Excel(name = "优秀导师")
    private String dsxm;

    /**
     * 所属部门
     */
//    @Excel(name = "所属部门")
    private Long deptId;

    /**
     * 推荐人
     */
//    @Excel(name = "推荐人")
    private Long createuserid;

    /**
     * 推荐年份
     */
    @Excel(name = "推荐年份")
    private String nf;

    /**
     * 推荐理由
     */
    @Excel(name = "推荐理由")
    private String tjly;

    public TsbzDsjbxx getTsbzDsjbxx() {
        return tsbzDsjbxx;
    }

    public void setTsbzDsjbxx(TsbzDsjbxx tsbzDsjbxx) {
        this.tsbzDsjbxx = tsbzDsjbxx;
    }

    private TsbzDsjbxx tsbzDsjbxx;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDsid(Long dsid) {
        this.dsid = dsid;
    }

    public Long getDsid() {
        return dsid;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getNf() {
        return nf;
    }

    public void setTjly(String tjly) {
        this.tjly = tjly;
    }

    public String getTjly() {
        return tjly;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("dsid", getDsid())
                .append("dsxm", getDsxm())
                .append("deptId", getDeptId())
                .append("createuserid", getCreateuserid())
                .append("nf", getNf())
                .append("tjly", getTjly())
                .append("createTime", getCreateTime())
                .append("tsbzDsjbxx", getTsbzDsjbxx())
                .toString();
    }
}