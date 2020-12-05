package com.stdiet.custom.page;

import com.stdiet.common.utils.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WxServeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String serveTime;

    private String serveStatus;

    private String startTime;

    private String weight;

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServeTime() {
        return serveTime;
    }

    public void setServeTime(String serveTime) {
        this.serveTime = serveTime;
    }

    public String getServeStatus() {
        return serveStatus;
    }

    public void setServeStatus(String serveStatus) {
        if (serveStatus.equals("0")) {
            this.serveStatus = "进行中";
        } else if (serveStatus.equals("1")) {
            this.serveStatus = "暂停";
        } else if (serveStatus.equals("")) {
            this.serveStatus = "已完成";
        }
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, startTime);
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight.toString() + "斤";
    }

    @Override
    public String toString() {
        return "WxServeInfo{" +
                "serveTime='" + serveTime + '\'' +
                ", serveStatus='" + serveStatus + '\'' +
                ", startTime='" + startTime + '\'' +
                ", weight='" + weight + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
