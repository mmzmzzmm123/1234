package com.ruoyi.productionManager.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 标准信息对象 standard_info
 * 
 * @author ruoyi
 * @date 2021-10-13
 */
public class StandardInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标准id */
    private Long standardId;

    /** 区域分类 */
    @Excel(name = "区域分类")
    private String areaCategory;

    /** 标准名称 */
    @Excel(name = "标准名称")
    private String standardName;

    /** 标准类型 */
    @Excel(name = "标准类型")
    private String standardCategory;

    /** 实施日期 */
    @Excel(name = "实施日期")
    private String standardBeginDate;

    /** 标准状态 */
    @Excel(name = "标准状态")
    private String standardStatus;

    public void setStandardId(Long standardId) 
    {
        this.standardId = standardId;
    }

    public Long getStandardId() 
    {
        return standardId;
    }
    public void setAreaCategory(String areaCategory) 
    {
        this.areaCategory = areaCategory;
    }

    public String getAreaCategory() 
    {
        return areaCategory;
    }
    public void setStandardName(String standardName) 
    {
        this.standardName = standardName;
    }

    public String getStandardName() 
    {
        return standardName;
    }
    public void setStandardCategory(String standardCategory) 
    {
        this.standardCategory = standardCategory;
    }

    public String getStandardCategory() 
    {
        return standardCategory;
    }
    public void setStandardBeginDate(String standardBeginDate) 
    {
        this.standardBeginDate = standardBeginDate;
    }

    public String getStandardBeginDate() 
    {
        return standardBeginDate;
    }
    public void setStandardStatus(String standardStatus) 
    {
        this.standardStatus = standardStatus;
    }

    public String getStandardStatus() 
    {
        return standardStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("standardId", getStandardId())
            .append("areaCategory", getAreaCategory())
            .append("standardName", getStandardName())
            .append("standardCategory", getStandardCategory())
            .append("standardBeginDate", getStandardBeginDate())
            .append("standardStatus", getStandardStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
