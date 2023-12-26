package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductQueryParamDTO extends PageBaseDTO {
    @ApiModelProperty("开始日期")
    private String startDate;

    @ApiModelProperty("结束日期")
    private String endDate;

    @ApiModelProperty("服务类型")
    private Integer categoryId;

    @ApiModelProperty("上架状态")
    private Integer status;

    @ApiModelProperty("服务名称")
    private String name;

    @ApiModelProperty("服务id")
    private Long productId;
}
