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
 * 服务信息对象 bus_service_info
 *
 * @author Lam
 * @date 2023-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-服务信息对象")
public class ServiceInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 简称 */
    @Excel(name = "简称")
    @ApiModelProperty("简称")
    private String shortName;

    /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long sortNum;

    /** 服务子项目信息 */
    private List<ServiceItem> serviceItemList;


}
