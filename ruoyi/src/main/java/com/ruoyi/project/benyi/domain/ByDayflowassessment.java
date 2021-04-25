package com.ruoyi.project.benyi.domain;

import com.ruoyi.project.system.domain.ByClass;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 幼儿园一日流程评估对象 by_dayflowassessment
 *
 * @author tsbz
 * @date 2021-02-05
 */
public class ByDayflowassessment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 所属计划
     */
    @Excel(name = "所属计划")
    private Long planid;

    /**
     * 学校编号
     */
    @Excel(name = "学校编号")
    private Long deptId;

    /**
     * 班级编号
     */
    @Excel(name = "班级编号")
    private String classid;

    /**
     * 班长编号
     */
    @Excel(name = "班长编号")
    private Long bzbh;

    /**
     * 班长姓名
     */
    @Excel(name = "班长姓名")
    private String bzxm;

    /**
     * 配班教师
     */
    @Excel(name = "配班教师")
    private Long pbbh;

    /**
     * 配班教师姓名
     */
    @Excel(name = "配班教师姓名")
    private String pbxm;

    /**
     * 助理教师
     */
    @Excel(name = "助理教师")
    private Long zlbh;

    /**
     * 助理教师姓名
     */
    @Excel(name = "助理教师姓名")
    private String zlxm;

    /**
     * 评估学年学期
     */
    @Excel(name = "评估学年学期")
    private String xnxq;

    /**
     * 评估标准编号
     */
    @Excel(name = "评估标准编号")
    private Long bzid;

    /**
     * 评估标准满分
     */
    @Excel(name = "评估标准满分")
    private Double bzmf;

    /**
     * 扣分值
     */
    @Excel(name = "扣分值")
    private Double kfz;

    /**
     * 班级得分
     */
    @Excel(name = "班级得分")
    private Double classdf;

    /**
     * 评估对象记录
     */
    @Excel(name = "评估对象记录")
    private String items;

    /**
     * 评估对象记录值
     */
    @Excel(name = "评估对象记录值")
    private String values;

    /**
     * 扣分次数
     */
    @Excel(name = "扣分次数")
    private Long kfcs;

    /**
     * 最终得分
     */
    @Excel(name = "评估标准满分")
    private Double zzdf;

    /**
     * 评估对象
     */
    @Excel(name = "评估对象")
    private Long pgdx;

    /**
     * 评估对象姓名
     */
    @Excel(name = "评估对象姓名")
    private String pgdxxm;

    /**
     * 执行人
     */
    @Excel(name = "执行人")
    private Long createUserid;

//    /**
//     * 创建时间
//     */
//    @Excel(name = "创建时间")
//    private Date createTime;

    public Double getBjpjf() {
        return bjpjf;
    }

    public void setBjpjf(Double bjpjf) {
        this.bjpjf = bjpjf;
    }

    public Double getZjjdpjf() {
        return zjjdpjf;
    }

    public void setZjjdpjf(Double zjjdpjf) {
        this.zjjdpjf = zjjdpjf;
    }

    public Double getYcpjf() {
        return ycpjf;
    }

    public void setYcpjf(Double ycpjf) {
        this.ycpjf = ycpjf;
    }

    public Double getZjzqpjf() {
        return zjzqpjf;
    }

    public void setZjzqpjf(Double zjzqpjf) {
        this.zjzqpjf = zjzqpjf;
    }

    public Double getFzjxpjf() {
        return fzjxpjf;
    }

    public void setFzjxpjf(Double fzjxpjf) {
        this.fzjxpjf = fzjxpjf;
    }

    public Double getDxsjpjf() {
        return dxsjpjf;
    }

    public void setDxsjpjf(Double dxsjpjf) {
        this.dxsjpjf = dxsjpjf;
    }

    public Double getRcxsyspjf() {
        return rcxsyspjf;
    }

    public void setRcxsyspjf(Double rcxsyspjf) {
        this.rcxsyspjf = rcxsyspjf;
    }

    public Double getHdgdpjf() {
        return hdgdpjf;
    }

    public void setHdgdpjf(Double hdgdpjf) {
        this.hdgdpjf = hdgdpjf;
    }

    public Double getHwhdpjf() {
        return hwhdpjf;
    }

    public void setHwhdpjf(Double hwhdpjf) {
        this.hwhdpjf = hwhdpjf;
    }

    public Double getWspjf() {
        return wspjf;
    }

    public void setWspjf(Double wspjf) {
        this.wspjf = wspjf;
    }

    public Double getLyzjpjf() {
        return lyzjpjf;
    }

    public void setLyzjpjf(Double lyzjpjf) {
        this.lyzjpjf = lyzjpjf;
    }

    public Double getAqpjf() {
        return aqpjf;
    }

    public void setAqpjf(Double aqpjf) {
        this.aqpjf = aqpjf;
    }

    public Double getZyhdpjf() {
        return zyhdpjf;
    }

    public void setZyhdpjf(Double zyhdpjf) {
        this.zyhdpjf = zyhdpjf;
    }

    public Double getGzyjlyspjf() {
        return gzyjlyspjf;
    }

    public void setGzyjlyspjf(Double gzyjlyspjf) {
        this.gzyjlyspjf = gzyjlyspjf;
    }

    public Double getWxkcpjf() {
        return wxkcpjf;
    }

    public void setWxkcpjf(Double wxkcpjf) {
        this.wxkcpjf = wxkcpjf;
    }

    public Double getQkcpjf() {
        return qkcpjf;
    }

    public void setQkcpjf(Double qkcpjf) {
        this.qkcpjf = qkcpjf;
    }

    private Double bjpjf;
    private Double zjjdpjf;
    private Double ycpjf;
    private Double zjzqpjf;
    private Double fzjxpjf;
    private Double dxsjpjf;
    private Double rcxsyspjf;
    private Double hdgdpjf;
    private Double hwhdpjf;
    private Double wspjf;
    private Double lyzjpjf;
    private Double aqpjf;
    private Double zyhdpjf;
    private Double gzyjlyspjf;
    private Double wxkcpjf;
    private Double qkcpjf;

    private List<ByDayFlowStandard> list;

    private ByClass byClass;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPlanid(Long planid) {
        this.planid = planid;
    }

    public Long getPlanid() {
        return planid;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid() {
        return classid;
    }

    public void setBzbh(Long bzbh) {
        this.bzbh = bzbh;
    }

    public Long getBzbh() {
        return bzbh;
    }

    public void setBzxm(String bzxm) {
        this.bzxm = bzxm;
    }

    public String getBzxm() {
        return bzxm;
    }

    public void setPbbh(Long pbbh) {
        this.pbbh = pbbh;
    }

    public Long getPbbh() {
        return pbbh;
    }

    public void setPbxm(String pbxm) {
        this.pbxm = pbxm;
    }

    public String getPbxm() {
        return pbxm;
    }

    public void setZlbh(Long zlbh) {
        this.zlbh = zlbh;
    }

    public Long getZlbh() {
        return zlbh;
    }

    public void setZlxm(String zlxm) {
        this.zlxm = zlxm;
    }

    public String getZlxm() {
        return zlxm;
    }

    public void setXnxq(String xnxq) {
        this.xnxq = xnxq;
    }

    public String getXnxq() {
        return xnxq;
    }

    public void setBzid(Long bzid) {
        this.bzid = bzid;
    }

    public Long getBzid() {
        return bzid;
    }

    public void setKfz(Double kfz) {
        this.kfz = kfz;
    }

    public Double getKfz() {
        return kfz;
    }

    public void setKfcs(Long kfcs) {
        this.kfcs = kfcs;
    }

    public Long getKfcs() {
        return kfcs;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getValues() {
        return values;
    }

    public void setPgdx(Long pgdx) {
        this.pgdx = pgdx;
    }

    public Long getPgdx() {
        return pgdx;
    }

    public void setPgdxxm(String pgdxxm) {
        this.pgdxxm = pgdxxm;
    }

    public String getPgdxxm() {
        return pgdxxm;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public List<ByDayFlowStandard> getList() {
        return list;
    }

    public void setList(List<ByDayFlowStandard> list) {
        this.list = list;
    }

    public ByClass getByClass() {
        return byClass;
    }

    public void setByClass(ByClass byClass) {
        this.byClass = byClass;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("planid", getPlanid())
                .append("deptId", getDeptId())
                .append("classid", getClassid())
                .append("bzbh", getBzbh())
                .append("bzxm", getBzxm())
                .append("pbbh", getPbbh())
                .append("pbxm", getPbxm())
                .append("zlbh", getZlbh())
                .append("zlxm", getZlxm())
                .append("xnxq", getXnxq())
                .append("bzid", getBzid())
                .append("kfz", getKfz())
                .append("classdf", getClassdf())
                .append("kfcs", getKfcs())
                .append("bzmf", getBzmf())
                .append("zzdf", getZzdf())
                .append("items", getItems())
                .append("values", getValues())
                .append("pgdx", getPgdx())
                .append("pgdxxm", getPgdxxm())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("list", getList())
                .append("bjpjf", getBjpjf())
                .append("zjjdpjf", getZjjdpjf())
                .append("ycpjf", getYcpjf())
                .append("zjzqpjf", getZjzqpjf())
                .append("fzjxpjf", getFzjxpjf())
                .append("dxsjpjf", getDxsjpjf())
                .append("rcxsyspjf", getRcxsyspjf())
                .append("hdgdpjf", getHdgdpjf())
                .append("hwhdpjf", getHwhdpjf())
                .append("wspjf", getWspjf())
                .append("lyzjpjf", getLyzjpjf())
                .append("aqpjf", getAqpjf())
                .append("zyhdpjf", getZyhdpjf())
                .append("gzyjlyspjf", getGzyjlyspjf())
                .append("wxkcpjf", getWxkcpjf())
                .append("qkcpjf", getQkcpjf())
                .append("byClass", getByClass())
                .toString();
    }

    public Double getBzmf() {
        return bzmf;
    }

    public void setBzmf(Double bzmf) {
        this.bzmf = bzmf;
    }

    public Double getZzdf() {
        return zzdf;
    }

    public void setZzdf(Double zzdf) {
        this.zzdf = zzdf;
    }


    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public Double getClassdf() {
        return classdf;
    }

    public void setClassdf(Double classdf) {
        this.classdf = classdf;
    }
}
