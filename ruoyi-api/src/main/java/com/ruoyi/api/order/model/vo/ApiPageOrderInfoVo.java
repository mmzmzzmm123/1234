package com.ruoyi.api.order.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/11 14:00
 */
@ApiModel("api-订单分页实体")
@Data
@Accessors(chain = true)
public class ApiPageOrderInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单信息集合")
    private List<ApiOrderInfoVo> data;

    @ApiModelProperty("总数")
    private long total;

}
