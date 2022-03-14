package com.ruoyi.info.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 交易信息对象 trade_info
 * 
 * @author ruoyi
 * @date 2022-03-10
 */
public class TradeInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 交易编号 */
    @Excel(name = "交易编号")
    private String tradeNo;

    /** 操作人名称 */
    @Excel(name = "操作人名称")
    private String operatorName;

    /** 群编号 */
    @Excel(name = "群编号")
    private String groupNo;

    /** 卡类型 */
    @Excel(name = "卡类型")
    private String cardType;

    /** 币种 */
    @Excel(name = "币种")
    private String currencyType;

    /** 卡面值 */
    @Excel(name = "卡面值")
    private Long cardValue;

    /** 收卡汇率 */
    @Excel(name = "收卡汇率")
    private BigDecimal buyCardExchangeRate;

    /** 收卡成本 */
    @Excel(name = "收卡成本")
    private BigDecimal buyCost;

    /** 奈拉值 */
    @Excel(name = "奈拉值")
    private Long nailaValue;

    /** 代码 */
    @Excel(name = "代码")
    private String code;

    /** 对接群 */
    @Excel(name = "对接群")
    private String commGroup;

    /** 售出价格 */
    @Excel(name = "售出价格")
    private BigDecimal salePrice;

    /** 利润 */
    @Excel(name = "利润")
    private BigDecimal profit;

    /** 银行账号 */
    @Excel(name = "银行账号")
    private String bankAccount;

    /** 支付状态 */
    @Excel(name = "支付状态")
    private String payStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTradeNo(String tradeNo) 
    {
        this.tradeNo = tradeNo;
    }

    public String getTradeNo() 
    {
        return tradeNo;
    }
    public void setOperatorName(String operatorName) 
    {
        this.operatorName = operatorName;
    }

    public String getOperatorName() 
    {
        return operatorName;
    }
    public void setGroupNo(String groupNo) 
    {
        this.groupNo = groupNo;
    }

    public String getGroupNo() 
    {
        return groupNo;
    }
    public void setCardType(String cardType) 
    {
        this.cardType = cardType;
    }

    public String getCardType() 
    {
        return cardType;
    }
    public void setCurrencyType(String currencyType) 
    {
        this.currencyType = currencyType;
    }

    public String getCurrencyType() 
    {
        return currencyType;
    }
    public void setCardValue(Long cardValue) 
    {
        this.cardValue = cardValue;
    }

    public Long getCardValue() 
    {
        return cardValue;
    }
    public void setBuyCardExchangeRate(BigDecimal buyCardExchangeRate) 
    {
        this.buyCardExchangeRate = buyCardExchangeRate;
    }

    public BigDecimal getBuyCardExchangeRate() 
    {
        return buyCardExchangeRate;
    }
    public void setBuyCost(BigDecimal buyCost) 
    {
        this.buyCost = buyCost;
    }

    public BigDecimal getBuyCost() 
    {
        return buyCost;
    }
    public void setNailaValue(Long nailaValue) 
    {
        this.nailaValue = nailaValue;
    }

    public Long getNailaValue() 
    {
        return nailaValue;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setCommGroup(String commGroup) 
    {
        this.commGroup = commGroup;
    }

    public String getCommGroup() 
    {
        return commGroup;
    }
    public void setSalePrice(BigDecimal salePrice) 
    {
        this.salePrice = salePrice;
    }

    public BigDecimal getSalePrice() 
    {
        return salePrice;
    }
    public void setProfit(BigDecimal profit) 
    {
        this.profit = profit;
    }

    public BigDecimal getProfit() 
    {
        return profit;
    }
    public void setBankAccount(String bankAccount) 
    {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount() 
    {
        return bankAccount;
    }
    public void setPayStatus(String payStatus) 
    {
        this.payStatus = payStatus;
    }

    public String getPayStatus() 
    {
        return payStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("tradeNo", getTradeNo())
            .append("operatorName", getOperatorName())
            .append("groupNo", getGroupNo())
            .append("cardType", getCardType())
            .append("currencyType", getCurrencyType())
            .append("cardValue", getCardValue())
            .append("buyCardExchangeRate", getBuyCardExchangeRate())
            .append("buyCost", getBuyCost())
            .append("nailaValue", getNailaValue())
            .append("code", getCode())
            .append("commGroup", getCommGroup())
            .append("salePrice", getSalePrice())
            .append("profit", getProfit())
            .append("bankAccount", getBankAccount())
            .append("payStatus", getPayStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
