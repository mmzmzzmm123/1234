package com.jlt.csa.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员卡对象 csa_member_card
 * 
 * @author JiaLeitao
 * @date 2022-03-26
 */
public class CsaMemberCard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 卡id */
    private Long id;

    /** 卡号 */
    @Excel(name = "卡号")
    private String cardno;

    /** 密码 */
    private String passwd;

    /** 激活有效期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "激活有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date activationValidity;

    /** 可用状态 */
    @Excel(name = "可用状态")
    private String usabilityStatus;

    /** 激活状态 */
    @Excel(name = "激活状态")
    private String activeStatus;

    /** 平方米 */
    @Excel(name = "平方米")
    private BigDecimal m2;

    /** 土地年限 */
    @Excel(name = "土地年限")
    private Integer landYears;

    /** 拱棚年限 */
    @Excel(name = "拱棚年限")
    private Integer archedYears;

    /** 金币 */
    @Excel(name = "金币")
    private BigDecimal coins;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal balance;

    /** 重量 */
    @Excel(name = "重量")
    private BigDecimal weight;

    /** 套餐代码 */
    @Excel(name = "套餐代码")
    private String setMeal;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCardno(String cardno) 
    {
        this.cardno = cardno;
    }

    public String getCardno() 
    {
        return cardno;
    }
    public void setPasswd(String passwd) 
    {
        this.passwd = passwd;
    }

    public String getPasswd() 
    {
        return passwd;
    }
    public void setActivationValidity(Date activationValidity) 
    {
        this.activationValidity = activationValidity;
    }

    public Date getActivationValidity() 
    {
        return activationValidity;
    }
    public void setUsabilityStatus(String usabilityStatus) 
    {
        this.usabilityStatus = usabilityStatus;
    }

    public String getUsabilityStatus() 
    {
        return usabilityStatus;
    }
    public void setActiveStatus(String activeStatus) 
    {
        this.activeStatus = activeStatus;
    }

    public String getActiveStatus() 
    {
        return activeStatus;
    }
    public void setM2(BigDecimal m2) 
    {
        this.m2 = m2;
    }

    public BigDecimal getM2() 
    {
        return m2;
    }
    public void setLandYears(Integer landYears) 
    {
        this.landYears = landYears;
    }

    public Integer getLandYears() 
    {
        return landYears;
    }
    public void setArchedYears(Integer archedYears) 
    {
        this.archedYears = archedYears;
    }

    public Integer getArchedYears() 
    {
        return archedYears;
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
    public void setSetMeal(String setMeal) 
    {
        this.setMeal = setMeal;
    }

    public String getSetMeal() 
    {
        return setMeal;
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
            .append("cardno", getCardno())
            .append("passwd", getPasswd())
            .append("activationValidity", getActivationValidity())
            .append("usabilityStatus", getUsabilityStatus())
            .append("activeStatus", getActiveStatus())
            .append("m2", getM2())
            .append("landYears", getLandYears())
            .append("archedYears", getArchedYears())
            .append("coins", getCoins())
            .append("balance", getBalance())
            .append("weight", getWeight())
            .append("setMeal", getSetMeal())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
