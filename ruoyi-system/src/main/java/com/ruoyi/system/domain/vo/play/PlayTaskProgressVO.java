package com.ruoyi.system.domain.vo.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

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

    @ApiModelProperty("群出库进度")
    private BigDecimal groupProgress;
    @ApiModelProperty("群出库总数量")
    private Integer groupTotalNum;
    @ApiModelProperty("群出库当前数量")
    private Integer groupCurrentNum;

    @ApiModelProperty("人设包装进度")
    private BigDecimal packProgress;
    @ApiModelProperty("人设包装总数量")
    private Integer packTotalNum;
    @ApiModelProperty("人设包装当前数量")
    private Integer packCurrentNum;

    @ApiModelProperty("拉人入群进度")
    private BigDecimal joinGroupProgress;
    @ApiModelProperty("拉人入群总数量")
    private Integer joinGroupTotalNum;
    @ApiModelProperty("拉人入群当前数量")
    private Integer joinGroupCurrentNum;


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
