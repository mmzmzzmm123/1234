package com.ruoyi.system.domain.dto.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/16/016 14:16
 * @Description :
 */
@Data
@ApiModel("设置炒群速度")
public class SetSpeedDTO {

    @ApiModelProperty("剧本ID")
    @NotBlank(message = "剧本ID不能为空")
    private String playId;

    @NotNull(message = "设置速度不能为空")
    @DecimalMin(value = "-100",message = "速度必须大于-100")
    @DecimalMax(value = "0.99",message = "速度必须小于0.99")
    private BigDecimal speed;


}
