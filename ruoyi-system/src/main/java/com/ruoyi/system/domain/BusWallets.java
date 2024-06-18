package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 存储用户的账户余额信息对象 bus_wallets
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
public class BusWallets extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 钱包ID */
    private Long walletId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 钱包余额 */
    @Excel(name = "钱包余额")
    private BigDecimal balance;

    /** 冻结金额 */
    @Excel(name = "冻结金额")
    private BigDecimal frozenBalance;

    /** 版本号，用于乐观锁 */
    @Excel(name = "版本号，用于乐观锁")
    private Long version;

    /** 钱包更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "钱包更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    /** 钱包创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "钱包创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    public void setWalletId(Long walletId) 
    {
        this.walletId = walletId;
    }

    public Long getWalletId() 
    {
        return walletId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setBalance(BigDecimal balance) 
    {
        this.balance = balance;
    }

    public BigDecimal getBalance() 
    {
        return balance;
    }
    public void setFrozenBalance(BigDecimal frozenBalance) 
    {
        this.frozenBalance = frozenBalance;
    }

    public BigDecimal getFrozenBalance() 
    {
        return frozenBalance;
    }
    public void setVersion(Long version) 
    {
        this.version = version;
    }

    public Long getVersion() 
    {
        return version;
    }
    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("walletId", getWalletId())
            .append("userId", getUserId())
            .append("balance", getBalance())
            .append("frozenBalance", getFrozenBalance())
            .append("version", getVersion())
            .append("updatedAt", getUpdatedAt())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}
