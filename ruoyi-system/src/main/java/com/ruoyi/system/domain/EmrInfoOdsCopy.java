package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * emr原始数据对象 emr_info_ods_copy
 *
 * @author ruoyi
 * @date 2021-11-04
 */
public class EmrInfoOdsCopy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 电子病历ID */
    @Excel(name = "电子病历ID")
    private Long emrId;

    /** 科室编码 */
    @Excel(name = "科室编码")
    private String deptNumber;

    /** 就诊科室 */
    @Excel(name = "就诊科室")
    private String treatmentDepartment;

    /** 就诊日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "就诊日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date treatmentDate;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 年龄 */
    @Excel(name = "年龄")
    private String age;

    /** 身高 */
    @Excel(name = "身高")
    private String height;

    /** 体重 */
    @Excel(name = "体重")
    private String weight;

    /** 主诉 */
    @Excel(name = "主诉")
    private String chiefComplaint;

    /** 现病史 */
    @Excel(name = "现病史")
    private String hsPresentIllness;

    /** 体格检查 */
    @Excel(name = "体格检查")
    private String physicalExamination;

    /** 初步诊断 */
    @Excel(name = "初步诊断")
    private String preliminaryDiagnosis;

    /** 诊断ICD编码 */
    @Excel(name = "诊断ICD编码")
    private String icdId;

    /** 病种中文名 */
    @Excel(name = "病种中文名")
    private String icdName;

    /** 医生姓名 */
    @Excel(name = "医生姓名")
    private String doctorName;

    /** 医生工号 */
    @Excel(name = "医生工号")
    private String doctorNumber;

    /** 医生职称 */
    @Excel(name = "医生职称")
    private String doctorTitle;

    /** 专科病历名称 */
    @Excel(name = "专科病历名称")
    private String specialRecord;

    /** 状态0-未处理 1-已处理 */
    @Excel(name = "状态0-未处理 1-已处理")
    private Integer state;

    /** 是否存在高危病种：0-不存在   1-存在 */
    @Excel(name = "是否存在高危病种：0-不存在   1-存在")
    private Integer isHightDisease;

    /** 诊断结果一致率：是否落在小布top5内：0：不一致 1：一致 */
    @Excel(name = "诊断结果一致率：是否落在小布top5内：0：不一致 1：一致")
    private Integer isTop;

    /** 是否为无依据诊断 ：0-不是无依据诊断    1-是无依据诊断 */
    @Excel(name = "是否为无依据诊断 ：0-不是无依据诊断    1-是无依据诊断")
    private Integer isGroundless;

    /** 小布返回top5数组 */
    @Excel(name = "小布返回top5数组")
    private String xiaobuResult;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setEmrId(Long emrId)
    {
        this.emrId = emrId;
    }

    public Long getEmrId()
    {
        return emrId;
    }
    public void setDeptNumber(String deptNumber)
    {
        this.deptNumber = deptNumber;
    }

    public String getDeptNumber()
    {
        return deptNumber;
    }
    public void setTreatmentDepartment(String treatmentDepartment)
    {
        this.treatmentDepartment = treatmentDepartment;
    }

    public String getTreatmentDepartment()
    {
        return treatmentDepartment;
    }
    public void setTreatmentDate(Date treatmentDate)
    {
        this.treatmentDate = treatmentDate;
    }

    public Date getTreatmentDate()
    {
        return treatmentDate;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    public void setAge(String age)
    {
        this.age = age;
    }

    public String getAge()
    {
        return age;
    }
    public void setHeight(String height)
    {
        this.height = height;
    }

    public String getHeight()
    {
        return height;
    }
    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    public String getWeight()
    {
        return weight;
    }
    public void setChiefComplaint(String chiefComplaint)
    {
        this.chiefComplaint = chiefComplaint;
    }

    public String getChiefComplaint()
    {
        return chiefComplaint;
    }
    public void setHsPresentIllness(String hsPresentIllness)
    {
        this.hsPresentIllness = hsPresentIllness;
    }

    public String getHsPresentIllness()
    {
        return hsPresentIllness;
    }
    public void setPhysicalExamination(String physicalExamination)
    {
        this.physicalExamination = physicalExamination;
    }

    public String getPhysicalExamination()
    {
        return physicalExamination;
    }
    public void setPreliminaryDiagnosis(String preliminaryDiagnosis)
    {
        this.preliminaryDiagnosis = preliminaryDiagnosis;
    }

    public String getPreliminaryDiagnosis()
    {
        return preliminaryDiagnosis;
    }
    public void setIcdId(String icdId)
    {
        this.icdId = icdId;
    }

    public String getIcdId()
    {
        return icdId;
    }
    public void setIcdName(String icdName)
    {
        this.icdName = icdName;
    }

    public String getIcdName()
    {
        return icdName;
    }
    public void setDoctorName(String doctorName)
    {
        this.doctorName = doctorName;
    }

    public String getDoctorName()
    {
        return doctorName;
    }
    public void setDoctorNumber(String doctorNumber)
    {
        this.doctorNumber = doctorNumber;
    }

    public String getDoctorNumber()
    {
        return doctorNumber;
    }
    public void setDoctorTitle(String doctorTitle)
    {
        this.doctorTitle = doctorTitle;
    }

    public String getDoctorTitle()
    {
        return doctorTitle;
    }
    public void setSpecialRecord(String specialRecord)
    {
        this.specialRecord = specialRecord;
    }

    public String getSpecialRecord()
    {
        return specialRecord;
    }
    public void setState(Integer state)
    {
        this.state = state;
    }

    public Integer getState()
    {
        return state;
    }
    public void setIsHightDisease(Integer isHightDisease)
    {
        this.isHightDisease = isHightDisease;
    }

    public Integer getIsHightDisease()
    {
        return isHightDisease;
    }
    public void setIsTop(Integer isTop)
    {
        this.isTop = isTop;
    }

    public Integer getIsTop()
    {
        return isTop;
    }
    public void setIsGroundless(Integer isGroundless)
    {
        this.isGroundless = isGroundless;
    }

    public Integer getIsGroundless()
    {
        return isGroundless;
    }
    public void setXiaobuResult(String xiaobuResult)
    {
        this.xiaobuResult = xiaobuResult;
    }

    public String getXiaobuResult()
    {
        return xiaobuResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("emrId", getEmrId())
            .append("deptNumber", getDeptNumber())
            .append("treatmentDepartment", getTreatmentDepartment())
            .append("treatmentDate", getTreatmentDate())
            .append("sex", getSex())
            .append("age", getAge())
            .append("height", getHeight())
            .append("weight", getWeight())
            .append("chiefComplaint", getChiefComplaint())
            .append("hsPresentIllness", getHsPresentIllness())
            .append("physicalExamination", getPhysicalExamination())
            .append("preliminaryDiagnosis", getPreliminaryDiagnosis())
            .append("icdId", getIcdId())
            .append("icdName", getIcdName())
            .append("doctorName", getDoctorName())
            .append("doctorNumber", getDoctorNumber())
            .append("doctorTitle", getDoctorTitle())
            .append("specialRecord", getSpecialRecord())
            .append("state", getState())
            .append("isHightDisease", getIsHightDisease())
            .append("isTop", getIsTop())
            .append("isGroundless", getIsGroundless())
            .append("xiaobuResult", getXiaobuResult())
            .toString();
    }

}
