package com.ruoyi.service.domain;

import java.util.List;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服务子项目对象 bus_service_item
 *
 * @author Lam
 * @date 2023-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-服务子项目对象")
public class ServiceItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 服务标识 */
    @Excel(name = "服务标识")
    @ApiModelProperty("服务标识")
    private Long serviceId;

    @ApiModelProperty("服务信息")
    private ServiceInfo serviceInfo;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 买断项 */
    @Excel(name = "买断项")
    @ApiModelProperty("买断项")
    private String ifBuyoutItem;

    /** 订单过期时间单位 */
    @Excel(name = "订单过期时间单位")
    @ApiModelProperty("订单过期时间单位")
    private String orderExpireTimeUnit;

    /** 订单服务时长 */
    @Excel(name = "订单服务时长")
    @ApiModelProperty("订单服务时长")
    private Long orderServiceDuration;

    /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long sortNum;

    /** 服务子项目价格信息 */
    private List<ServiceItemPrice> serviceItemPriceList;


}
