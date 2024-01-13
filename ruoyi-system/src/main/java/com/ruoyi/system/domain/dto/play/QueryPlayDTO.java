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
public class QueryPlayDTO  extends PageBaseDTO {

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;



}
