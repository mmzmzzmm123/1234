package com.ruoyi.system.extend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jing.Zhang
 */
@Data
@ApiModel("接口信息")
public class ApiInterfaceVO {

    @ApiModelProperty("接口名称")
    private String interfaceName;

    @ApiModelProperty("接口注释")
    private String interfaceDesc;

    public ApiInterfaceVO() {
    }

    public ApiInterfaceVO(UtTouchApiEnum api) {
        this.interfaceName = api.name();
        this.interfaceDesc = api.getApiDesc();
    }
}
