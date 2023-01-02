package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 app_operatorRation
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public class AppOperatorration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long mId;

    /** 运营商类型 */
    @Excel(name = "运营商类型")
    private String mOperatorType;

    /** 使用人数 */
    @Excel(name = "使用人数")
    private Long mOperatorNum;

    /** 使用占比 */
    @Excel(name = "使用占比")
    private Long mOperatorRation;

    public void setmId(Long mId) 
    {
        this.mId = mId;
    }

    public Long getmId() 
    {
        return mId;
    }
    public void setmOperatorType(String mOperatorType) 
    {
        this.mOperatorType = mOperatorType;
    }

    public String getmOperatorType() 
    {
        return mOperatorType;
    }
    public void setmOperatorNum(Long mOperatorNum) 
    {
        this.mOperatorNum = mOperatorNum;
    }

    public Long getmOperatorNum() 
    {
        return mOperatorNum;
    }
    public void setmOperatorRation(Long mOperatorRation) 
    {
        this.mOperatorRation = mOperatorRation;
    }

    public Long getmOperatorRation() 
    {
        return mOperatorRation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mId", getmId())
            .append("mOperatorType", getmOperatorType())
            .append("mOperatorNum", getmOperatorNum())
            .append("mOperatorRation", getmOperatorRation())
            .toString();
    }
}
