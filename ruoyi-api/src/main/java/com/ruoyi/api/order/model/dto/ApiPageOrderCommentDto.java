package com.ruoyi.api.order.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/12 23:41
 */
@Data
@ApiModel("api-订单评论查询表单")
@Accessors(chain = true)
public class ApiPageOrderCommentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店员标识")
    private Long staffId;
}
