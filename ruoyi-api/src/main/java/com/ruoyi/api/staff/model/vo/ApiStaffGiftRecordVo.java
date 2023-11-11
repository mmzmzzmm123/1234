package com.ruoyi.api.staff.model.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/9 23:00
 */
@ApiModel("api-店员礼物总记录")
@Data
@Accessors(chain = true)
public class ApiStaffGiftRecordVo implements Serializable {

    private final static long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    private Long id;

    @ApiModelProperty("礼物标识")
    private Long giftId;

    @ApiModelProperty("礼物数量")
    private Long giftNum;
}
