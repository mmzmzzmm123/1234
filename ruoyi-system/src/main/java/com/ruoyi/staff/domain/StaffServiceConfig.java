package com.ruoyi.staff.domain;

import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工服务配置对象 bus_staff_service_config
 *
 * @author Lam
 * @date 2023-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-员工服务配置对象")
public class StaffServiceConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 员工标识 */
    @Excel(name = "员工标识")
    @ApiModelProperty("员工标识")
    private Long staffId;

    /** 服务标识 */
    @Excel(name = "服务标识")
    @ApiModelProperty("服务标识")
    private Long serviceId;


}
