package com.ruoyi.api.staff.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LAM
 * @date 2023/9/25 18:50
 */
@Data
@ApiModel("api-员工相册")
@Accessors(chain = true)
public class ApiStaffPhotoVo implements Serializable {

    @ApiModelProperty("标识")
    private Long id;

    @ApiModelProperty("图片")
    private String imgUrl;

    @ApiModelProperty("是否解锁")
    private String ifUnlock;

    @ApiModelProperty("解锁金额")
    private BigDecimal unlockAmount;

    @ApiModelProperty("状态")
    private String state;

    @ApiModelProperty("不通过原因")
    private String noPassReason;
}
