package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jing.Zhang
 */
@Data
public class DeleteMaterialGroupData {

    @ApiModelProperty(value = "商家ID", hidden = true)
    private String userCode;

    @ApiModelProperty("素材组Id")
    private String materialGroupId;

}
