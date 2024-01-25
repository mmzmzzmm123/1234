package com.ruoyi.system.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/25/025 10:31
 */
@Data
public class ScreeningTaskExportDTO{

    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "批次id")
    private String batchId;


    @ApiModelProperty(value = "目标状态 0-待检测 1-已检查")
    private Integer result;

}
