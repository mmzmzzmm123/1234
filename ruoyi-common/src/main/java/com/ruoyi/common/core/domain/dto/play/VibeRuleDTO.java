package com.ruoyi.common.core.domain.dto.play;

import com.ruoyi.common.core.domain.dto.VibeRuleTargetParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class VibeRuleDTO {
    private Integer id;

    @ApiModelProperty(value = "单个水军每天入群限制")
    private Integer joinLimitByDay;

    @ApiModelProperty(value = "单个水军累计入群限制")
    private Integer joinLimitByTotal;

    @ApiModelProperty(value = "单个水军每日设为群管理员上限")
    private Integer setManageLimitByDay;

    @ApiModelProperty(value = "单个水军累计设为群管理员上限")
    private Integer setManageLimitByTotal;

    @ApiModelProperty(value = "单个水军带群数")
    private Integer groupNum;

    @ApiModelProperty(value = "单个群主号可设置管理员的上限")
    private Integer setManageLimit;

    @ApiModelProperty(value = "单个群累计发送失败次数上限")
    private Integer errLimit;

    @ApiModelProperty(value = "单个群累计双向数上限")
    private Integer bothwayLimit;

    @ApiModelProperty(value = "单个群累计封号数上限")
    private Integer bannedLimit;

    @ApiModelProperty(value = "按代理IP段离散: 1是 0否")
    private Integer periodByIp;

    @ApiModelProperty(value = "B段离散: 1是 0否")
    private Integer periodByB;

    @ApiModelProperty(value = "C段离散: 1是 0否")
    private Integer periodByC;

    @ApiModelProperty(value = "备用号 水军倍数")
    private Integer standbyNum;

    @ApiModelProperty(value = "同一个机器人不同群退群间隔 开始")
    private Integer robotIntervalStart;

    @ApiModelProperty(value = "同一个机器人不同群退群间隔 结束")
    private Integer robotIntervalEnd;

    @ApiModelProperty(value = "不同的机器人在相同的群间隔  开始")
    private Integer diffRobotIntervalStart;

    @ApiModelProperty(value = "不同的机器人在相同的群间隔 结束")
    private Integer diffRobotIntervalEnd;

    @ApiModelProperty(value = "同时进一个群的水军数量")
    private Integer joinGroupLimit;

    @ApiModelProperty(value = "同时进一个群的水军时间间隔 开始")
    private Integer joinGroupStart;

    @ApiModelProperty(value = "同时进一个群的水军时间间隔 结束")
    private Integer joinGroupEnd;

    @ApiModelProperty(value = "根据内容目标国优选群和水军（全局）")
    private List<VibeRuleTargetParam> targetParams;

    private String merchantId;
}
