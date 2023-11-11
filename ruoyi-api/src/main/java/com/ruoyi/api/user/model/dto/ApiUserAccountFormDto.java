package com.ruoyi.api.user.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/9/21 20:09
 */
@ApiModel("api-用户账号表单")
@Data
@Accessors(chain = true)
public class ApiUserAccountFormDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("账号标识")
    @NotNull(message = "标识不能为空")
    private Long id;

    @ApiModelProperty("账号服务商")
    private String accountServiceProvider;

    @ApiModelProperty("账号")
    private String num;

    @ApiModelProperty("是否默认")
    private String ifDefault;
}
