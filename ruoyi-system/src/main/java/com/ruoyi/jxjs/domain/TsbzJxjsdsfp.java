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
    //@Excel(name = "见习之星编号")
    private Long jxjsid;

    /**
     * 见习之星编号
     */
    @Excel(name = "见习教师")
    private String jsname;

    /**
     * 导师编号
     */
    //@Excel(name = "导师编号")
    private Long dsid;

    public String getJsname() {
        return jsname;
    }

    public void setJsname(String jsname) {
        this.jsname = jsname;
    }

    public String getDsname() {
        return dsname;
    }

    public void setDsname(String dsname) {
        this.dsname = dsname;
    }

    /**
     * 导师编号
     */
    @Excel(name = "导师姓名")
    private String dsname;

    private TsbzJxjsjbxx tsbzJxjsjbxx;

    @Excel(name = "基地校名称")
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
                .append("dsname", getDsname())
                .append("jsname", getJsname())
                .append("rjxd", getRjxd())
                .append("rjxk", getRjxk())
                .toString();
    }
}
