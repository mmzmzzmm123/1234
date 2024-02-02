package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgGetSaleActiveChatroomInputDTO {

    @ApiModelProperty("需求量")
    private Integer requestCount;
    @ApiModelProperty("建群天数")
    private Integer minCreateDurDay;
    @ApiModelProperty("活跃天数")
    private Integer minActiveDurDay;
    @ApiModelProperty("群人数")
    private Integer minMemberCount;
    @ApiModelProperty("国家（示例\"+1\",不传即表示不选择国家）")
    private String countryCode;

}
