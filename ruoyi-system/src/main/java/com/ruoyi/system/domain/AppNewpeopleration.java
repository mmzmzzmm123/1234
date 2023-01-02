package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 app_newpeopleRation
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public class AppNewpeopleration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long mId;

    /** 时间段 */
    @Excel(name = "时间段")
    private String mTimeQuantum;

    /** 新人数量 */
    @Excel(name = "新人数量")
    private Long mNewpeople;

    /** 当前时间段总人数 */
    @Excel(name = "当前时间段总人数")
    private Long mTotalPeople;

    /** 当前时间段新人占比 */
    @Excel(name = "当前时间段新人占比")
    private Long mNewpeopleRation;

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
    public void setmNewpeople(Long mNewpeople) 
    {
        this.mNewpeople = mNewpeople;
    }

    public Long getmNewpeople() 
    {
        return mNewpeople;
    }
    public void setmTotalPeople(Long mTotalPeople) 
    {
        this.mTotalPeople = mTotalPeople;
    }

    public Long getmTotalPeople() 
    {
        return mTotalPeople;
    }
    public void setmNewpeopleRation(Long mNewpeopleRation) 
    {
        this.mNewpeopleRation = mNewpeopleRation;
    }

    public Long getmNewpeopleRation() 
    {
        return mNewpeopleRation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mId", getmId())
            .append("mTimeQuantum", getmTimeQuantum())
            .append("mNewpeople", getmNewpeople())
            .append("mTotalPeople", getmTotalPeople())
            .append("mNewpeopleRation", getmNewpeopleRation())
            .toString();
    }
}
