package com.ruoyi.fin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 记账项目对象 fin_project
 * 
 * @author ruoyi
 * @date 2023-09-15
 */
public class FinProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long projId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projName;

    /** 排序值 */
    @Excel(name = "排序值")
    private Long sortNum;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    public void setProjId(Long projId) 
    {
        this.projId = projId;
    }

    public Long getProjId() 
    {
        return projId;
    }
    public void setProjName(String projName) 
    {
        this.projName = projName;
    }

    public String getProjName() 
    {
        return projName;
    }
    public void setSortNum(Long sortNum) 
    {
        this.sortNum = sortNum;
    }

    public Long getSortNum() 
    {
        return sortNum;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projId", getProjId())
            .append("projName", getProjName())
            .append("sortNum", getSortNum())
            .append("userId", getUserId())
            .toString();
    }
}
