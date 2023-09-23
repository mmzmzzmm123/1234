package com.ruoyi.fin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 记账账户对象 fin_account
 * 
 * @author ruoyi
 * @date 2023-09-23
 */
public class FinAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long acctId;

    /** 账户名称 */
    @Excel(name = "账户名称")
    private String acctName;

    /** 排序值 */
    @Excel(name = "排序值")
    private Long sortNum;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    public void setAcctId(Long acctId) 
    {
        this.acctId = acctId;
    }

    public Long getAcctId() 
    {
        return acctId;
    }
    public void setAcctName(String acctName) 
    {
        this.acctName = acctName;
    }

    public String getAcctName() 
    {
        return acctName;
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
            .append("acctId", getAcctId())
            .append("acctName", getAcctName())
            .append("sortNum", getSortNum())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
