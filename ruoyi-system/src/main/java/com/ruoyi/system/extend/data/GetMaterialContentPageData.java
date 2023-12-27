package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jing.Zhang
 */
@Data
public class GetMaterialContentPageData extends UtTouchQueryPage {

    @ApiModelProperty("素材分组Id")
    private String materialGroupId;

    @ApiModelProperty("素材类型")
    private Integer materialType;

    @ApiModelProperty("素材名称")
    private String keyword;

    @ApiModelProperty(hidden = true)
    private String userCode;

}
