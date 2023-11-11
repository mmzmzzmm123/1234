package com.ruoyi.api.order.model.dto;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/11 14:30
 */
@Data
@ApiModel("api-订单信息表单")
@Accessors(chain = true)
public class ApiPageOrderInfoDto extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单状态")
    private String orderState;

    @ApiModelProperty("端显示类型，0用户端，1店员端，2接单大厅")
    @NotBlank(message = "重要参数不能为空")
    private String formUser;
}
