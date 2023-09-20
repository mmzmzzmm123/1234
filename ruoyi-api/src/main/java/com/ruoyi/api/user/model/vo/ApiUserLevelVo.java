package com.ruoyi.api.user.model.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LAM
 * @date 2023/9/19 21:52
 */
@ApiModel("api-用户等级")
@Data
@Accessors(chain = true)
public class ApiUserLevelVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("等级标识")
    private Long levelConfigId;

    @ApiModelProperty("当前积分")
    private BigDecimal currentPoints;

    @ApiModelProperty("当前等级")
    private Long currentLevel;

    @ApiModelProperty("总积分")
    private BigDecimal totalPoints;
}
