package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("SetNameInfoDTO")
@Data
public class SetNameInfoDTO {

    @ApiModelProperty(value = "号编号")
    private List<String> robotSerialNos;
    @ApiModelProperty(value = "名称数据")
    private List<Info> infos;
    @Data
    public static class Info{
        private String firstName;
        private String lastName;
        private String briefIntro;
    }


}
