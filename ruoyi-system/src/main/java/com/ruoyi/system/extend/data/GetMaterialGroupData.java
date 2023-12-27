package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jing.Zhang
 */
@Data
public class GetMaterialGroupData {

    @ApiModelProperty("素材组名称")
    private String name;

    @ApiModelProperty("素材组类型")
    private Integer type;

    @ApiModelProperty(hidden = true)
    private String userCode;

    @ApiModelProperty("商家ID")
    private String extendKey;
}
