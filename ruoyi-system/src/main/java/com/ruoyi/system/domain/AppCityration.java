package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 app_cityRation
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public class AppCityration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long mId;

    /** 城市名 */
    @Excel(name = "城市名")
    private String mCityName;

    /** 观看直播人数 */
    @Excel(name = "观看直播人数")
    private Long mNum;

    /** 观看直播占比 */
    @Excel(name = "观看直播占比")
    private Long mRation;

    public void setmId(Long mId) 
    {
        this.mId = mId;
    }

    public Long getmId() 
    {
        return mId;
    }
    public void setmCityName(String mCityName) 
    {
        this.mCityName = mCityName;
    }

    public String getmCityName() 
    {
        return mCityName;
    }
    public void setmNum(Long mNum) 
    {
        this.mNum = mNum;
    }

    public Long getmNum() 
    {
        return mNum;
    }
    public void setmRation(Long mRation) 
    {
        this.mRation = mRation;
    }

    public Long getmRation() 
    {
        return mRation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mId", getmId())
            .append("mCityName", getmCityName())
            .append("mNum", getmNum())
            .append("mRation", getmRation())
            .toString();
    }
}
