package com.ruoyi.system.openapi.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @className: TimBaseOutputDTO
 * @author: zzl
 * @date: 2022-05-17 16:32
 */
@Data
public class TgBaseOutputDTO<T> {

    /**
     * 操作序列号
     */
    @ApiModelProperty(value = "当前操作的流水号")
    private String optSerNo;
    /**
     * 服务名
     */
    @ApiModelProperty(value = "调用方服务名")
    private String serverName;

    /**
     * 标识该接口是否有回调
     */
    @ApiModelProperty(value = "该接口是否有回调")
    private boolean hasCallback = false;

    @ApiModelProperty(value = "返回当前服务器的时间")
    private String serviceTime;

    @ApiModelProperty(value = "数据")
    private T data;
}
