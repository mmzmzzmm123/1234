package com.ruoyi.api.user.model.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LAM
 * @date 2023/9/19 23:12
 */
@Data
@Accessors(chain = true)
public class ApiUserLevelConfigVo implements Serializable {

    private final static long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("阈值")
    private BigDecimal threshold;

    @ApiModelProperty("等级")
    private Long level;

    @ApiModelProperty("充值折扣")
    private BigDecimal rechargeDiscount;

    @ApiModelProperty("等级图标")
    private String icoUrl;

    @ApiModelProperty("头像框")
    private String avatarUrl;
}
