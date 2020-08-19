package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.project.system.domain.ByClass;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 儿童常规体检记录对象 by_child_health_check
 *
 * @author tsbz
 * @date 2020-08-07
 */
public class ByChildHealthCheck extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 体检表编号
     */
    private Long id;

    /**
     * 幼儿ID
     */
    @Excel(name = "幼儿ID")
    private Long childId;

    /**
     * 班级信息
     */
    @Excel(name = "班级信息")
    private String classInfo;

    /**
     * 学校名称
     */
    @Excel(name = "学校名称")
    private String schoolName;

    /**
     * 检查日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkTime;

    /**
     * 保健师
     */
    @Excel(name = "保健师")
    private String doctorName;

    /**
     * 左眼视力
     */
    @Excel(name = "左眼视力")
    private String eyesVisionLeft;

    /**
     * 右眼视力
     */
    @Excel(name = "右眼视力")
    private String eyesVisionRight;

    /**
     * 视力评价
     */
    @Excel(name = "视力评价")
    private String visionAssessment;

    /**
     * 是否龋齿
     */
    @Excel(name = "是否龋齿")
    private String decayedTooth;

    /**
     * 体重
     */
    @Excel(name = "体重")
    private Double weight;

    /**
     * 体重评价
     */
    @Excel(name = "体重评价")
    private String weightAssessment;

    /**
     * 身高
     */
    @Excel(name = "身高")
    private Long height;

    /**
     * 身高评价
     */
    @Excel(name = "身高评价")
    private String heightAssessment;

    /**
     * 总评价
     */
    @Excel(name = "总评价")
    private String totalAssessment;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;


    /**
     * 创建人
     */
    @Excel(name = "创建人")

    private ByChild byChild;

    private ByClass byClass;

    private Long createuser;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setEyesVisionLeft(String eyesVisionLeft) {
        this.eyesVisionLeft = eyesVisionLeft;
    }

    public String getEyesVisionLeft() {
        return eyesVisionLeft;
    }

    public void setEyesVisionRight(String eyesVisionRight) {
        this.eyesVisionRight = eyesVisionRight;
    }

    public String getEyesVisionRight() {
        return eyesVisionRight;
    }

    public void setVisionAssessment(String visionAssessment) {
        this.visionAssessment = visionAssessment;
    }

    public String getVisionAssessment() {
        return visionAssessment;
    }

    public void setDecayedTooth(String decayedTooth) {
        this.decayedTooth = decayedTooth;
    }

    public String getDecayedTooth() {
        return decayedTooth;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeightAssessment(String weightAssessment) {
        this.weightAssessment = weightAssessment;
    }

    public String getWeightAssessment() {
        return weightAssessment;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeightAssessment(String heightAssessment) {
        this.heightAssessment = heightAssessment;
    }

    public String getHeightAssessment() {
        return heightAssessment;
    }

    public void setTotalAssessment(String totalAssessment) {
        this.totalAssessment = totalAssessment;
    }

    public String getTotalAssessment() {
        return totalAssessment;
    }

    public void setCreateuser(Long createuser) {
        this.createuser = createuser;
    }

    public Long getCreateuser() {
        return createuser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("childId", getChildId())
                .append("classInfo", getClassInfo())
                .append("schoolName", getSchoolName())
                .append("checkTime", getCheckTime())
                .append("doctorName", getDoctorName())
                .append("eyesVisionLeft", getEyesVisionLeft())
                .append("eyesVisionRight", getEyesVisionRight())
                .append("visionAssessment", getVisionAssessment())
                .append("decayedTooth", getDecayedTooth())
                .append("weight", getWeight())
                .append("weightAssessment", getWeightAssessment())
                .append("height", getHeight())
                .append("heightAssessment", getHeightAssessment())
                .append("totalAssessment", getTotalAssessment())
                .append("createtime", getCreatetime())
                .append("createuser", getCreateuser())
                .append("byChild", getByChild())
                .append("byClass", getByChild())
                .toString();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public ByChild getByChild() {
        return byChild;
    }

    public void setByChild(ByChild byChild) {
        this.byChild = byChild;
    }

    public ByClass getByClass() {
        return byClass;
    }

    public void setByClass(ByClass byClass) {
        this.byClass = byClass;
    }
}
