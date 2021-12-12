package com.ruoyi.carpool.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CommonVO {
    /**
     * 订单流水号
     */
    private String orderNum ;

    /**
     * 平台用户唯一ID
     */
    private String userID;


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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderNum", getOrderNum())
                .append("userID", getUserID())
                .append("num", getNum())
                .toString();
    }
}
