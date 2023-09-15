package com.ruoyi.service.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.staff.domain.StaffLevelConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服务子项目价格对象 bus_service_item_price
 *
 * @author Lam
 * @date 2023-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-服务子项目价格对象 bus_service_item_price")
public class ServiceItemPrice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 服务子项目标识 */
    @Excel(name = "服务子项目标识")
    @ApiModelProperty("服务子项目标识")
    private Long serviceItemId;

    @ApiModelProperty("服务子项目")
    private Long serviceItem;

    /** 等级标识 */
    @Excel(name = "等级标识")
    @ApiModelProperty("等级标识")
    private Long staffLevelConfigId;

    @ApiModelProperty("员工等级数据")
    private StaffLevelConfig staffLevelConfig;

    /** 价格 */
    @Excel(name = "价格")
    @ApiModelProperty("价格")
    private BigDecimal price;

}
