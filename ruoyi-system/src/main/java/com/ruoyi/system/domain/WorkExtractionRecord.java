package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 work_extraction_record
 * 
 * @author ruoyi
 * @date 2022-08-07
 */
public class WorkExtractionRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long extractionRecordId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String batchNo;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String reactionCode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String standing;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String arg1;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String arg2;

    /** 1过程设定，2结果 */
    @Excel(name = "1过程设定，2结果")
    private Long type;

    public void setExtractionRecordId(Long extractionRecordId) 
    {
        this.extractionRecordId = extractionRecordId;
    }

    public Long getExtractionRecordId() 
    {
        return extractionRecordId;
    }
    public void setBatchNo(String batchNo) 
    {
        this.batchNo = batchNo;
    }

    public String getBatchNo() 
    {
        return batchNo;
    }
    public void setReactionCode(String reactionCode) 
    {
        this.reactionCode = reactionCode;
    }

    public String getReactionCode() 
    {
        return reactionCode;
    }
    public void setStanding(String standing) 
    {
        this.standing = standing;
    }

    public String getStanding() 
    {
        return standing;
    }
    public void setArg1(String arg1) 
    {
        this.arg1 = arg1;
    }

    public String getArg1() 
    {
        return arg1;
    }
    public void setArg2(String arg2) 
    {
        this.arg2 = arg2;
    }

    public String getArg2() 
    {
        return arg2;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("extractionRecordId", getExtractionRecordId())
            .append("batchNo", getBatchNo())
            .append("reactionCode", getReactionCode())
            .append("standing", getStanding())
            .append("arg1", getArg1())
            .append("arg2", getArg2())
            .append("type", getType())
            .toString();
    }
}
