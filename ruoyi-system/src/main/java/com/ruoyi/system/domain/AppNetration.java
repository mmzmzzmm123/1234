package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 app_netRation
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public class AppNetration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long mId;

    /** 网络类型 */
    @Excel(name = "网络类型")
    private String mNetType;

    /** 占比 */
    @Excel(name = "占比")
    private Long mNetRation;

    public void setmId(Long mId) 
    {
        this.mId = mId;
    }

    public Long getmId() 
    {
        return mId;
    }
    public void setmNetType(String mNetType) 
    {
        this.mNetType = mNetType;
    }

    public String getmNetType() 
    {
        return mNetType;
    }
    public void setmNetRation(Long mNetRation) 
    {
        this.mNetRation = mNetRation;
    }

    public Long getmNetRation() 
    {
        return mNetRation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mId", getmId())
            .append("mNetType", getmNetType())
            .append("mNetRation", getmNetRation())
            .toString();
    }
}
