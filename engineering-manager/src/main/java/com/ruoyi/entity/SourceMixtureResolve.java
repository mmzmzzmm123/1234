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
 * 原数据 --- 混合料分解
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Data
@TableName("source_mixture_resolve")
@ApiModel(value = "SourceMixtureResolve对象", description = "原数据 --- 混合料分解")
public class SourceMixtureResolve implements Serializable {

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

    @ApiModelProperty("0号台账id")
    private Integer zeroBillId;

    @ApiModelProperty("子目名称---混合料名称")
    private String mixtureName;

    @ApiModelProperty("使用部位")
    private String partUsed;

    @ApiModelProperty("混合料数量")
    private BigDecimal sumQuantity;

    @ApiModelProperty("配合比")
    private BigDecimal proportion;

    @ApiModelProperty("材料名称")
    private String materialsName;

    @ApiModelProperty("单位名称")
    private String unit;

    @ApiModelProperty("理论数量")
    private BigDecimal theoryCount;

    @ApiModelProperty("规格型号")
    private String specificationsModel;

    @ApiModelProperty("数量（混合料数量*理论数量）")
    private BigDecimal quantity;

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
