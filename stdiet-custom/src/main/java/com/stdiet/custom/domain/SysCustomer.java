package com.stdiet.custom.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 客户信息对象 sys_customer
 *
 * @author xzj
 * @date 2020-12-31
 */
public class SysCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 名字 */
    @Excel(name = "名字")
    private String name;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 进粉日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进粉日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fansTime;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 付款日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "付款日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payDate;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 购买月数 */
    @Excel(name = "购买月数")
    private Long purchaseNum;

    /** 累计总金额 */
    @Excel(name = "累计总金额")
    private BigDecimal payTotal;

    /** 主营养师 */
    @Excel(name = "主营养师")
    private Long mainDietitian;

    /** 营养师助理 */
    @Excel(name = "营养师助理")
    private Long assistantDietitian;

    /** 售后营养师 */
    @Excel(name = "售后营养师")
    private Long afterDietitian;

    /** 销售人员 */
    @Excel(name = "销售人员")
    private Long salesman;

    /** 负责人 */
    @Excel(name = "负责人")
    private Long chargePerson;

    /** 跟进状态 */
    @Excel(name = "跟进状态")
    private Long followStatus;

    /** 删除标识(0 未删除 1已删除) */
    private Integer delFlag;

    /** 体征数据，非持久化字段 */
    private SysCustomerPhysicalSigns sign;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setPayDate(Date payDate)
    {
        this.payDate = payDate;
    }

    public Date getPayDate()
    {
        return payDate;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setPurchaseNum(Long purchaseNum)
    {
        this.purchaseNum = purchaseNum;
    }

    public Long getPurchaseNum()
    {
        return purchaseNum;
    }
    public void setPayTotal(BigDecimal payTotal)
    {
        this.payTotal = payTotal;
    }

    public BigDecimal getPayTotal()
    {
        return payTotal;
    }
    public void setMainDietitian(Long mainDietitian)
    {
        this.mainDietitian = mainDietitian;
    }

    public Long getMainDietitian()
    {
        return mainDietitian;
    }
    public void setAssistantDietitian(Long assistantDietitian)
    {
        this.assistantDietitian = assistantDietitian;
    }

    public Long getAssistantDietitian()
    {
        return assistantDietitian;
    }
    public void setAfterDietitian(Long afterDietitian)
    {
        this.afterDietitian = afterDietitian;
    }

    public Long getAfterDietitian()
    {
        return afterDietitian;
    }
    public void setSalesman(Long salesman)
    {
        this.salesman = salesman;
    }

    public Long getSalesman()
    {
        return salesman;
    }
    public void setChargePerson(Long chargePerson)
    {
        this.chargePerson = chargePerson;
    }

    public Long getChargePerson()
    {
        return chargePerson;
    }
    public void setFollowStatus(Long followStatus)
    {
        this.followStatus = followStatus;
    }

    public Long getFollowStatus()
    {
        return followStatus;
    }

    public SysCustomerPhysicalSigns getSign() {
        return sign;
    }

    public void setSign(SysCustomerPhysicalSigns sign) {
        this.sign = sign;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getFansTime() {
        return fansTime;
    }

    public void setFansTime(Date fansTime) {
        this.fansTime = fansTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("phone", getPhone())
                .append("email", getEmail())
                .append("address", getAddress())
                .append("payDate", getPayDate())
                .append("startDate", getStartDate())
                .append("purchaseNum", getPurchaseNum())
                .append("payTotal", getPayTotal())
                .append("mainDietitian", getMainDietitian())
                .append("assistantDietitian", getAssistantDietitian())
                .append("afterDietitian", getAfterDietitian())
                .append("salesman", getSalesman())
                .append("chargePerson", getChargePerson())
                .append("followStatus", getFollowStatus())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}