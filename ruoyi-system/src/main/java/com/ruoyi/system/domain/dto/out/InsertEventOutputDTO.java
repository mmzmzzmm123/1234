package com.ruoyi.system.domain.dto.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/16/016 20:31
 * @Description :
 */
@Data
public class InsertEventOutputDTO {

    private String activityGuid;
    @ApiModelProperty("中转地址")
    private String activityLinkAddress;
    private String activityQrLinkAddress;
    private String activityCreateTime;
}
