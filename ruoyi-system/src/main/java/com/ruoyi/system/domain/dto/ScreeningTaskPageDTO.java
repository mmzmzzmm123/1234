package com.ruoyi.system.domain.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/25/025 10:31
 */
@Data
public class ScreeningTaskPageDTO extends PageBaseDTO {

    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "商家名")
    private String merchantName;

    @ApiModelProperty(value = "商家id")
    private String merchantId;


    @ApiModelProperty(value = "任务状态：  0-待检查 1-检查中 3-已暂停 4-已取消 5-已完成 ")
    private Integer taskState;

    @ApiModelProperty(value = "创建时间-开始")
    private String createTimeStart;


    @ApiModelProperty(value = "创建时间-结束")
    private String createTimeEnd;

}
