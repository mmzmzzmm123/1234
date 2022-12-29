package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 app_iosRation
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public class AppIosration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long mId;

    /** 系统类型 */
    @Excel(name = "系统类型")
    private String mIosType;

    /** 使用人数 */
    @Excel(name = "使用人数")
    private Long mIosNum;

    /** 使用占比 */
    @Excel(name = "使用占比")
    private Long mIosRation;

    public void setmId(Long mId) 
    {
        this.mId = mId;
    }

    public Long getmId() 
    {
        return mId;
    }
    public void setmIosType(String mIosType) 
    {
        this.mIosType = mIosType;
    }

    public String getmIosType() 
    {
        return mIosType;
    }
    public void setmIosNum(Long mIosNum) 
    {
        this.mIosNum = mIosNum;
    }

    public Long getmIosNum() 
    {
        return mIosNum;
    }
    public void setmIosRation(Long mIosRation) 
    {
        this.mIosRation = mIosRation;
    }

    public Long getmIosRation() 
    {
        return mIosRation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mId", getmId())
            .append("mIosType", getmIosType())
            .append("mIosNum", getmIosNum())
            .append("mIosRation", getmIosRation())
            .toString();
    }
}
