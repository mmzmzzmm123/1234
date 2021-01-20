package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class SysCommissionDayDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //用户ID
    private Long userId;

    //用户姓名
    @Excel(name = "业务员")
    private String nickName;

    //用户账户
    private String userName;

    //用户岗位ID
    private Long postId;

    //岗位名称
    @Excel(name = "岗位名称")
    private String postName;

    @Excel(name = "总提成")
    private BigDecimal totalCommissionAmount;

    @Excel(name = "已发放提成")
    private BigDecimal totalHasSentCommissionAmount;

    @Excel(name = "未发放提成")
    private BigDecimal totalNotSentCommissionAmount;

    @Excel(name = "下月应发提成")
    private BigDecimal nextMonthCommission;

    private List<Map<String, Object>> sendDetailList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public BigDecimal getTotalCommissionAmount() {
        return totalCommissionAmount;
    }

    public void setTotalCommissionAmount(BigDecimal totalCommissionAmount) {
        this.totalCommissionAmount = totalCommissionAmount;
    }

    public BigDecimal getTotalHasSentCommissionAmount() {
        return totalHasSentCommissionAmount;
    }

    public void setTotalHasSentCommissionAmount(BigDecimal totalHasSentCommissionAmount) {
        this.totalHasSentCommissionAmount = totalHasSentCommissionAmount;
    }

    public BigDecimal getTotalNotSentCommissionAmount() {
        return totalNotSentCommissionAmount;
    }

    public void setTotalNotSentCommissionAmount(BigDecimal totalNotSentCommissionAmount) {
        this.totalNotSentCommissionAmount = totalNotSentCommissionAmount;
    }

    public List<Map<String, Object>> getSendDetailList() {
        return sendDetailList;
    }

    public void setSendDetailList(List<Map<String, Object>> sendDetailList) {
        this.sendDetailList = sendDetailList;
    }

    public BigDecimal getNextMonthCommission() {
        return nextMonthCommission;
    }

    public void setNextMonthCommission(BigDecimal nextMonthCommission) {
        this.nextMonthCommission = nextMonthCommission;
    }
}
