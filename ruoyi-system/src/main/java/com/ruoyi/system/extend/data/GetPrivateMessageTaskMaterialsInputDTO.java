package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/19/019 15:22
 * @Description :
 */
@Data
public class GetPrivateMessageTaskMaterialsInputDTO extends UtTouchQueryPage {
    @ApiModelProperty("任务ID")
    private String taskId;
}
