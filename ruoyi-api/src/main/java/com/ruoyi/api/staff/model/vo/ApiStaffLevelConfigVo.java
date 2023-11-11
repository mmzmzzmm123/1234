package com.ruoyi.api.staff.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LAM
 * @date 2023/9/20 16:04
 */
@ApiModel("api-员工等级配置对象")
@Data
@Accessors(chain = true)
public class ApiStaffLevelConfigVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("阈值")
    private BigDecimal threshold;

    @ApiModelProperty("等级")
    private Long level;

    @ApiModelProperty("首单提成")
    private BigDecimal firstOrderRatio;

    @ApiModelProperty("续单提成")
    private BigDecimal orderRatio;

    @ApiModelProperty("打赏提成")
    private BigDecimal rewardRatio;
}
