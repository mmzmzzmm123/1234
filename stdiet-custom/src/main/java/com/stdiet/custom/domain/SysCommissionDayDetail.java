package com.stdiet.custom.domain;

import com.stdiet.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class SysCommissionDayDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //用户ID
    private Long userId;

    //用户姓名
    private String nickName;

    //用户账户
    private String userName;

    //用户岗位ID
    private Long postId;

    //岗位名称
    private String postName;

    private BigDecimal totalCommissionAmount;

    private BigDecimal totalHasSentCommissionAmount;

    private BigDecimal totalNotSentCommissionAmount;

    private List<Map<String, Object>> sendDetailList;


    //提成比例
    private Float rate;

    //订单成交总额
    private BigDecimal orderTotalAmount;

    //服务总天数
    private Integer serverTotalDay;

    //服务总额（每笔订单的服务天数 * 每天金额，相加）
    private BigDecimal serverTotalAmount;

    //服务订单总数量
    private Integer serverOrderTotalCount;

    //服务暂停总天数
    private Integer serverOrderPauseTotalDay;

    //服务提成金额
    private BigDecimal commissionTotalAmount;

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

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public BigDecimal getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public Integer getServerTotalDay() {
        return serverTotalDay;
    }

    public void setServerTotalDay(Integer serverTotalDay) {
        this.serverTotalDay = serverTotalDay;
    }

    public BigDecimal getServerTotalAmount() {
        return serverTotalAmount;
    }

    public void setServerTotalAmount(BigDecimal serverTotalAmount) {
        this.serverTotalAmount = serverTotalAmount;
    }

    public Integer getServerOrderTotalCount() {
        return serverOrderTotalCount;
    }

    public void setServerOrderTotalCount(Integer serverOrderTotalCount) {
        this.serverOrderTotalCount = serverOrderTotalCount;
    }

    public Integer getServerOrderPauseTotalDay() {
        return serverOrderPauseTotalDay;
    }

    public void setServerOrderPauseTotalDay(Integer serverOrderPauseTotalDay) {
        this.serverOrderPauseTotalDay = serverOrderPauseTotalDay;
    }

    public BigDecimal getCommissionTotalAmount() {
        return commissionTotalAmount;
    }

    public void setCommissionTotalAmount(BigDecimal commissionTotalAmount) {
        this.commissionTotalAmount = commissionTotalAmount;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
