package com.stdiet.custom.page;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.utils.StringUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class WxLogInfo {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Long water;

    private String sleepTime;

    private String wakeupTime;

    private String sport;

    private BigDecimal weight;

    private String diet;

    private String insomnia;

    private String defecation;

    private String remark;

    /**
     * 情绪
     */
    private String emotion;

    /**
     * 食谱之外的食物
     */
    private String slyEatFood;

    /**
     * 是否便秘（Y是 N否）
     */
    private String constipation;

    /**
     * 食材描述
     */
    private String ingredientDescribe;

    //全部食材照片
    private String allImages;

    /**
     * 早餐照片
     */
//    private String breakfastImages;

    /**
     * 午餐照片
     */
//    private String lunchImages;

    /**
     * 午餐照片
     */
//    private String dinnerImages;

    /**
     * 加餐照片
     */
//    private String extraMealImages;

    /**
     * 体型对比照
     */
//    private String bodyImages;

    /**
     * 服务建议
     */
    private String suggest;

    /**
     * 目标体重
     */
    private BigDecimal targetWeight;

    /**
     * 执行评分，五分制
     */
    private BigDecimal executionScore;

    /**
     * 点评
     */
    private String comment;

    private List<String> allImagesUrl;

    private List<String> breakfastImagesUrl;

    private List<String> lunchImagesUrl;

    private List<String> dinnerImagesUrl;

    private List<String> extraMealImagesUrl;

    private List<String> bodyImagesUrl;

    private String healthManifesto;

    public void setDefecation(String defecation) {
        if (StringUtils.isAlpha(defecation)) {
//            this.defecation = defecation.equals("Y") ? "是" : "否";
            this.defecation = defecation.equals("Y") ? "顺畅" : "其他";
        } else {
            this.defecation = defecation;
        }
    }

    public void setDiet(String diet) {
        this.diet = diet.equals("Y") ? "是" : "否";
    }

    public void setInsomnia(String insomnia) {
        this.insomnia = insomnia.equals("Y") ? "是" : "否";
    }

    public void setSport(String sport) {
        this.sport = sport.equals("Y") ? "是" : "否";
    }

    public void setConstipation(String constipation) {
        this.constipation = constipation.equals("Y") ? "是" : "否";
    }

}
