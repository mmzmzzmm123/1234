package com.jlt.csa.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 拱棚使用记录对象 csa_arched_record
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public class ArchedRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 拱棚使用记录id */
    private Long id;

    /** 会员id */
    private Long memberId;

    /** 菜地id */
    private Long gardenId;

    /** 对应年份 */
    @Excel(name = "对应年份")
    private Long yearNo;

    /** 消耗年份 */
    private Long consumYear;

    /** 剩余年份 */
    @Excel(name = "剩余年份")
    private Long remainingYears;

    /** 操作人id */
    @Excel(name = "操作人id")
    private Long doerId;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date doTime;

    /** 有效状态 */
    @Excel(name = "有效状态")
    private String status;

    /** 删除标志 */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }
    public void setGardenId(Long gardenId) 
    {
        this.gardenId = gardenId;
    }

    public Long getGardenId() 
    {
        return gardenId;
    }
    public void setYearNo(Long yearNo) 
    {
        this.yearNo = yearNo;
    }

    public Long getYearNo() 
    {
        return yearNo;
    }
    public void setConsumYear(Long consumYear) 
    {
        this.consumYear = consumYear;
    }

    public Long getConsumYear() 
    {
        return consumYear;
    }
    public void setRemainingYears(Long remainingYears) 
    {
        this.remainingYears = remainingYears;
    }

    public Long getRemainingYears() 
    {
        return remainingYears;
    }
    public void setDoerId(Long doerId) 
    {
        this.doerId = doerId;
    }

    public Long getDoerId() 
    {
        return doerId;
    }
    public void setDoTime(Date doTime) 
    {
        this.doTime = doTime;
    }

    public Date getDoTime() 
    {
        return doTime;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("memberId", getMemberId())
            .append("gardenId", getGardenId())
            .append("yearNo", getYearNo())
            .append("consumYear", getConsumYear())
            .append("remainingYears", getRemainingYears())
            .append("doerId", getDoerId())
            .append("doTime", getDoTime())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
