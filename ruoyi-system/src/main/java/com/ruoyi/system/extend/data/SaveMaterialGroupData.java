package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
@ApiModel("保存素材分组")
public class SaveMaterialGroupData {

    @ApiModelProperty("素材分组Id")
    private String materialGroupId;

    @ApiModelProperty("素材分组名称")
    private String name;

    @ApiModelProperty("商家ID")
    private String userCode;

    private List<String> robotGroups;

    private String extendKey;

}
