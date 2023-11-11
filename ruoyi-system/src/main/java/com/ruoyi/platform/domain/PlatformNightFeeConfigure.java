package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 夜间费用配置对象 bus_platform_night_fee_configure
 *
 * @author Lam
 * @date 2023-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-夜间费用配置对象")
public class PlatformNightFeeConfigure extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 是否开启 */
    @Excel(name = "是否开启")
    @ApiModelProperty("是否开启")
    private String ifOpen;

    /** 开始时间 */
    @Excel(name = "开始时间")
    @ApiModelProperty("开始时间")
    private String startTime;

    /** 结束时间 */
    @Excel(name = "结束时间")
    @ApiModelProperty("结束时间")
    private String endTime;

    /** 加收比例 */
    @Excel(name = "加收比例")
    @ApiModelProperty("加收比例")
    private BigDecimal additionalChargeRatio;


}
