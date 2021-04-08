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

    /** 情绪 */
    private String emotion;

    /** 食谱之外的食物 */
    private String slyEatFood;

    /** 是否便秘（Y是 N否） */
    private String constipation;

    /** 早餐照片 */
    private String breakfastImages;

    /** 午餐照片 */
    private String lunchImages;

    /** 午餐照片 */
    private String dinnerImages;

    /** 加餐照片 */
    private String extraMealImages;

    /** 体型对比照 */
    private String bodyImages;

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

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getSlyEatFood() {
        return slyEatFood;
    }

    public void setSlyEatFood(String slyEatFood) {
        this.slyEatFood = slyEatFood;
    }

    public String getConstipation() {
        return constipation;
    }

    public void setConstipation(String constipation) {
        this.constipation = constipation == "Y" ? "是" : "否";
    }

    public String getBreakfastImages() {
        return breakfastImages;
    }

    public void setBreakfastImages(String breakfastImages) {
        this.breakfastImages = breakfastImages;
    }

    public String getLunchImages() {
        return lunchImages;
    }

    public void setLunchImages(String lunchImages) {
        this.lunchImages = lunchImages;
    }

    public String getDinnerImages() {
        return dinnerImages;
    }

    public void setDinnerImages(String dinnerImages) {
        this.dinnerImages = dinnerImages;
    }

    public String getExtraMealImages() {
        return extraMealImages;
    }

    public void setExtraMealImages(String extraMealImages) {
        this.extraMealImages = extraMealImages;
    }

    public String getBodyImages() {
        return bodyImages;
    }

    public void setBodyImages(String bodyImages) {
        this.bodyImages = bodyImages;
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
