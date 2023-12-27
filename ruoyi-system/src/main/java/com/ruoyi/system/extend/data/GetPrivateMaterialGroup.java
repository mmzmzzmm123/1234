package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetPrivateMaterialGroup {

    @ApiModelProperty("素材分组编号")
    private String materialGroupId;
    @ApiModelProperty("素材分组名称")
    private String name;

}
