package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 廉政档案对象 di_integrity_archives
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
public class DiIntegrityArchives extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idNumber;

    /** 工号 */
    @Excel(name = "工号")
    private String jobId;

    /** 出生年月 */
    @Excel(name = "出生年月")
    private String birthDate;

    /** 职级 */
    @Excel(name = "职级")
    private String positionLevel;

    /** 学历 */
    @Excel(name = "学历")
    private String qualification;

    /** 毕业学校名称 */
    @Excel(name = "毕业学校名称")
    private String graduationSchoolName;

    /** 荣誉称号 */
    @Excel(name = "荣誉称号")
    private String honoraryTitleFile;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setIdNumber(String idNumber) 
    {
        this.idNumber = idNumber;
    }

    public String getIdNumber() 
    {
        return idNumber;
    }
    public void setJobId(String jobId) 
    {
        this.jobId = jobId;
    }

    public String getJobId() 
    {
        return jobId;
    }
    public void setBirthDate(String birthDate) 
    {
        this.birthDate = birthDate;
    }

    public String getBirthDate() 
    {
        return birthDate;
    }
    public void setPositionLevel(String positionLevel) 
    {
        this.positionLevel = positionLevel;
    }

    public String getPositionLevel() 
    {
        return positionLevel;
    }
    public void setQualification(String qualification) 
    {
        this.qualification = qualification;
    }

    public String getQualification() 
    {
        return qualification;
    }
    public void setGraduationSchoolName(String graduationSchoolName) 
    {
        this.graduationSchoolName = graduationSchoolName;
    }

    public String getGraduationSchoolName() 
    {
        return graduationSchoolName;
    }
    public void setHonoraryTitleFile(String honoraryTitleFile) 
    {
        this.honoraryTitleFile = honoraryTitleFile;
    }

    public String getHonoraryTitleFile() 
    {
        return honoraryTitleFile;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("idNumber", getIdNumber())
            .append("jobId", getJobId())
            .append("birthDate", getBirthDate())
            .append("positionLevel", getPositionLevel())
            .append("qualification", getQualification())
            .append("graduationSchoolName", getGraduationSchoolName())
            .append("honoraryTitleFile", getHonoraryTitleFile())
            .append("remarks", getRemarks())
            .toString();
    }
}
