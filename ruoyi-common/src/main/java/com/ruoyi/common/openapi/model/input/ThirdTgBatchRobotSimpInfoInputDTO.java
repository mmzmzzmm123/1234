package com.ruoyi.common.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ThirdTgBatchRobotSimpInfoInputDTO {


    @ApiModelProperty(value = "机器人编号集合", required = true)
    private List<String> robotSerialNos;
    @ApiModelProperty(value = "产品类型(目前仅支持telegram) 40-LINE 50-Viber 60-telegram 70-whatsup 不传默认60")
    private Integer productType = 60;

}
