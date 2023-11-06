package com.ruoyi.api.distribution.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.api.user.model.vo.ApiUserVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author LAM
 * @date 2023/11/6 12:13
 */
@ApiModel("api-分销佣金记录")
@Data
@Accessors(chain = true)
public class ApiDistributionCommissionRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分销佣金记录类型")
    private String commissionRecordType;

    @ApiModelProperty("订单标识")
    private Long orderId;

    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("分销用户")
    private Long distributionUserId;

    @ApiModelProperty("分销用户信息")
    private ApiUserVo distributionUserInfo;

    @ApiModelProperty("分销层级")
    private Long distributionLevel;

    @ApiModelProperty("分销比例")
    private BigDecimal distributionRate;

    @ApiModelProperty("分销佣金")
    private BigDecimal distributionCommission;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;
}
