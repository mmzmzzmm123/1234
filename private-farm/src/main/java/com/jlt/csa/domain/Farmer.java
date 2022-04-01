package com.jlt.csa.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 农场会员对象 csa_farmer
 * 
 * @author JiaLeitao
 * @date 2022-04-01
 */
public class Farmer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会员id */
    private Long farmerId;

    /** 会员姓名 */
    @Excel(name = "会员姓名")
    private String name;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 金币余额 */
    @Excel(name = "金币余额")
    private BigDecimal coins;

    /** 账户余额 */
    @Excel(name = "账户余额")
    private BigDecimal balance;

    /** 重量余额 */
    @Excel(name = "重量余额")
    private BigDecimal weight;

    /** 拱棚年限余额 */
    @Excel(name = "拱棚年限余额")
    private Long archedYears;

    /** 加入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "加入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date joinDate;

    /** 到期日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 是否过期 */
    @Excel(name = "是否过期")
    private String isExpire;

    /** 状态 */
    private String status;

    /** 删除标志 */
    private String delFlag;

    public void setFarmerId(Long farmerId) 
    {
        this.farmerId = farmerId;
    }

    public Long getFarmerId() 
    {
        return farmerId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }
    public void setCoins(BigDecimal coins) 
    {
        this.coins = coins;
    }

    public BigDecimal getCoins() 
    {
        return coins;
    }
    public void setBalance(BigDecimal balance) 
    {
        this.balance = balance;
    }

    public BigDecimal getBalance() 
    {
        return balance;
    }
    public void setWeight(BigDecimal weight) 
    {
        this.weight = weight;
    }

    public BigDecimal getWeight() 
    {
        return weight;
    }
    public void setArchedYears(Long archedYears) 
    {
        this.archedYears = archedYears;
    }

    public Long getArchedYears() 
    {
        return archedYears;
    }
    public void setJoinDate(Date joinDate) 
    {
        this.joinDate = joinDate;
    }

    public Date getJoinDate() 
    {
        return joinDate;
    }
    public void setDueDate(Date dueDate) 
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate() 
    {
        return dueDate;
    }
    public void setIsExpire(String isExpire) 
    {
        this.isExpire = isExpire;
    }

    public String getIsExpire() 
    {
        return isExpire;
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
            .append("farmerId", getFarmerId())
            .append("name", getName())
            .append("gender", getGender())
            .append("coins", getCoins())
            .append("balance", getBalance())
            .append("weight", getWeight())
            .append("archedYears", getArchedYears())
            .append("joinDate", getJoinDate())
            .append("dueDate", getDueDate())
            .append("isExpire", getIsExpire())
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
