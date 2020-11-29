package com.stdiet.custom.page;

import com.stdiet.common.utils.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WxLogInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String date;

    private Long water;

    private String sleepTime;

    private String wakeupTime;

    private String sport;

    private BigDecimal weight;

    private String diet;

    private String insomnia;

    private String defecation;

    public String getDate() {
        return date;
    }

    public String getDefecation() {
        return defecation;
    }

    public String getDiet() {
        return diet;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public Long getWater() {
        return water;
    }

    public String getInsomnia() {
        return insomnia;
    }

    public String getSport() {
        return sport;
    }

    public String getWakeupTime() {
        return wakeupTime;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public void setDate(Date date) {
        this.date = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, date);
    }

    public void setDiet(String diet) {
        this.diet = diet == "Y" ? "是" : "否";
    }

    public void setWater(Long water) {
        this.water = water;
    }

    public void setWakeupTime(String wakeupTime) {
        this.wakeupTime = wakeupTime;
    }

    public void setDefecation(String defecation) {
        this.defecation = defecation == "Y" ? "是" : "否";
    }

    public void setSport(String sport) {
        this.sport = sport == "Y" ? "是" : "否";
    }

    public void setInsomnia(String insomnia) {
        this.insomnia = insomnia == "Y" ? "是" : "否";
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }



    @Override
    public String toString() {
        return "WxLogInfo{" +
                "date='" + date + '\'' +
                ", water='" + water + '\'' +
                ", sleepTime='" + sleepTime + '\'' +
                ", wakeupTime='" + wakeupTime + '\'' +
                ", sport='" + sport + '\'' +
                ", weight='" + weight + '\'' +
                ", diet='" + diet + '\'' +
                ", insomnia='" + insomnia + '\'' +
                ", defecation='" + defecation + '\'' +
                '}';
    }
}
