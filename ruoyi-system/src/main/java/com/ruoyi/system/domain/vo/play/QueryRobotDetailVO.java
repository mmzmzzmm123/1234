package com.ruoyi.system.domain.vo.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/17/017 15:44
 * @Description :
 */
@ApiModel("查询任务账号明细VO")
@Data
public class QueryRobotDetailVO {
    @ApiModelProperty("国家代码")
    private String countryCode;

    @ApiModelProperty("国家")
    private String countryName;

    @ApiModelProperty("账号总数")
    private Integer robotTotalNUm;

    @ApiModelProperty("正常账号数")
    private Integer robotSuccessNUm;

    @ApiModelProperty("双向个数")
    private Integer bidirectionalNum;

    @ApiModelProperty("封号数")
    private Integer closureNum;

    @ApiModelProperty("离线个数")
    private Integer offlineNum;

    @ApiModelProperty("发言总数")
    private Integer sendTotalNUm;

    @ApiModelProperty("发言成功")
    private Integer sendSuccessNUm;

    @ApiModelProperty("发言失败")
    private Integer sendFailNUm;

    @ApiModelProperty("发言成功率")
    private BigDecimal sendSuccessRate;

}
