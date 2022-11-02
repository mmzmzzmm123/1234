package com.ruoyi.gauge.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心理测评对象 psy_gauge
 * 
 * @author ruoyi
 * @date 2022-08-30
 */
public class PsyGauge extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 测评标题 */
    @Excel(name = "测评标题")
    private String title;

    /** 子标题 */
    @Excel(name = "子标题")
    private String subtitle;

    /** 头部图片 */
    @Excel(name = "头部图片")
    private String headPicture;

    /** 测评描述 */
    @Excel(name = "测评描述")
    private String introduce;

    /** 测评分类 */
    @Excel(name = "测评分类")
    private Integer gaugeClass;

    /** 测评题数 */
    @Excel(name = "测评题数")
    private Long gaugeNum;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;



    /** 测评次数 */
    @Excel(name = "测评次数")
    private Long num;

    /** 测评类型 */
    @Excel(name = "测评类型")
    private Integer type;

    @Excel(name = "测评简介")
    private String gaugeDes;

    @Excel(name = "列表展示图片")
    private String listShowPicture;

    public String getListShowPicture() {
        return listShowPicture;
    }

    public void setListShowPicture(String listShowPicture) {
        this.listShowPicture = listShowPicture;
    }

    public String getGaugeDes() {
        return gaugeDes;
    }

    public void setGaugeDes(String gaugeDes) {
        this.gaugeDes = gaugeDes;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setSubtitle(String subtitle) 
    {
        this.subtitle = subtitle;
    }

    public String getSubtitle() 
    {
        return subtitle;
    }
    public void setHeadPicture(String headPicture) 
    {
        this.headPicture = headPicture;
    }

    public String getHeadPicture() 
    {
        return headPicture;
    }

    public void setGaugeClass(Integer gaugeClass) 
    {
        this.gaugeClass = gaugeClass;
    }

    public Integer getGaugeClass() 
    {
        return gaugeClass;
    }
    public void setGaugeNum(Long gaugeNum) 
    {
        this.gaugeNum = gaugeNum;
    }

    public Long getGaugeNum() 
    {
        return gaugeNum;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public void setNum(Long num) 
    {
        this.num = num;
    }

    public Long getNum() 
    {
        return num;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("subtitle", getSubtitle())
            .append("headPicture", getHeadPicture())
            .append("introduce", getIntroduce())
            .append("gaugeClass", getGaugeClass())
            .append("gaugeNum", getGaugeNum())
            .append("price", getPrice())
            .append("num", getNum())
            .append("type", getType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
