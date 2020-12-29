package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 食材对象 sys_ingredient
 *
 * @author wonder
 * @date 2020-12-15
 */
public class SysIngredient extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 食材名称
     */
    @Excel(name = "食材名称")
    private String name;

    /**
     * 食材类别
     */
    @Excel(name = "食材类别")
    private String type;

    /**
     * 推荐分量估算
     */
    @Excel(name = "推荐分量估算")
    private Long recEstimation;

    /**
     * 推荐分量估算单位id
     */
    @Excel(name = "推荐分量估算单位id")
    private Long recEstUnit;

    /**
     * 推荐分量
     */
    @Excel(name = "推荐分量")
    private Long recPortion;

    /**
     * 蛋白质比例
     */
    @Excel(name = "蛋白质比例")
    private BigDecimal proteinRatio;

    /**
     * 脂肪比例
     */
    @Excel(name = "脂肪比例")
    private BigDecimal fatRatio;

    /**
     * 碳水比例
     */
    @Excel(name = "碳水比例")
    private BigDecimal carbonRatio;

    /**
     * 地域
     */
    @Excel(name = "地域")
    private String area;

    /**
     * 忌口
     */
    @Excel(name = "忌口人群")
    private String notRec;

    /**
     * 推荐
     */
    @Excel(name = "推荐人群")
    private String rec;

    private Long[] recIds;

    private Long[] notRecIds;

    public Long[] getRecIds() {
        return recIds;
    }

    public Long[] getNotRecIds() {
        return notRecIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRecEstimation() {
        return recEstimation;
    }

    public void setRecEstimation(Long recEstimation) {
        this.recEstimation = recEstimation;
    }

    public Long getRecEstUnit() {
        return recEstUnit;
    }

    public void setRecEstUnit(Long recEstUnit) {
        this.recEstUnit = recEstUnit;
    }

    public Long getRecPortion() {
        return recPortion;
    }

    public void setRecPortion(Long recPortion) {
        this.recPortion = recPortion;
    }

    public BigDecimal getProteinRatio() {
        return proteinRatio;
    }

    public void setProteinRatio(BigDecimal proteinRatio) {
        this.proteinRatio = proteinRatio;
    }

    public BigDecimal getFatRatio() {
        return fatRatio;
    }

    public void setFatRatio(BigDecimal fatRatio) {
        this.fatRatio = fatRatio;
    }

    public BigDecimal getCarbonRatio() {
        return carbonRatio;
    }

    public void setCarbonRatio(BigDecimal carbonRatio) {
        this.carbonRatio = carbonRatio;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNotRec() {
        return notRec;
    }

    public void setNotRec(String notRec) {
        this.notRec = notRec;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("type", getType())
                .append("recEstimation", getRecEstimation())
                .append("recEstUnit", getRecEstUnit())
                .append("recPortion", getRecPortion())
                .append("proteinRatio", getProteinRatio())
                .append("fatRatio", getFatRatio())
                .append("carbonRatio", getCarbonRatio())
                .append("remark", getRemark())
                .append("area", getArea())
                .append("notRec", getNotRec())
                .append("recommend", getRec())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}