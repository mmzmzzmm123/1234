package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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

    @Size(min = 1, max = 64, message = "服务名称长度为1-64字")
    @ApiModelProperty("服务名称")
    private String name;

    @Positive(message = "服务id必须是正数")
    @Size(min = 0, max = 20, message="服务id不合规则")
    @ApiModelProperty("服务id")
    private String productId;
}
