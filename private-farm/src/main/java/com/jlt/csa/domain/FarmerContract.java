package com.jlt.csa.domain;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员签约对象 csa_farmer_contract
 * 
 * @author 郏磊涛
 * @date 2022-04-01
 */
public class FarmerContract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 合约id */
    private Long contractId;

    /** 会员id */
    @Excel(name = "会员id")
    private Long farmerId;

    /** 会员姓名 */
    @Excel(name = "会员姓名")
    private String memberName;

    /** 合约金币 */
    @Excel(name = "合约金币")
    private BigDecimal coins;

    /** 合约账户金额 */
    @Excel(name = "合约账户金额")
    private BigDecimal balance;

    /** 合约斤数 */
    @Excel(name = "合约斤数")
    private BigDecimal weight;

    /** 合约拱棚年限 */
    @Excel(name = "合约拱棚年限")
    private Long archedYears;

    /** 激活方式 */
    @Excel(name = "激活方式")
    private String activateWay;

    /** 加入凭据号码 */
    @Excel(name = "加入凭据号码")
    private String joinCredentialNo;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date joinDate;

    /** 到期日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 合约天数 */
    @Excel(name = "合约天数")
    private Long days;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /**
     * 根据生效日期、到期天数计算天数，包含起止日，即两者相加+1
     * @return
     */
    public Long countDays() {
        if (joinDate == null) {
            throw new RuntimeException("缺少生效日期！");
        }

        if (dueDate == null) {
            throw new RuntimeException("缺少终止日期！");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(simpleDateFormat.format(joinDate), dateTimeFormatter);
        LocalDate endDate = LocalDate.parse(simpleDateFormat.format(dueDate), dateTimeFormatter);

        this.days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        return this.days;
    }

    public void setContractId(Long contractId) 
    {
        this.contractId = contractId;
    }

    public Long getContractId() 
    {
        return contractId;
    }
    public void setFarmerId(Long farmerId) 
    {
        this.farmerId = farmerId;
    }

    public Long getFarmerId() 
    {
        return farmerId;
    }
    public void setMemberName(String memberName) 
    {
        this.memberName = memberName;
    }

    public String getMemberName() 
    {
        return memberName;
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
    public void setActivateWay(String activateWay)
    {
        this.activateWay = activateWay;
    }

    public String getActivateWay()
    {
        return activateWay;
    }
    public void setJoinCredentialNo(String joinCredentialNo) 
    {
        this.joinCredentialNo = joinCredentialNo;
    }

    public String getJoinCredentialNo() 
    {
        return joinCredentialNo;
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
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
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
            .append("contractId", getContractId())
            .append("farmerId", getFarmerId())
            .append("memberName", getMemberName())
            .append("coins", getCoins())
            .append("balance", getBalance())
            .append("weight", getWeight())
            .append("archedYears", getArchedYears())
            .append("activateMode", getActivateWay())
            .append("joinCredentialNo", getJoinCredentialNo())
            .append("joinDate", getJoinDate())
            .append("dueDate", getDueDate())
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
