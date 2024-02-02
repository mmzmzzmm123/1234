package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgSqlTaskSubmitInputDTO {

    @ApiModelProperty("来源：kfpt-doris-bd，kfpt-doris-ed")
    private String dbSource;
    @ApiModelProperty("select * from tg_chatroom_info limit 10")
    private String sql;

    @ApiModelProperty("透传字段")
    private String extend;
}
