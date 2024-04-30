package com.ruoyi.app.product.vo;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.category.domain.TCategory;
import com.ruoyi.system.price.domain.TPrice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 移动产品vo
 *
 * @author Guard Baby
 * @date 2024/04/25
 */
@ApiModel(value = "MobileProductVO", description = "商品实体类VO")
public class MobileProductVO implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;

    /** 商品名称 */
    @ApiModelProperty("商品名称")
    @Excel(name = "商品名称")
    private String name;

    /** 商品图片 */
    @ApiModelProperty("商品图片，多张图用逗号分割")
    @Excel(name = "商品图片")
    private String image;

    /** 商品价格 */
    @ApiModelProperty("商品标准价格")
    @Excel(name = "商品价格")
    private BigDecimal price;

    /** 商品隐藏价格 */
    @ApiModelProperty("商品隐藏价格")
    @Excel(name = "商品隐藏价格")
    private String hidePrice;

    /** 商品回收价 */
    @ApiModelProperty("商品回收价")
    @Excel(name = "商品回收价")
    private BigDecimal recoveredPrice;

    /** 上架状态 */
    @ApiModelProperty("上架状态")
    @Excel(name = "上架状态")
    private Integer status;

    /** 商品描述 */
    @ApiModelProperty("商品描述")
    @Excel(name = "商品描述")
    private String description;

    @ApiModelProperty("价格涨幅")
    private BigDecimal subPrice;

    @ApiModelProperty("上次价格")
    private TPrice tPrice;

    @ApiModelProperty("商品分类")
    private List<TCategory> tCategoryList;

    public List<TCategory> gettCategoryList() {
        return tCategoryList;
    }

    public void settCategoryList(List<TCategory> tCategoryList) {
        this.tCategoryList = tCategoryList;
    }

    public BigDecimal getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(BigDecimal subPrice) {
        this.subPrice = subPrice;
    }

    public TPrice gettPrice() {
        return tPrice;
    }

    public void settPrice(TPrice tPrice) {
        this.tPrice = tPrice;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getHidePrice() {
        return hidePrice;
    }

    public void setHidePrice(String hidePrice) {
        this.hidePrice = hidePrice;
    }

    public BigDecimal getRecoveredPrice() {
        return recoveredPrice;
    }

    public void setRecoveredPrice(BigDecimal recoveredPrice) {
        this.recoveredPrice = recoveredPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
