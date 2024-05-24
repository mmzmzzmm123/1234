package com.ruoyi.system.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 典型案例对象 di_classic_case
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
public class DiClassicCase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 案例名称 */
    @Excel(name = "案例名称")
    private String caseName;

    /** 案例分类id */
    @Excel(name = "案例分类id")
    private String caseClassificationId;

    /** 案例分类名称 */
    @Excel(name = "案例分类名称")
    private String caseClassificationName;

    /** 案例编号 */
    @Excel(name = "案例编号")
    private String caseNumber;

    /** 案例简要说明 */
    @Excel(name = "案例简要说明")
    private String briefExplanationCase;

    /** 其它附件上传 */
    @Excel(name = "其它附件上传")
    private String uploadOtherAttachments;

    /** 发布人 */
    @Excel(name = "发布人")
    private String publisher;

    /** 案例分类信息 */
    private List<DiCaseClassification> diCaseClassificationList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCaseName(String caseName) 
    {
        this.caseName = caseName;
    }

    public String getCaseName() 
    {
        return caseName;
    }
    public void setCaseClassificationId(String caseClassificationId) 
    {
        this.caseClassificationId = caseClassificationId;
    }

    public String getCaseClassificationId() 
    {
        return caseClassificationId;
    }
    public void setCaseClassificationName(String caseClassificationName) 
    {
        this.caseClassificationName = caseClassificationName;
    }

    public String getCaseClassificationName() 
    {
        return caseClassificationName;
    }
    public void setCaseNumber(String caseNumber) 
    {
        this.caseNumber = caseNumber;
    }

    public String getCaseNumber() 
    {
        return caseNumber;
    }
    public void setBriefExplanationCase(String briefExplanationCase) 
    {
        this.briefExplanationCase = briefExplanationCase;
    }

    public String getBriefExplanationCase() 
    {
        return briefExplanationCase;
    }
    public void setUploadOtherAttachments(String uploadOtherAttachments) 
    {
        this.uploadOtherAttachments = uploadOtherAttachments;
    }

    public String getUploadOtherAttachments() 
    {
        return uploadOtherAttachments;
    }
    public void setPublisher(String publisher) 
    {
        this.publisher = publisher;
    }

    public String getPublisher() 
    {
        return publisher;
    }

    public List<DiCaseClassification> getDiCaseClassificationList()
    {
        return diCaseClassificationList;
    }

    public void setDiCaseClassificationList(List<DiCaseClassification> diCaseClassificationList)
    {
        this.diCaseClassificationList = diCaseClassificationList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("caseName", getCaseName())
            .append("caseClassificationId", getCaseClassificationId())
            .append("caseClassificationName", getCaseClassificationName())
            .append("caseNumber", getCaseNumber())
            .append("briefExplanationCase", getBriefExplanationCase())
            .append("uploadOtherAttachments", getUploadOtherAttachments())
            .append("publisher", getPublisher())
            .append("diCaseClassificationList", getDiCaseClassificationList())
            .toString();
    }
}
