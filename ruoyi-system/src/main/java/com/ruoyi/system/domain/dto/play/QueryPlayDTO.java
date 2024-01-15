package com.ruoyi.system.domain.dto.play;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/13/013 16:45
 * @Description :
 */
@ApiModel("查询剧本任务DTO")
@Data
public class QueryPlayDTO extends PageBaseDTO {

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("剧本状态：1-调度中 2-炒群中 3-已暂停 4-已取消 5-已完成")
    private Integer state;

    @ApiModelProperty("查询类型 1-任务名称 2-任务ID")
    private Integer queryType;

    @ApiModelProperty("查询值")
    private String queryValue;

}
