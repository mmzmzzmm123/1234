package com.ruoyi.system.domain.vo.play;

import com.ruoyi.common.core.domain.dto.play.PlayDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PlayVO extends PlayDTO {
    @ApiModelProperty(value = "剧本最终状态时间(取消或完成)")
    private Date endDate;

    @ApiModelProperty(value = "商家id")
    private String merchantId;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

}
