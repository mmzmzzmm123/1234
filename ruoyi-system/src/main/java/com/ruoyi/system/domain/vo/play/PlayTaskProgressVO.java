package com.ruoyi.system.domain.vo.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/15/015 9:10
 * @Description :
 */
@Data
@ApiModel("任务进度VO")
public class PlayTaskProgressVO {

    @ApiModelProperty("剧本ID")
    private String playId;

    @ApiModelProperty("总进度数量")
    private Integer totalNum;

    @ApiModelProperty("当前进度数量")
    private Integer currentNum;

    @ApiModelProperty("发送成功数量")
    private Integer sendSuccessNum;

    @ApiModelProperty("发送失败数量")
    private Integer sendFailNum;

    @ApiModelProperty("异常群数")
    private Integer groupFailNum;
}
