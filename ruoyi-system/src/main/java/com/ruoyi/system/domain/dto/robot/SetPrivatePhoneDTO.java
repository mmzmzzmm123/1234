package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("SetPrivatePhoneDTO")
@Data
public class SetPrivatePhoneDTO {
    @ApiModelProperty(value = "机器人编号")
    private List<String> robotSerialNos;
    @ApiModelProperty(value = "谁可以看见我的手机号,1-不允许任何人,2-我的联系人,3-所有人")
    private Integer type;
    @ApiModelProperty(value = "谁可以通过我的手机号找到我,10-我的联系人,11-所有人")
    private Integer accountPrivacy;
}
