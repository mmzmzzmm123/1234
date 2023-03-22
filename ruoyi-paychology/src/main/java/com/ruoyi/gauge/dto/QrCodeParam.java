package com.ruoyi.gauge.dto;


import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;




@Data
@ApiModel("二维码生成接口入参")
public class QrCodeParam extends BaseEntity {
    @ApiModelProperty("二维码生成接口入参")
    private String codeParam;
}
