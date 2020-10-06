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
public class SysCommision extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long ruleId;

    /**
     * 业务员
     */
    private Long userId;

    /**
     * 岗位id
     */
    private Long postId;

    /**
     * 业务员
     */
    @Excel(name = "业务员")
    private String userName;

    @Excel(name = "岗位")
    private String postName;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal amount;

    /**
     * 分成比例
     */
    @Excel(name = "比例", suffix = "%")
    private Float rate;

    /**
     * 提成
     */
    @Excel(name = "提成")
    private BigDecimal commision;

    public BigDecimal getCommision() {
        return commision;
    }

    public void setCommision(BigDecimal commision) {
        this.commision = commision;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostName() {
        return postName;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Float getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("ruleId", getRuleId())
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("postId", getPostId())
                .append("postName", getPostName())
                .append("amount", getAmount())
                .append("commision", getCommision())
                .append("rate", getRate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
