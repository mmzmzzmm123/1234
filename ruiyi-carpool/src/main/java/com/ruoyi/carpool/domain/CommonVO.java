package com.ruoyi.carpool.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class CommonVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单流水号
     */
    private String orderNum ;

    /**
     * 平台用户唯一ID
     */
    private String userID;


    /**
     * 参与拼单人的openID
     */
    private String openId;

    /**
     * 用户名
     */
    private String name ;

    /**
     * 电话
     */
    private String phone ;

    /**
     * 申请拼单的人数
     */
    private Integer num;



    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CommonVO{" +
                "orderNum='" + orderNum + '\'' +
                ", userID='" + userID + '\'' +
                ", openId='" + openId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", num=" + num +
                '}';
    }
}
