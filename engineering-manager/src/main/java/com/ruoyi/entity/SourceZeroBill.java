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
 * 原数据---0号台账
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Data
@TableName("source_zero_bill")
@ApiModel(value = "SourceZeroBill对象", description = "原数据---0号台账")
public class SourceZeroBill implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("公司编号")
    private Integer companyNo;

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

    @ApiModelProperty("原合同清单")
    private BigDecimal inventory;

    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("合价")
    private BigDecimal combinedPrice;

    @ApiModelProperty("0号台账数量")
    private BigDecimal zeroCount;

    @ApiModelProperty("0号台账合价")
    private BigDecimal zeroUnitPrice;

    @ApiModelProperty("0号台账合价")
    private BigDecimal zeroCombinedPrice;

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
