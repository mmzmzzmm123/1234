package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 周计划(家长和教育部门)对象 by_planweek
 *
 * @author tsbz
 * @date 2020-08-24
 */
public class ByPlanweek extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 学校id
     */
    @Excel(name = "学校id")
    private Long schoolid;

    /**
     * 班级id
     */
    @Excel(name = "班级id")
    private String classid;

    /**
     * 计划名称
     */
    @Excel(name = "计划名称")
    private String name;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date starttime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endtime;

    /**
     * 本周主题
     */
    @Excel(name = "本周主题")
    private String themeofweek;

    /**
     * 教学目标(社会)
     */
    @Excel(name = "教学目标(社会)")
    private String jxmbSh;

    /**
     * 教学目标(语言)
     */
    @Excel(name = "教学目标(语言)")
    private String jxmbYy;

    /**
     * 教学目标(健康)
     */
    @Excel(name = "教学目标(健康)")
    private String jxmbJk;

    /**
     * 教学目标(科学)
     */
    @Excel(name = "教学目标(科学)")
    private String jxmbKx;

    /**
     * 教学目标(艺术)
     */
    @Excel(name = "教学目标(艺术)")
    private String jxmbYs;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    /**
     * 当前状态
     */
    @Excel(name = "当前状态")
    private String status;

    /**
     * 审核人
     */
    @Excel(name = "审核人")
    private Long shrid;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date shtime;

    /**
     * 审核意见
     */
    @Excel(name = "审核意见")
    private String shyj;

    /**
     * 周次
     */
    @Excel(name = "周次")
    private String weekly;

    /**
     * 月份
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "月份", width = 30, dateFormat = "yyyy-MM")
    private Date month;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSchoolid(Long schoolid) {
        this.schoolid = schoolid;
    }

    public Long getSchoolid() {
        return schoolid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassid() {
        return classid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setThemeofweek(String themeofweek) {
        this.themeofweek = themeofweek;
    }

    public String getThemeofweek() {
        return themeofweek;
    }

    public void setJxmbSh(String jxmbSh) {
        this.jxmbSh = jxmbSh;
    }

    public String getJxmbSh() {
        return jxmbSh;
    }

    public void setJxmbYy(String jxmbYy) {
        this.jxmbYy = jxmbYy;
    }

    public String getJxmbYy() {
        return jxmbYy;
    }

    public void setJxmbJk(String jxmbJk) {
        this.jxmbJk = jxmbJk;
    }

    public String getJxmbJk() {
        return jxmbJk;
    }

    public void setJxmbKx(String jxmbKx) {
        this.jxmbKx = jxmbKx;
    }

    public String getJxmbKx() {
        return jxmbKx;
    }

    public void setJxmbYs(String jxmbYs) {
        this.jxmbYs = jxmbYs;
    }

    public String getJxmbYs() {
        return jxmbYs;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setShrid(Long shrid) {
        this.shrid = shrid;
    }

    public Long getShrid() {
        return shrid;
    }

    public void setShtime(Date shtime) {
        this.shtime = shtime;
    }

    public Date getShtime() {
        return shtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("schoolid", getSchoolid())
                .append("classid", getClassid())
                .append("name", getName())
                .append("starttime", getStarttime())
                .append("endtime", getEndtime())
                .append("themeofweek", getThemeofweek())
                .append("jxmbSh", getJxmbSh())
                .append("jxmbYy", getJxmbYy())
                .append("jxmbJk", getJxmbJk())
                .append("jxmbKx", getJxmbKx())
                .append("jxmbYs", getJxmbYs())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("status", getStatus())
                .append("shrid", getShrid())
                .append("shtime", getShtime())
                .append("shyj", getShyj())
                .append("weekly", getWeekly())
                .append("month", getMonth())
                .toString();
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public String getWeekly() {
        return weekly;
    }

    public void setWeekly(String weekly) {
        this.weekly = weekly;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
