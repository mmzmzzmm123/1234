package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/18/018 14:22
 * @Description :
 */
@Data
@ApiModel("占位符信息")
public class TailInfo {
    @ApiModelProperty("1=人设模板用户名 2=自定义内容 3=中转号 4=中转域名")
    private Integer sendType;

    private List<String> tails;

    private Integer groupId = 0;

//    "sendType": 1,
//            "tails": [
//            "string"
//            ],
//            "groupId": 0

}
