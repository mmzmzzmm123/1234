package com.ruoyi.api.staff.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author LAM
 * @date 2023/10/18 19:21
 */
@Data
@Accessors(chain = true)
@ApiModel("api-员工钱包记录对象")
public class ApiStaffWalletRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    private Long id;

    @ApiModelProperty("订单标识")
    private Long orderId;

    @ApiModelProperty("记录类型")
    private String staffWalletRecordType;

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;
}
