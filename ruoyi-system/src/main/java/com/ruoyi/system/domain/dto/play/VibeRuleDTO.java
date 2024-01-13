package com.ruoyi.system.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class VibeRuleDTO {
    private Integer id;

    private String groupId;

    @ApiModelProperty(value = "水军号综合调度（全局）")
    private ComprehensiveParam dispatch;

    @ApiModelProperty(value = "水军号止损（群维度）")
    private StopConfig stopConfig;

    @ApiModelProperty(value = "水军号离散（群维度）")
    private SpreadConfig spreadConfig;

    @ApiModelProperty(value = "备用号数量（群维度）")
    private Integer standbyNum;

    @ApiModelProperty(value = "水军号退群离散（全局）")
    private QuitGroup quitGroup;

    @ApiModelProperty(value = "水军号进群离散（全局）")
    private JoinGroup joinGroup;

    @ApiModelProperty(value = "根据内容目标国优选群和水军（全局）")
    private List<TargetParam> targetParams;

    @Data
    public static class ComprehensiveParam {
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
        private Integer setManageLimitByAdmin;
    }

    @Data
    public static class StopConfig {
        @ApiModelProperty(value = "单个群累计发送失败次数上限")
        private Integer errLimit;

        @ApiModelProperty(value = "单个群累计双向数上限")
        private Integer bothwayLimit;

        @ApiModelProperty(value = "单个群累计封号数上限")
        private Integer bannedLimit;
    }

    @Data
    public static class SpreadConfig {
        @ApiModelProperty(value = "按代理IP段离散")
        private Integer periodByIp;

        @ApiModelProperty(value = "B段离散")
        private Integer periodByB;

        @ApiModelProperty(value = "C段离散")
        private Integer periodByC;
    }

    @Data
    public static class QuitGroup {
        @ApiModelProperty(value = "同一个机器人不同群退群间隔 开始")
        private Integer robotIntervalStart;

        @ApiModelProperty(value = "同一个机器人不同群退群间隔 结束")
        private Integer robotIntervalEnd;

        @ApiModelProperty(value = "不同的机器人在相同的群间隔  开始")
        private Integer diffRobotIntervalStart;

        @ApiModelProperty(value = "不同的机器人在相同的群间隔 结束")
        private Integer diffRobotIntervalEnd;
    }

    @Data
    public static class JoinGroup {
        @ApiModelProperty(value = "同时进一个群的水军数量")
        private Integer limit;

        @ApiModelProperty(value = "同时进一个群的水军时间间隔 开始")
        private Integer start;

        @ApiModelProperty(value = "同时进一个群的水军时间间隔 结束")
        private Integer end;
    }

    @Data
    public static class TargetParam {
        @ApiModelProperty(value = "内容目标国编码")
        private String countryCode;

        @ApiModelProperty(value = "内容目标国名称")
        private String countryName;

        @ApiModelProperty(value = "优选群主号和水军国编码")
        private String preferenceCode;

        @ApiModelProperty(value = "优选群主号和水军国名称")
        private String preferenceName;
    }
}
