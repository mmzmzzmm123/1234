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
 * 成本测算---材料费
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Data
@TableName("source_calculation_material_cost")
@ApiModel(value = "SourceCalculationMaterialCost对象", description = "成本测算---材料费")
public class SourceCalculationMaterialCost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("公司编号")
    private String companyNo;

    @ApiModelProperty("项目no")
    private Integer projectNo;

    @ApiModelProperty("章节名称 100-800章")
    private String chapterName;

    @ApiModelProperty("材料名称")
    private String materialsName;

    @ApiModelProperty("规格型号")
    private String specificationsModels;

    @ApiModelProperty("编号")
    private Integer number;

    @ApiModelProperty("产地")
    private String yieldly;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("出厂不含税单价")
    private BigDecimal exwUnitPrice;

    @ApiModelProperty("运费")
    private BigDecimal freightPrice;

    @ApiModelProperty("损耗百分比%")
    private BigDecimal attritionRate;

    @ApiModelProperty("到厂不含税单价")
    private BigDecimal arriveFactoryExwUnitPrice;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改人")
    private String updateUser;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除（0：否,1：是）")
    private Boolean deleted;

}
