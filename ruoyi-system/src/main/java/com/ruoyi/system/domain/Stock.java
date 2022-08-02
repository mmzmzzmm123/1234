package com.ruoyi.system.domain;

import lombok.Data;

/**
 * @author wjp
 * @date 2022/8/2 10:51
 * @description
 */
@Data
public class Stock {

    /** 库存ID */
    private Long stockId;

    /** 仓库分类 1-试剂库；2-原料库；3-溶剂库；4-危险品库；5-方程式库；*/
    private Integer stockType;

    /** 名称 */
    private String name;

    /** 品牌 */
    private String brand;

    /** 参数 */
    private String param;

    /** 数量 */
    private String unit;

    /** 单位 */
    private Double quantity;
}
