package com.ruoyi.jxjs.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 见习教师成绩对象 tsbz_jxjscj
 *
 * @author ruoyi
 * @date 2020-11-09
 */
public class TsbzJxjscj extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 方案编号
     */
//    @Excel(name = "方案编号")
    private Long faid;

    @Excel(name = "方案名称", type = Excel.Type.EXPORT)
    private String faname;

    /**
     * 教师编号
     */
    @Excel(name = "教师编号", type = Excel.Type.IMPORT)
    private Long jsid;

    @Excel(name = "教师姓名")
    private String jsname;

    /**
     * 基地排序
     */
    @Excel(name = "基地排序")
    private Long jdpx;

    /**
     * 案例分析得分
     */
    @Excel(name = "案例分析得分", type = Excel.Type.IMPORT)
    private BigDecimal alfxdf;

    /**
     * 教案设计得分
     */
    @Excel(name = "教案设计得分", type = Excel.Type.IMPORT)
    private BigDecimal jasjdf;

    /**
     * 钢笔字得分
     */
    @Excel(name = "钢笔字得分", type = Excel.Type.IMPORT)
    private BigDecimal gbzdf;

    /**
     * 综合得分
     */
    @Excel(name = "综合得分", type = Excel.Type.IMPORT)
    private BigDecimal zhdf;

    /**
     * 学年学期
     */
    @Excel(name = "学年学期", type = Excel.Type.IMPORT)
    private String xnxq;

    /**
     * 创建人
     */
    // @Excel(name = "创建人")
    private Long createuserid;

    private String name;

    private TsbzJxjsjbxx tsbzJxjsjbxx;

    private String jdxmc;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFaid(Long faid) {
        this.faid = faid;
    }

    public Long getFaid() {
        return faid;
    }

    public void setJsid(Long jsid) {
        this.jsid = jsid;
    }

    public Long getJsid() {
        return jsid;
    }

    public void setJdpx(Long jdpx) {
        this.jdpx = jdpx;
    }

    public Long getJdpx() {
        return jdpx;
    }

    public void setAlfxdf(BigDecimal alfxdf) {
        this.alfxdf = alfxdf;
    }

    public BigDecimal getAlfxdf() {
        return alfxdf;
    }

    public void setJasjdf(BigDecimal jasjdf) {
        this.jasjdf = jasjdf;
    }

    public BigDecimal getJasjdf() {
        return jasjdf;
    }

    public void setGbzdf(BigDecimal gbzdf) {
        this.gbzdf = gbzdf;
    }

    public BigDecimal getGbzdf() {
        return gbzdf;
    }

    public void setZhdf(BigDecimal zhdf) {
        this.zhdf = zhdf;
    }

    public BigDecimal getZhdf() {
        return zhdf;
    }

    public void setXnxq(String xnxq) {
        this.xnxq = xnxq;
    }

    public String getXnxq() {
        return xnxq;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
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
                .append("faid", getFaid())
                .append("jsid", getJsid())
                .append("jdpx", getJdpx())
                .append("alfxdf", getAlfxdf())
                .append("jasjdf", getJasjdf())
                .append("gbzdf", getGbzdf())
                .append("zhdf", getZhdf())
                .append("xnxq", getXnxq())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("jdxmc", getJdxmc())
                .append("tsbzJxjsjbxx", getTsbzJxjsjbxx())
                .append("name", getName())
                .toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaname() {
        return faname;
    }

    public void setFaname(String faname) {
        this.faname = faname;
    }

    public String getJsname() {
        return jsname;
    }

    public void setJsname(String jsname) {
        this.jsname = jsname;
    }
}
