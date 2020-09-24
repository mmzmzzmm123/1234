package com.ruoyi.custom.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 业务提成比例对象 sys_commision
 * 
 * @author wonder
 * @date 2020-09-24
 */
public class SysCommision extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long ruleId;

    /** 业务员 */
    private Long userId;

    /** 业务员 */
    @Excel(name = "业务员")
    private String nickName;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 分成比例 */
    @Excel(name = "分成比例")
    private Long rate;

    public void setRuleId(Long ruleId) 
    {
        this.ruleId = ruleId;
    }

    public Long getRuleId() 
    {
        return ruleId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getAmount()
    {
        return amount;
    }
    public void setRate(Long rate) 
    {
        this.rate = rate;
    }

    public Long getRate() 
    {
        return rate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ruleId", getRuleId())
            .append("userId", getUserId())
            .append("nickName", getNickName())
            .append("amount", getAmount())
            .append("rate", getRate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
