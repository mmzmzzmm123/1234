package com.ruoyi.system.domain.vo.play;

import com.ruoyi.common.core.domain.dto.play.PlayDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlayVO extends PlayDTO {
    @ApiModelProperty(value = "剧本状态：1-调度中 2-炒群中 3-已暂停 4-已取消 5-已完成")
    private Integer state;
}
