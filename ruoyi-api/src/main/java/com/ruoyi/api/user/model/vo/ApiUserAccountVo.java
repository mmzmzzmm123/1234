package com.ruoyi.api.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/9/21 20:01
 */
@ApiModel("api-用户账号信息")
@Data
@Accessors(chain = true)
public class ApiUserAccountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("账号标识")
    private Long id;

    @ApiModelProperty("用户标识")
    private Long userId;

    @ApiModelProperty("账号服务商")
    private String accountServiceProvider;

    @ApiModelProperty("账号")
    private String num;

    @ApiModelProperty("是否默认")
    private String ifDefault;
}
