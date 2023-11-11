package com.ruoyi.api.order.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/11 23:12
 */
@ApiModel("api-订单完成表单")
@Data
@Accessors(chain = true)
public class ApiOrderFinishDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty("订单评论内容")
    private String comment;
}
