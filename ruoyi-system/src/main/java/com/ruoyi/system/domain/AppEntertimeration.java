package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 app_enterTimeRation
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public class AppEntertimeration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long mId;

    /** 进入时间段 */
    @Excel(name = "进入时间段")
    private String mTimeQuantum;

    /** 进入人数 */
    @Excel(name = "进入人数")
    private Long mTimeQuantuNum;

    /** 进入占比 */
    @Excel(name = "进入占比")
    private Long mTimeQuantueRation;

    public void setmId(Long mId) 
    {
        this.mId = mId;
    }

    public Long getmId() 
    {
        return mId;
    }
    public void setmTimeQuantum(String mTimeQuantum) 
    {
        this.mTimeQuantum = mTimeQuantum;
    }

    public String getmTimeQuantum() 
    {
        return mTimeQuantum;
    }
    public void setmTimeQuantuNum(Long mTimeQuantuNum) 
    {
        this.mTimeQuantuNum = mTimeQuantuNum;
    }

    public Long getmTimeQuantuNum() 
    {
        return mTimeQuantuNum;
    }
    public void setmTimeQuantueRation(Long mTimeQuantueRation) 
    {
        this.mTimeQuantueRation = mTimeQuantueRation;
    }

    public Long getmTimeQuantueRation() 
    {
        return mTimeQuantueRation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mId", getmId())
            .append("mTimeQuantum", getmTimeQuantum())
            .append("mTimeQuantuNum", getmTimeQuantuNum())
            .append("mTimeQuantueRation", getmTimeQuantueRation())
            .toString();
    }
}
