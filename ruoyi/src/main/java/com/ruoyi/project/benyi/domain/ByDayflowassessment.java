package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

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
     * 扣分值
     */
    @Excel(name = "扣分值")
    private Double kfz;

    /**
     * 扣分次数
     */
    @Excel(name = "扣分次数")
    private Long kfcs;

    /**
     * 评估对象
     */
    @Excel(name = "评估对象")
    private Long pgdx;

    /**
     * 执行人
     */
    @Excel(name = "执行人")
    private Long createUserid;

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

    public void setPgdx(Long pgdx) {
        this.pgdx = pgdx;
    }

    public Long getPgdx() {
        return pgdx;
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
                .append("kfcs", getKfcs())
                .append("pgdx", getPgdx())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
