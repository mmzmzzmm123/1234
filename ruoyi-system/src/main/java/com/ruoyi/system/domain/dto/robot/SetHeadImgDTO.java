package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("SetHeadImgDTO")
@Data
public class SetHeadImgDTO {
    @ApiModelProperty(value = "机器人编号")
    private List<String> robotSerialNos;
    @ApiModelProperty(value = "头像地址")
    private List<String> headImgUrls;
}
