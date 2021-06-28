package com.ruoyi.jxjs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 见习教师导师分配对象 tsbz_jxjsdsfp
 *
 * @author tsbz
 * @date 2021-06-28
 */
public class TsbzJxjsdsfp extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 见习之星编号
     */
    @Excel(name = "见习之星编号")
    private Long jxjsid;

    /**
     * 导师编号
     */
    @Excel(name = "导师编号")
    private Long dsid;

    private TsbzJxjsjbxx tsbzJxjsjbxx;

    private String jdxmc;

    public String getRjxd() {
        return rjxd;
    }

    public void setRjxd(String rjxd) {
        this.rjxd = rjxd;
    }

    public String getRjxk() {
        return rjxk;
    }

    public void setRjxk(String rjxk) {
        this.rjxk = rjxk;
    }

    private String rjxd;
    private String rjxk;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setJxjsid(Long jxjsid) {
        this.jxjsid = jxjsid;
    }

    public Long getJxjsid() {
        return jxjsid;
    }

    public void setDsid(Long dsid) {
        this.dsid = dsid;
    }

    public Long getDsid() {
        return dsid;
    }

    public TsbzJxjsjbxx getTsbzJxjsjbxx() {
        return tsbzJxjsjbxx;
    }

    public void setTsbzJxjsjbxx(TsbzJxjsjbxx tsbzJxjsjbxx) {
        this.tsbzJxjsjbxx = tsbzJxjsjbxx;
    }

    public String getJdxmc() {
        return jdxmc;
    }

    public void setJdxmc(String jdxmc) {
        this.jdxmc = jdxmc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("jxjsid", getJxjsid())
                .append("dsid", getDsid())
                .append("createTime", getCreateTime())
                .append("tsbzJxjsjbxx", getTsbzJxjsjbxx())
                .append("jdxmc", getJdxmc())
                .append("rjxd", getRjxd())
                .append("rjxk", getRjxk())
                .toString();
    }
}
