package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ThirdTgSelectGroupByMerchantDTO {

    @ApiModelProperty(
            value = "页码",
            example = "1"
    )
    private Integer page = 1;
    @ApiModelProperty(
            value = "每页显示记录数",
            example = "10"
    )
    private Integer limit = 10;
    @ApiModelProperty("商家id")
    private String merchantId;
    private Integer merchantType;
    @ApiModelProperty("群编号")
    private List<String> chatroomSerialNos;
    @ApiModelProperty("群名称")
    private String title;
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
}
