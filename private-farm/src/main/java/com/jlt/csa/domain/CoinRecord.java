package com.jlt.csa.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 金币记录对象 csa_coin_record
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public class CoinRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录id */
    private Long id;

    /** 会员id */
    @Excel(name = "会员id")
    private Long memberId;

    /** 业务id */
    private Long opId;

    /** 业务类型 */
    @Excel(name = "业务类型")
    private String opType;

    /** 金币花费 */
    @Excel(name = "金币花费")
    private BigDecimal coins;

    /** 金币余额 */
    @Excel(name = "金币余额")
    private BigDecimal coinBalance;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 业务发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "业务发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date opTime;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志 */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }
    public void setOpId(Long opId) 
    {
        this.opId = opId;
    }

    public Long getOpId() 
    {
        return opId;
    }
    public void setOpType(String opType) 
    {
        this.opType = opType;
    }

    public String getOpType() 
    {
        return opType;
    }
    public void setCoins(BigDecimal coins) 
    {
        this.coins = coins;
    }

    public BigDecimal getCoins() 
    {
        return coins;
    }
    public void setCoinBalance(BigDecimal coinBalance) 
    {
        this.coinBalance = coinBalance;
    }

    public BigDecimal getCoinBalance() 
    {
        return coinBalance;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setOpTime(Date opTime) 
    {
        this.opTime = opTime;
    }

    public Date getOpTime() 
    {
        return opTime;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("memberId", getMemberId())
            .append("opId", getOpId())
            .append("opType", getOpType())
            .append("coins", getCoins())
            .append("coinBalance", getCoinBalance())
            .append("description", getDescription())
            .append("opTime", getOpTime())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
