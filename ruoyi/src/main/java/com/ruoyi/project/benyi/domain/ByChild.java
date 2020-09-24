package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 幼儿信息对象 by_child
 *
 * @author tsbz
 * @date 2020-07-21
 */
public class ByChild extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

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
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 英文名
     */
    @Excel(name = "英文名")
    private String enName;

    /**
     * 小名
     */
    @Excel(name = "小名")
    private String infantName;

    /**
     * 家长手机号码
     */
    @Excel(name = "家长手机号码")
    private String phone;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String xb;

    /**
     * 民族
     */
    @Excel(name = "民族")
    private String mz;

    /**
     * 证件号码
     */
    @Excel(name = "证件号码")
    private String zjhm;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date csrq;

    /**
     * 出生所在省
     */
    @Excel(name = "出生所在省")
    private String birthProvince;

    /**
     * 出生所在省名称
     */
    @Excel(name = "出生所在省名称")
    private String birthProvincename;

    /**
     * 出生所在地市
     */
    @Excel(name = "出生所在地市")
    private String birthCity;

    /**
     * 出生所在市名称
     */
    @Excel(name = "出生所在市名称")
    private String birthCityname;

    /**
     * 出生地区
     */
    @Excel(name = "出生地区")
    private String birthArea;

    /**
     * 出生所在区名称
     */
    @Excel(name = "出生所在区名称")
    private String birthAreaname;

    /**
     * 户口所在地
     */
    @Excel(name = "户口所在地")
    private String registeredProvince;

    /**
     * 户口所在地名称
     */
    @Excel(name = "户口所在地名称")
    private String registeredProvincename;

    /**
     * 户口所在地市
     */
    @Excel(name = "户口所在地市")
    private String registeredCity;

    /**
     * 户口所在市名称
     */
    @Excel(name = "户口所在市名称")
    private String registeredCityname;

    /**
     * 户口所在区
     */
    @Excel(name = "户口所在区")
    private String registeredArea;

    /**
     * 户口所在区名称
     */
    @Excel(name = "户口所在区名称")
    private String registeredAreaname;

    /**
     * 住址省
     */
    @Excel(name = "住址省")
    private String addrProvince;

    /**
     * 住址省名称
     */
    @Excel(name = "住址省名称")
    private String addrProvincename;

    /**
     * 住址市
     */
    @Excel(name = "住址市")
    private String addrCity;

    /**
     * 住址市名称
     */
    @Excel(name = "住址市名称")
    private String addrCityname;

    /**
     * 住址区
     */
    @Excel(name = "住址区")
    private String addrArea;

    /**
     * 住址区名称
     */
    @Excel(name = "住址区名称")
    private String addrAreaname;

    /**
     * 详细地址
     */
    @Excel(name = "详细地址")
    private String addrDetail;

    /**
     * 曾经就读幼儿园
     */
    @Excel(name = "曾经就读幼儿园")
    private String everSchool;

    /**
     * 是否学习英语
     */
    @Excel(name = "是否学习英语")
    private String learnEnglish;

    /**
     * 入园渠道
     */
    @Excel(name = "入园渠道")
    private String source;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 入园时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入园时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date enterDate;

    /**
     * 离园时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离园时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outDate;

    /**
     * 第一语言
     */
    @Excel(name = "第一语言")
    private String firstLanguage;

    /**
     * 第二语言
     */
    @Excel(name = "第二语言")
    private String seconderLanguage;

    /**
     * 其他语言
     */
    @Excel(name = "其他语言")
    private String otherLanguage;

    /**
     * 创建人
     */
    @Excel(name = "创建人")



    private Long createuserid;

    private ByChildContactpeople byChildContactpeople;

    // 幼儿考勤系统属性
    private String month;
    private String day1;
    private String day2;
    private String day3;
    private String day4;
    private String day5;
    private String day6;
    private String day7;
    private String day8;
    private String day9;
    private String day10;
    private String day11;
    private String day12;
    private String day13;
    private String day14;
    private String day15;
    private String day16;
    private String day17;
    private String day18;
    private String day19;
    private String day20;
    private String day21;
    private String day22;
    private String day23;
    private String day24;
    private String day25;
    private String day26;
    private String day27;
    private String day28;
    private String day29;
    private String day30;
    private String day31;

    public String getDay1() {
        return day1;
    }

    public void setDay1(String day1) {
        this.day1 = day1;
    }

    public String getDay2() {
        return day2;
    }

    public void setDay2(String day2) {
        this.day2 = day2;
    }

    public String getDay3() {
        return day3;
    }

    public void setDay3(String day3) {
        this.day3 = day3;
    }

    public String getDay4() {
        return day4;
    }

    public void setDay4(String day4) {
        this.day4 = day4;
    }

    public String getDay5() {
        return day5;
    }

    public void setDay5(String day5) {
        this.day5 = day5;
    }

    public String getDay6() {
        return day6;
    }

    public void setDay6(String day6) {
        this.day6 = day6;
    }

    public String getDay7() {
        return day7;
    }

    public void setDay7(String day7) {
        this.day7 = day7;
    }

    public String getDay8() {
        return day8;
    }

    public void setDay8(String day8) {
        this.day8 = day8;
    }

    public String getDay9() {
        return day9;
    }

    public void setDay9(String day9) {
        this.day9 = day9;
    }

    public String getDay10() {
        return day10;
    }

    public void setDay10(String day10) {
        this.day10 = day10;
    }

    public String getDay11() {
        return day11;
    }

    public void setDay11(String day11) {
        this.day11 = day11;
    }

    public String getDay12() {
        return day12;
    }

    public void setDay12(String day12) {
        this.day12 = day12;
    }

    public String getDay13() {
        return day13;
    }

    public void setDay13(String day13) {
        this.day13 = day13;
    }

    public String getDay14() {
        return day14;
    }

    public void setDay14(String day14) {
        this.day14 = day14;
    }

    public String getDay15() {
        return day15;
    }

    public void setDay15(String day15) {
        this.day15 = day15;
    }

    public String getDay16() {
        return day16;
    }

    public void setDay16(String day16) {
        this.day16 = day16;
    }

    public String getDay17() {
        return day17;
    }

    public void setDay17(String day17) {
        this.day17 = day17;
    }

    public String getDay18() {
        return day18;
    }

    public void setDay18(String day18) {
        this.day18 = day18;
    }

    public String getDay19() {
        return day19;
    }

    public void setDay19(String day19) {
        this.day19 = day19;
    }

    public String getDay20() {
        return day20;
    }

    public void setDay20(String day20) {
        this.day20 = day20;
    }

    public String getDay21() {
        return day21;
    }

    public void setDay21(String day21) {
        this.day21 = day21;
    }

    public String getDay22() {
        return day22;
    }

    public void setDay22(String day22) {
        this.day22 = day22;
    }

    public String getDay23() {
        return day23;
    }

    public void setDay23(String day23) {
        this.day23 = day23;
    }

    public String getDay24() {
        return day24;
    }

    public void setDay24(String day24) {
        this.day24 = day24;
    }

    public String getDay25() {
        return day25;
    }

    public void setDay25(String day25) {
        this.day25 = day25;
    }

    public String getDay26() {
        return day26;
    }

    public void setDay26(String day26) {
        this.day26 = day26;
    }

    public String getDay27() {
        return day27;
    }

    public void setDay27(String day27) {
        this.day27 = day27;
    }

    public String getDay28() {
        return day28;
    }

    public void setDay28(String day28) {
        this.day28 = day28;
    }

    public String getDay29() {
        return day29;
    }

    public void setDay29(String day29) {
        this.day29 = day29;
    }

    public String getDay30() {
        return day30;
    }

    public void setDay30(String day30) {
        this.day30 = day30;
    }

    public String getDay31() {
        return day31;
    }

    public void setDay31(String day31) {
        this.day31 = day31;
    }






    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getEnName() {
        return enName;
    }

    public void setInfantName(String infantName) {
        this.infantName = infantName;
    }

    public String getInfantName() {
        return infantName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getXb() {
        return xb;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getMz() {
        return mz;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setBirthProvince(String birthProvince) {
        this.birthProvince = birthProvince;
    }

    public String getBirthProvince() {
        return birthProvince;
    }

    public void setBirthProvincename(String birthProvincename) {
        this.birthProvincename = birthProvincename;
    }

    public String getBirthProvincename() {
        return birthProvincename;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCityname(String birthCityname) {
        this.birthCityname = birthCityname;
    }

    public String getBirthCityname() {
        return birthCityname;
    }

    public void setBirthArea(String birthArea) {
        this.birthArea = birthArea;
    }

    public String getBirthArea() {
        return birthArea;
    }

    public void setBirthAreaname(String birthAreaname) {
        this.birthAreaname = birthAreaname;
    }

    public String getBirthAreaname() {
        return birthAreaname;
    }

    public void setRegisteredProvince(String registeredProvince) {
        this.registeredProvince = registeredProvince;
    }

    public String getRegisteredProvince() {
        return registeredProvince;
    }

    public void setRegisteredProvincename(String registeredProvincename) {
        this.registeredProvincename = registeredProvincename;
    }

    public String getRegisteredProvincename() {
        return registeredProvincename;
    }

    public void setRegisteredCity(String registeredCity) {
        this.registeredCity = registeredCity;
    }

    public String getRegisteredCity() {
        return registeredCity;
    }

    public void setRegisteredCityname(String registeredCityname) {
        this.registeredCityname = registeredCityname;
    }

    public String getRegisteredCityname() {
        return registeredCityname;
    }

    public void setRegisteredArea(String registeredArea) {
        this.registeredArea = registeredArea;
    }

    public String getRegisteredArea() {
        return registeredArea;
    }

    public void setRegisteredAreaname(String registeredAreaname) {
        this.registeredAreaname = registeredAreaname;
    }

    public String getRegisteredAreaname() {
        return registeredAreaname;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvincename(String addrProvincename) {
        this.addrProvincename = addrProvincename;
    }

    public String getAddrProvincename() {
        return addrProvincename;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCityname(String addrCityname) {
        this.addrCityname = addrCityname;
    }

    public String getAddrCityname() {
        return addrCityname;
    }

    public void setAddrArea(String addrArea) {
        this.addrArea = addrArea;
    }

    public String getAddrArea() {
        return addrArea;
    }

    public void setAddrAreaname(String addrAreaname) {
        this.addrAreaname = addrAreaname;
    }

    public String getAddrAreaname() {
        return addrAreaname;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setEverSchool(String everSchool) {
        this.everSchool = everSchool;
    }

    public String getEverSchool() {
        return everSchool;
    }

    public void setLearnEnglish(String learnEnglish) {
        this.learnEnglish = learnEnglish;
    }

    public String getLearnEnglish() {
        return learnEnglish;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setFirstLanguage(String firstLanguage) {
        this.firstLanguage = firstLanguage;
    }

    public String getFirstLanguage() {
        return firstLanguage;
    }

    public void setSeconderLanguage(String seconderLanguage) {
        this.seconderLanguage = seconderLanguage;
    }

    public String getSeconderLanguage() {
        return seconderLanguage;
    }

    public void setOtherLanguage(String otherLanguage) {
        this.otherLanguage = otherLanguage;
    }

    public String getOtherLanguage() {
        return otherLanguage;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("schoolid", getSchoolid())
                .append("classid", getClassid())
                .append("name", getName())
                .append("enName", getEnName())
                .append("infantName", getInfantName())
                .append("phone", getPhone())
                .append("xb", getXb())
                .append("mz", getMz())
                .append("zjhm", getZjhm())
                .append("csrq", getCsrq())
                .append("birthProvince", getBirthProvince())
                .append("birthProvincename", getBirthProvincename())
                .append("birthCity", getBirthCity())
                .append("birthCityname", getBirthCityname())
                .append("birthArea", getBirthArea())
                .append("birthAreaname", getBirthAreaname())
                .append("registeredProvince", getRegisteredProvince())
                .append("registeredProvincename", getRegisteredProvincename())
                .append("registeredCity", getRegisteredCity())
                .append("registeredCityname", getRegisteredCityname())
                .append("registeredArea", getRegisteredArea())
                .append("registeredAreaname", getRegisteredAreaname())
                .append("addrProvince", getAddrProvince())
                .append("addrProvincename", getAddrProvincename())
                .append("addrCity", getAddrCity())
                .append("addrCityname", getAddrCityname())
                .append("addrArea", getAddrArea())
                .append("addrAreaname", getAddrAreaname())
                .append("addrDetail", getAddrDetail())
                .append("everSchool", getEverSchool())
                .append("learnEnglish", getLearnEnglish())
                .append("source", getSource())
                .append("status", getStatus())
                .append("enterDate", getEnterDate())
                .append("outDate", getOutDate())
                .append("firstLanguage", getFirstLanguage())
                .append("seconderLanguage", getSeconderLanguage())
                .append("otherLanguage", getOtherLanguage())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("byChildContactpeople", getByChildContactpeople())
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

    public ByChildContactpeople getByChildContactpeople() {
        return byChildContactpeople;
    }

    public void setByChildContactpeople(ByChildContactpeople byChildContactpeople) {
        this.byChildContactpeople = byChildContactpeople;
    }
}