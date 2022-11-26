package com.ruoyi.student.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class StuAccVo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 学院名称
     */
    @Excel(name = "学院名称")
    private String deptName;

    /**
     * 校区
     */
    private String campus;

    /**
     * 学生在册总人数
     */
    @Excel(name = "学生在册总人数")
    private String stuAcc;

    /**
     * 当日省内返校学生
     */
    @Excel(name = "当日省内返校学生")
    private String inProReturnAcc;

    /**
     * 当日省外返校学生
     */
    @Excel(name = "当日省外返校学生")
    private String outProReturnAcc;

    /**
     * 省内累计学生
     */
    @Excel(name = "省内累计学生")
    private String inProAcc;

    /**
     * 省外累计学生
     */
    @Excel(name = "省外累计学生")
    private String outProAcc;

    /**
     * 高风险返回学生数
     */
    @Excel(name = "高风险返回学生数")
    private String highLevReturnAcc;

    /**
     * 教学区宿舍区
     */
    @Excel(name = "教学区宿舍区")
    private String dormitoryTeachingAreaAcc;

    /**
     * 教学区其他地方
     */
    @Excel(name = "教学区其他地方")
    private String dormitoryOtherAreaAcc;

    /**
     * 家属区
     */
    @Excel(name = "家属区")
    private String familyAreaAcc;

    /**
     * 走读生
     */
    @Excel(name = "走读生")
    private String dayStudentAcc;

    /**
     * 校内小计
     */
    @Excel(name = "实际在校人数-小计")
    private String inSchoolAcc;

    /**
     * B类密接
     */
    @Excel(name = "B类密接")
    private String typeBAcc;

    /**
     * C类密接
     */
    private String typeCAcc;

    /**
     * 外出人数
     */
    private String toOutAcc;

    /**
     * 返回人数
     */
    private String returnAcc;

    /**
     * 未返回人数
     */
    private String notReturnAcc;

    /**
     * 市内
     */
    @Excel(name = "市内")
    private String leaveInCityAcc;

    /**
     * 省内市外
     */
    @Excel(name = "省内市外")
    private String leaveOutCityInProAcc;

    /**
     * 省外
     */
    @Excel(name = "省外")
    private String leaveOutProAcc;

    /**
     * 今日请假小计
     */
    @Excel(name = "今日请假-小计")
    private String leaveTodayAcc;

    /**
     * 因疫暂缓返校人数
     */
    @Excel(name = "因疫暂缓返校人数")
    private String epidemicSusAcc;

    /**
     * 因就业实习暂缓返校人数
     */
    @Excel(name = "因就业实习暂缓返校人数")
    private String practiceSusAcc;

    /**
     * 因其他暂缓返校人数
     */
    @Excel(name = "因其他暂缓返校人数")
    private String otherSusAcc;

    /**
     * 暂缓返校小计
     */
    @Excel(name = "暂缓返校-小计")
    private String susAcc;


    /**
     * 校外实际集中隔离人数
     */
    @Excel(name = "校外实际集中隔离人数")
    private String outSchCentIsolationAcc;

    /**
     * 校内实际居家隔离人数
     */
    @Excel(name = "校内实际居家隔离人数")
    private String outSchHomeIsolationAcc;

    /**
     * 校内实际居家健康监测人数
     */
    @Excel(name = "校内实际居家健康监测人数")
    private String inSchHomeIsolationAcc;

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getStuAcc() {
        return stuAcc;
    }

    public void setStuAcc(String stuAcc) {
        this.stuAcc = stuAcc;
    }

    public String getInProReturnAcc() {
        return inProReturnAcc;
    }

    public void setInProReturnAcc(String inProReturnAcc) {
        this.inProReturnAcc = inProReturnAcc;
    }

    public String getOutProReturnAcc() {
        return outProReturnAcc;
    }

    public void setOutProReturnAcc(String outProReturnAcc) {
        this.outProReturnAcc = outProReturnAcc;
    }

    public String getInProAcc() {
        return inProAcc;
    }

    public void setInProAcc(String inProAcc) {
        this.inProAcc = inProAcc;
    }

    public String getOutProAcc() {
        return outProAcc;
    }

    public void setOutProAcc(String outProAcc) {
        this.outProAcc = outProAcc;
    }

    public String getHighLevReturnAcc() {
        return highLevReturnAcc;
    }

    public void setHighLevReturnAcc(String highLevReturnAcc) {
        this.highLevReturnAcc = highLevReturnAcc;
    }

    public String getDormitoryTeachingAreaAcc() {
        return dormitoryTeachingAreaAcc;
    }

    public void setDormitoryTeachingAreaAcc(String dormitoryTeachingAreaAcc) {
        this.dormitoryTeachingAreaAcc = dormitoryTeachingAreaAcc;
    }

    public String getDormitoryOtherAreaAcc() {
        return dormitoryOtherAreaAcc;
    }

    public void setDormitoryOtherAreaAcc(String dormitoryOtherAreaAcc) {
        this.dormitoryOtherAreaAcc = dormitoryOtherAreaAcc;
    }

    public String getFamilyAreaAcc() {
        return familyAreaAcc;
    }

    public void setFamilyAreaAcc(String familyAreaAcc) {
        this.familyAreaAcc = familyAreaAcc;
    }

    public String getDayStudentAcc() {
        return dayStudentAcc;
    }

    public void setDayStudentAcc(String dayStudentAcc) {
        this.dayStudentAcc = dayStudentAcc;
    }

    public String getInSchoolAcc() {
        return inSchoolAcc;
    }

    public void setInSchoolAcc(String inSchoolAcc) {
        this.inSchoolAcc = inSchoolAcc;
    }

    public String getTypeBAcc() {
        return typeBAcc;
    }

    public void setTypeBAcc(String typeBAcc) {
        this.typeBAcc = typeBAcc;
    }

    public String getTypeCAcc() {
        return typeCAcc;
    }

    public void setTypeCAcc(String typeCAcc) {
        this.typeCAcc = typeCAcc;
    }

    public String getToOutAcc() {
        return toOutAcc;
    }

    public void setToOutAcc(String toOutAcc) {
        this.toOutAcc = toOutAcc;
    }

    public String getReturnAcc() {
        return returnAcc;
    }

    public void setReturnAcc(String returnAcc) {
        this.returnAcc = returnAcc;
    }

    public String getNotReturnAcc() {
        return notReturnAcc;
    }

    public void setNotReturnAcc(String notReturnAcc) {
        this.notReturnAcc = notReturnAcc;
    }

    public String getLeaveInCityAcc() {
        return leaveInCityAcc;
    }

    public void setLeaveInCityAcc(String leaveInCityAcc) {
        this.leaveInCityAcc = leaveInCityAcc;
    }

    public String getLeaveOutCityInProAcc() {
        return leaveOutCityInProAcc;
    }

    public void setLeaveOutCityInProAcc(String leaveOutCityInProAcc) {
        this.leaveOutCityInProAcc = leaveOutCityInProAcc;
    }

    public String getLeaveOutProAcc() {
        return leaveOutProAcc;
    }

    public void setLeaveOutProAcc(String leaveOutProAcc) {
        this.leaveOutProAcc = leaveOutProAcc;
    }

    public String getLeaveTodayAcc() {
        return leaveTodayAcc;
    }

    public void setLeaveTodayAcc(String leaveTodayAcc) {
        this.leaveTodayAcc = leaveTodayAcc;
    }

    public String getEpidemicSusAcc() {
        return epidemicSusAcc;
    }

    public void setEpidemicSusAcc(String epidemicSusAcc) {
        this.epidemicSusAcc = epidemicSusAcc;
    }

    public String getPracticeSusAcc() {
        return practiceSusAcc;
    }

    public void setPracticeSusAcc(String practiceSusAcc) {
        this.practiceSusAcc = practiceSusAcc;
    }

    public String getOtherSusAcc() {
        return otherSusAcc;
    }

    public void setOtherSusAcc(String otherSusAcc) {
        this.otherSusAcc = otherSusAcc;
    }

    public String getSusAcc() {
        return susAcc;
    }

    public void setSusAcc(String susAcc) {
        this.susAcc = susAcc;
    }

    public String getOutSchCentIsolationAcc() {
        return outSchCentIsolationAcc;
    }

    public void setOutSchCentIsolationAcc(String outSchCentIsolationAcc) {
        this.outSchCentIsolationAcc = outSchCentIsolationAcc;
    }

    public String getOutSchHomeIsolationAcc() {
        return outSchHomeIsolationAcc;
    }

    public void setOutSchHomeIsolationAcc(String outSchHomeIsolationAcc) {
        this.outSchHomeIsolationAcc = outSchHomeIsolationAcc;
    }

    public String getInSchHomeIsolationAcc() {
        return inSchHomeIsolationAcc;
    }

    public void setInSchHomeIsolationAcc(String inSchHomeIsolationAcc) {
        this.inSchHomeIsolationAcc = inSchHomeIsolationAcc;
    }
}
