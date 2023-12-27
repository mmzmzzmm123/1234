package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author Jing.Zhang
 */
@Data
@ApiModel("获取素材分组")
public class GetMaterialGroupResult {

    @ApiModelProperty("素材分组Id")
    private String materialGroupId;

    @ApiModelProperty("分组名称")
    private String name;

    @ApiModelProperty("素材总数")
    private Integer materialCount;

    @ApiModelProperty("是否内部素材组")
    private Boolean isInnerMaterilGroup;

    @ApiModelProperty("素材类型数量")
    private Map<String, Integer> materilTypeCounts;

    @ApiModelProperty("商家Id")
    private String extendKey;

}
