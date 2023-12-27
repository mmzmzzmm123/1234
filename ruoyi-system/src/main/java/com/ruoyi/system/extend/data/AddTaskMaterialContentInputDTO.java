package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/19/019 15:09
 * @Description :
 */
@Data
public class AddTaskMaterialContentInputDTO {

    @ApiModelProperty(hidden = true)
    private String userCode;

    @ApiModelProperty("任务ID")
    private String taskId;

    @ApiModelProperty("素材类型")
    private Integer materialType;

    @ApiModelProperty("素材内容")
    private List<AddTaskMaterialContentData> materialContents;


}
