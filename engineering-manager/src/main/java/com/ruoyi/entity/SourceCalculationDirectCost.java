package com.ruoyi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 成本测算导入（直接费）
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Data
@TableName("source_calculation_direct_cost")
@ApiModel(value = "SourceCalculationDirectCost对象", description = "成本测算导入（直接费）")
public class SourceCalculationDirectCost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("公司编号")
    private String companyNo;

    @ApiModelProperty("项目no")
    private Integer projectNo;

    @ApiModelProperty("章节名称 100-800章")
    private String chapterName;

    @ApiModelProperty("唯一编号")
    private Long uniqueNo;

    @ApiModelProperty("子目号")
    private String itemNum;

    @ApiModelProperty("子目名称")
    private String itemName;

    @ApiModelProperty("是否混合材料（0：否,1：是）")
    private Boolean mixture;

    @ApiModelProperty("单位名称")
    private String unit;

    @ApiModelProperty("0号台账数量")
    private BigDecimal inventory;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("合价")
    private BigDecimal combinedPrice;

    @ApiModelProperty("不含税单价	")
    private BigDecimal noTaxUnitPrice;

    @ApiModelProperty("不含税合价")
    private BigDecimal noTaxCombinedPrice;

    @ApiModelProperty("成本单价合计")
    private BigDecimal costUnitPriceTotal;

    @ApiModelProperty("成本总额合计")
    private BigDecimal costAllPriceTotal;

    @ApiModelProperty("人工（劳务：人机+辅助材料+机械）")
    private BigDecimal laborUnitPrice;

    @ApiModelProperty("钢材单价")
    private BigDecimal steelUnitPrice;

    @ApiModelProperty("砼单价（或沥青稳定土混合料）")
    private BigDecimal concreteUnitPrice;

    @ApiModelProperty("土工材料或橡胶制品--单价	")
    private BigDecimal soilOrRubberUnitPrice;

    @ApiModelProperty("其他材料单价")
    private BigDecimal otherMaterialsUnitPrice;

    @ApiModelProperty("周转材料单价")
    private BigDecimal revolvingMaterialUnitPrice;

    @ApiModelProperty("机械费单价")
    private BigDecimal machineryUnitPrice;

    @ApiModelProperty("直接费单价和")
    private BigDecimal costDirectUnitPriceSum;

    @ApiModelProperty("其他直接费单价  (计算比例分摊至200～800章）")
    private BigDecimal costOtherDirectUnitPrice;

    @ApiModelProperty("间接费单价")
    private BigDecimal costMachineryUnitPrice;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否删除（0：否,1：是）")
    private Boolean deleted;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改人")
    private String updateUser;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;


}
