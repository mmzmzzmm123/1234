package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/21/021 11:30
 * @Description :
 */
@Data
public class SopContent {
    @ApiModelProperty("延迟时间")
    private Integer delaySeconds;

    @ApiModelProperty("话术分组id")
    private List<String> materialGroupIds;

    @ApiModelProperty("话术类型：文本2001，图片2002，postbot2035")
    private List<Integer> materialTypes;

    @ApiModelProperty("是否开启小尾巴：0未开启、1已开启")
    private Integer isOpenTail;

    @ApiModelProperty("小尾巴内容")
    private String tailContent;

    @ApiModelProperty("占位符信息")
    private List<TailInfo> tailContentList;

}
