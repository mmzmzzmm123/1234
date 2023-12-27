package com.ruoyi.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_product")
public class Product {
    /**
     * 商品id
     */
    @TableId
    private Long productId;

    /**
     * {@link com.ruoyi.common.enums.AppType}
     * 平台类型
     */
    private Integer appType;

    /**
     * {@link com.ruoyi.common.enums.ProductCategoryType}
     * 商品类型
     */
    private Integer categoryId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 列表图
     */
    private String albumPics;

    /**
     * 海报图
     */
    private String pic;

    /**
     * 商品状态:1-未上架 2-上架
     */
    private Integer status;

    /**
     * 是否显示:0否 1是
     */
    private Integer isShow;

    /**
     * 产品描述信息
     */
    private String intro;

    /**
     * 支撑量
     */
    private Integer stock;

    /**
     * 规格:1统一规格 2多规格
     */
    private Integer specsType;

    /**
     * 默认展示规格
     */
    private String skuAttr;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 创建人
     */
    private String operatorUser;

    /**
     * 创建人id
     */
    private Long operatorUserId;

    /**
     * 是否删除:0否 1是
     */
    private Integer isDel;
}
