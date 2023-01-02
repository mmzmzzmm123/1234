package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 app_phoneRation
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public class AppPhoneration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long mId;

    /** 手机类型 */
    @Excel(name = "手机类型")
    private String mPhoneType;

    /** 使用人数 */
    @Excel(name = "使用人数")
    private Long mPhoneNum;

    /** 使用占比 */
    @Excel(name = "使用占比")
    private Long mPhoneRation;

    public void setmId(Long mId) 
    {
        this.mId = mId;
    }

    public Long getmId() 
    {
        return mId;
    }
    public void setmPhoneType(String mPhoneType) 
    {
        this.mPhoneType = mPhoneType;
    }

    public String getmPhoneType() 
    {
        return mPhoneType;
    }
    public void setmPhoneNum(Long mPhoneNum) 
    {
        this.mPhoneNum = mPhoneNum;
    }

    public Long getmPhoneNum() 
    {
        return mPhoneNum;
    }
    public void setmPhoneRation(Long mPhoneRation) 
    {
        this.mPhoneRation = mPhoneRation;
    }

    public Long getmPhoneRation() 
    {
        return mPhoneRation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mId", getmId())
            .append("mPhoneType", getmPhoneType())
            .append("mPhoneNum", getmPhoneNum())
            .append("mPhoneRation", getmPhoneRation())
            .toString();
    }
}
