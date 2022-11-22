package com.ruoyi.student.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生信息对象 stu_info
 *
 * @author wangzq
 * @date 2022-11-22
 */
public class StuInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 学号 */
    @Excel(name = "学号")
    private Long studentId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String studentName;

    /** 学院编号 */
    @Excel(name = "学院编号")
    private Long deptId;

    /** 学院名称 */
    @Excel(name = "学院名称")
    private String deptName;

    /** 培养层次 */
    @Excel(name = "培养层次")
    private String trainingLevel;

    /** 年级 */
    @Excel(name = "年级")
    private String grade;

    /** 住址 */
    @Excel(name = "住址")
    private String address;

    /** 民族 */
    @Excel(name = "民族")
    private String nation;

    /** 校区 */
    @Excel(name = "校区")
    private String campus;

    /** 学生标签 */
    @Excel(name = "学生标签")
    private String studentTag;

    /** 住宿地点 */
    @Excel(name = "住宿地点")
    private String accommodation;

    /** 住宿园区 */
    @Excel(name = "住宿园区")
    private String accommodationPark;

    /** 宿舍号 */
    @Excel(name = "宿舍号")
    private String dormitoryNo;

    /** 是否校内住宿 */
    @Excel(name = "是否校内住宿")
    private String isOnSchool;

    /** 管控等级 */
    @Excel(name = "管控等级")
    private String controlLevel;

    /** 未返校原因 */
    @Excel(name = "未返校原因")
    private String notSchoolReason;

    /** 返校出发地风险等级 */
    @Excel(name = "返校出发地风险等级")
    private String placeToSchoolLevel;

    /** 返校出发地 */
    @Excel(name = "返校出发地")
    private String placeToSchool;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setStudentId(Long studentId)
    {
        this.studentId = studentId;
    }

    public Long getStudentId()
    {
        return studentId;
    }
    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public String getStudentName()
    {
        return studentName;
    }
    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDeptName()
    {
        return deptName;
    }
    public void setTrainingLevel(String trainingLevel)
    {
        this.trainingLevel = trainingLevel;
    }

    public String getTrainingLevel()
    {
        return trainingLevel;
    }
    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public String getGrade()
    {
        return grade;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setNation(String nation)
    {
        this.nation = nation;
    }

    public String getNation()
    {
        return nation;
    }
    public void setCampus(String campus)
    {
        this.campus = campus;
    }

    public String getCampus()
    {
        return campus;
    }
    public void setStudentTag(String studentTag)
    {
        this.studentTag = studentTag;
    }

    public String getStudentTag()
    {
        return studentTag;
    }
    public void setAccommodation(String accommodation)
    {
        this.accommodation = accommodation;
    }

    public String getAccommodation()
    {
        return accommodation;
    }
    public void setAccommodationPark(String accommodationPark)
    {
        this.accommodationPark = accommodationPark;
    }

    public String getAccommodationPark()
    {
        return accommodationPark;
    }
    public void setDormitoryNo(String dormitoryNo)
    {
        this.dormitoryNo = dormitoryNo;
    }

    public String getDormitoryNo()
    {
        return dormitoryNo;
    }
    public void setIsOnSchool(String isOnSchool)
    {
        this.isOnSchool = isOnSchool;
    }

    public String getIsOnSchool()
    {
        return isOnSchool;
    }
    public void setControlLevel(String controlLevel)
    {
        this.controlLevel = controlLevel;
    }

    public String getControlLevel()
    {
        return controlLevel;
    }
    public void setNotSchoolReason(String notSchoolReason)
    {
        this.notSchoolReason = notSchoolReason;
    }

    public String getNotSchoolReason()
    {
        return notSchoolReason;
    }
    public void setPlaceToSchoolLevel(String placeToSchoolLevel)
    {
        this.placeToSchoolLevel = placeToSchoolLevel;
    }

    public String getPlaceToSchoolLevel()
    {
        return placeToSchoolLevel;
    }
    public void setPlaceToSchool(String placeToSchool)
    {
        this.placeToSchool = placeToSchool;
    }

    public String getPlaceToSchool()
    {
        return placeToSchool;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("studentId", getStudentId())
                .append("studentName", getStudentName())
                .append("deptId", getDeptId())
                .append("deptName", getDeptName())
                .append("trainingLevel", getTrainingLevel())
                .append("grade", getGrade())
                .append("address", getAddress())
                .append("nation", getNation())
                .append("campus", getCampus())
                .append("studentTag", getStudentTag())
                .append("accommodation", getAccommodation())
                .append("accommodationPark", getAccommodationPark())
                .append("dormitoryNo", getDormitoryNo())
                .append("isOnSchool", getIsOnSchool())
                .append("controlLevel", getControlLevel())
                .append("notSchoolReason", getNotSchoolReason())
                .append("placeToSchoolLevel", getPlaceToSchoolLevel())
                .append("placeToSchool", getPlaceToSchool())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
