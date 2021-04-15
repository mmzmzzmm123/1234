package com.stdiet.custom.page;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.utils.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WxLogInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String date;

    private Long water;

    private String sleepTime;

    private String wakeupTime;

    private String sport;

    private BigDecimal weight;

    private String diet;

    private String insomnia;

    private String defecation;

    private String remark;

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

    /** 服务建议 */
    private String suggest;

    /** 目标体重 */
    private BigDecimal targetWeight;

    /** 执行评分，五分制 */
    private BigDecimal executionScore;

    /** 点评 */
    private String comment;

    private List<String> breakfastImagesUrl;

    private List<String> lunchImagesUrl;

    private List<String> dinnerImagesUrl;

    private List<String> extraMealImagesUrl;

    private List<String> bodyImagesUrl;

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

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public BigDecimal getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(BigDecimal targetWeight) {
        this.targetWeight = targetWeight;
    }

    public BigDecimal getExecutionScore() {
        return executionScore;
    }

    public void setExecutionScore(BigDecimal executionScore) {
        this.executionScore = executionScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getBreakfastImagesUrl() {
        return breakfastImagesUrl;
    }

    public void setBreakfastImagesUrl(List<String> breakfastImagesUrl) {
        this.breakfastImagesUrl = breakfastImagesUrl;
    }

    public List<String> getLunchImagesUrl() {
        return lunchImagesUrl;
    }

    public void setLunchImagesUrl(List<String> lunchImagesUrl) {
        this.lunchImagesUrl = lunchImagesUrl;
    }

    public List<String> getDinnerImagesUrl() {
        return dinnerImagesUrl;
    }

    public void setDinnerImagesUrl(List<String> dinnerImagesUrl) {
        this.dinnerImagesUrl = dinnerImagesUrl;
    }

    public List<String> getExtraMealImagesUrl() {
        return extraMealImagesUrl;
    }

    public void setExtraMealImagesUrl(List<String> extraMealImagesUrl) {
        this.extraMealImagesUrl = extraMealImagesUrl;
    }

    public List<String> getBodyImagesUrl() {
        return bodyImagesUrl;
    }

    public void setBodyImagesUrl(List<String> bodyImagesUrl) {
        this.bodyImagesUrl = bodyImagesUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
