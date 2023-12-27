package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class DeletePrivateMessageTaskData {

    @ApiModelProperty(hidden = true)
    private String userCode;

    @ApiModelProperty("任务Id")
    private List<String> taskIds;

}
