package com.ruoyi.project.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 班级信息对象 by_class
 *
 * @author tsbz
 * @date 2020-04-14
 */
public class ByClass extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 班级编号
     */
    private String bjbh;

    /**
     * 学校代码
     */
    @Excel(name = "学校代码")
    private Long deptId;

    /**
     * 班级类型
     */
    @Excel(name = "班级类型")
    private String bjtype;

    /**
     * 班级序号
     */
    @Excel(name = "班级序号")
    private Long bhxh;

    /**
     * 学年
     */
    @Excel(name = "学年")
    private String xn;

    /**
     * 班级名称
     */
    @Excel(name = "班级名称")
    private String bjmc;

    /**
     * 班级荣誉称号
     */
    @Excel(name = "班级荣誉称号")
    private String bjrych;

    /**
     * 建班年月
     */
    @Excel(name = "建班年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jbny;

    /**
     * 主班教师
     */
    @Excel(name = "主班教师")
    private Long zbjs;

    /**
     * 主班教师名称
     */
    private String zbjsxm;

    /**
     * 配班教师
     */
    @Excel(name = "配班教师")
    private Long pbjs;

    /**
     * 配班教师名称
     */
    private String pbjsxm;

    /**
     * 助理教师
     */
    @Excel(name = "助理教师")
    private Long zljs;

    /**
     * 助理教师名称
     */
    private String zljsxm;

    /**
     * 是否删除
     * 1:删除
     * 0:正常
     */
    @Excel(name = "是否删除 1:删除 0:正常")
    private String isdel;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    /***
     * 幼儿总数
     */
    private Long childcount;
    private Long childcheckincount;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public float getDay1() {
        return day1;
    }

    public void setDay1(float day1) {
        this.day1 = day1;
    }

    public float getDay2() {
        return day2;
    }

    public void setDay2(float day2) {
        this.day2 = day2;
    }

    public float getDay3() {
        return day3;
    }

    public void setDay3(float day3) {
        this.day3 = day3;
    }

    public float getDay4() {
        return day4;
    }

    public void setDay4(float day4) {
        this.day4 = day4;
    }

    public float getDay5() {
        return day5;
    }

    public void setDay5(float day5) {
        this.day5 = day5;
    }

    public float getDay6() {
        return day6;
    }

    public void setDay6(float day6) {
        this.day6 = day6;
    }

    public float getDay7() {
        return day7;
    }

    public void setDay7(float day7) {
        this.day7 = day7;
    }

    public float getDay8() {
        return day8;
    }

    public void setDay8(float day8) {
        this.day8 = day8;
    }

    public float getDay9() {
        return day9;
    }

    public void setDay9(float day9) {
        this.day9 = day9;
    }

    public float getDay10() {
        return day10;
    }

    public void setDay10(float day10) {
        this.day10 = day10;
    }

    public float getDay11() {
        return day11;
    }

    public void setDay11(float day11) {
        this.day11 = day11;
    }

    public float getDay12() {
        return day12;
    }

    public void setDay12(float day12) {
        this.day12 = day12;
    }

    public float getDay13() {
        return day13;
    }

    public void setDay13(float day13) {
        this.day13 = day13;
    }

    public float getDay14() {
        return day14;
    }

    public void setDay14(float day14) {
        this.day14 = day14;
    }

    public float getDay15() {
        return day15;
    }

    public void setDay15(float day15) {
        this.day15 = day15;
    }

    public float getDay16() {
        return day16;
    }

    public void setDay16(float day16) {
        this.day16 = day16;
    }

    public float getDay17() {
        return day17;
    }

    public void setDay17(float day17) {
        this.day17 = day17;
    }

    public float getDay18() {
        return day18;
    }

    public void setDay18(float day18) {
        this.day18 = day18;
    }

    public float getDay19() {
        return day19;
    }

    public void setDay19(float day19) {
        this.day19 = day19;
    }

    public float getDay20() {
        return day20;
    }

    public void setDay20(float day20) {
        this.day20 = day20;
    }

    public float getDay21() {
        return day21;
    }

    public void setDay21(float day21) {
        this.day21 = day21;
    }

    public float getDay22() {
        return day22;
    }

    public void setDay22(float day22) {
        this.day22 = day22;
    }

    public float getDay23() {
        return day23;
    }

    public void setDay23(float day23) {
        this.day23 = day23;
    }

    public float getDay24() {
        return day24;
    }

    public void setDay24(float day24) {
        this.day24 = day24;
    }

    public float getDay25() {
        return day25;
    }

    public void setDay25(float day25) {
        this.day25 = day25;
    }

    public float getDay26() {
        return day26;
    }

    public void setDay26(float day26) {
        this.day26 = day26;
    }

    public float getDay27() {
        return day27;
    }

    public void setDay27(float day27) {
        this.day27 = day27;
    }

    public float getDay28() {
        return day28;
    }

    public void setDay28(float day28) {
        this.day28 = day28;
    }

    public float getDay29() {
        return day29;
    }

    public void setDay29(float day29) {
        this.day29 = day29;
    }

    public float getDay30() {
        return day30;
    }

    public void setDay30(float day30) {
        this.day30 = day30;
    }

    public float getDay31() {
        return day31;
    }

    public void setDay31(float day31) {
        this.day31 = day31;
    }

    private String month;

    private float day1;
    private float day2;
    private float day3;
    private float day4;
    private float day5;
    private float day6;
    private float day7;
    private float day8;
    private float day9;
    private float day10;
    private float day11;
    private float day12;
    private float day13;
    private float day14;
    private float day15;
    private float day16;
    private float day17;
    private float day18;
    private float day19;
    private float day20;
    private float day21;
    private float day22;
    private float day23;
    private float day24;
    private float day25;
    private float day26;
    private float day27;
    private float day28;
    private float day29;
    private float day30;
    private float day31;


    public void setBjbh(String bjbh) {
        this.bjbh = bjbh;
    }

    public String getBjbh() {
        return bjbh;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setBjtype(String bjtype) {
        this.bjtype = bjtype;
    }

    public String getBjtype() {
        return bjtype;
    }

    public void setBhxh(Long bhxh) {
        this.bhxh = bhxh;
    }

    public Long getBhxh() {
        return bhxh;
    }

    public void setXn(String xn) {
        this.xn = xn;
    }

    public String getXn() {
        return xn;
    }

    public void setBjmc(String bjmc) {
        this.bjmc = bjmc;
    }

    public String getBjmc() {
        return bjmc;
    }

    public void setBjrych(String bjrych) {
        this.bjrych = bjrych;
    }

    public String getBjrych() {
        return bjrych;
    }

    public void setJbny(Date jbny) {
        this.jbny = jbny;
    }

    public Date getJbny() {
        return jbny;
    }

    public void setZbjs(Long zbjs) {
        this.zbjs = zbjs;
    }

    public Long getZbjs() {
        return zbjs;
    }

    public void setZbjsxm(String zbjsxm) {
        this.zbjsxm = zbjsxm;
    }

    public String getZbjsxm() {
        return zbjsxm;
    }

    public void setPbjs(Long pbjs) {
        this.pbjs = pbjs;
    }

    public Long getPbjs() {
        return pbjs;
    }

    public void setPbjsxm(String pbjsxm) {
        this.pbjsxm = pbjsxm;
    }

    public String getPbjsxm() {
        return pbjsxm;
    }

    public void setZljs(Long zljs) {
        this.zljs = zljs;
    }

    public Long getZljs() {
        return zljs;
    }

    public void setZljsxm(String zljsxm) {
        this.zljsxm = zljsxm;
    }

    public String getZljsxm() {
        return zljsxm;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public Long getChildcount() {
        return childcount;
    }

    public void setChildcount(Long childcount) {
        this.childcount = childcount;
    }

    public Long getChildcheckincount() {
        return childcheckincount;
    }

    public void setChildcheckincount(Long childcheckincount) {
        this.childcheckincount = childcheckincount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("bjbh", getBjbh())
                .append("deptId", getDeptId())
                .append("bjtype", getBjtype())
                .append("bhxh", getBhxh())
                .append("xn", getXn())
                .append("bjmc", getBjmc())
                .append("bjrych", getBjrych())
                .append("jbny", getJbny())
                .append("zbjs", getZbjs())
                .append("zbjsxm", getZbjsxm())
                .append("pbjs", getPbjs())
                .append("pbjsxm", getPbjsxm())
                .append("zljs", getZljs())
                .append("zljsxm", getZljsxm())
                .append("isdel", getIsdel())
                .append("createtime", getCreatetime())
                .append("childcount", getChildcount())
                .append("childcheckincount", getChildcheckincount())
                .append("month", getMonth())
                .append("day1", getDay1())
                .append("day2", getDay2())
                .append("day3", getDay3())
                .append("day4", getDay4())
                .append("day5", getDay5())
                .append("day6", getDay6())
                .append("day7", getDay7())
                .append("day8", getDay8())
                .append("day9", getDay9())
                .append("day10", getDay10())
                .append("day11", getDay11())
                .append("day12", getDay12())
                .append("day13", getDay13())
                .append("day14", getDay14())
                .append("day15", getDay15())
                .append("day16", getDay16())
                .append("day17", getDay17())
                .append("day18", getDay18())
                .append("day19", getDay19())
                .append("day20", getDay20())
                .append("day21", getDay21())
                .append("day22", getDay22())
                .append("day23", getDay23())
                .append("day24", getDay24())
                .append("day25", getDay25())
                .append("day26", getDay26())
                .append("day27", getDay27())
                .append("day28", getDay28())
                .append("day29", getDay29())
                .append("day30", getDay30())
                .append("day31", getDay31())
                .toString();
    }
}
