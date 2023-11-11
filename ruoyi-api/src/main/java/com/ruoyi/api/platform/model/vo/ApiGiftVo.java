package com.ruoyi.api.platform.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LAM
 * @date 2023/9/26 17:38
 */
@ApiModel("api-礼物信息")
@Data
@Accessors(chain = true)
public class ApiGiftVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    private Long id;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("视频地址")
    private String videoUrl;

    @ApiModelProperty("视频秒数")
    private Long videoSeconds;

    @ApiModelProperty("价格")
    private BigDecimal price;
}
