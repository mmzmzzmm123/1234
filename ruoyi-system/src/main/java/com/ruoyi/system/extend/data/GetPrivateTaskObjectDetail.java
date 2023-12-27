package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetPrivateTaskObjectDetail {
    @ApiModelProperty("人群包名称")
    private String contactPackageName;
    @ApiModelProperty("人群包对象数")
    private Integer contactPackageObjectCount;
    @ApiModelProperty("人群包关联可用数")
    private Integer contactObjectSuccessCount;

}
