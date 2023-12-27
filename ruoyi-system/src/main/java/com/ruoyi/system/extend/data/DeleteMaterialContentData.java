package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class DeleteMaterialContentData {

    @ApiModelProperty(hidden = true)
    private String userCode;


    @ApiModelProperty("素材内容Id")
    private List<String> materialContentIds;

    @ApiModelProperty("状态：0可用、1禁用")
    private Integer status;
}
