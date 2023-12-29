package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 保存并创建任务
 *
 * @author Jing.Zhang
 */
@Data
@ApiModel("保存并创建任务")
public class SaveAndCreateTaskVO {

    @ApiModelProperty("群ID")
    private List<String> groupIds;

    @ApiModelProperty("设置群人数类型 0-单群拉人数 1-单群人数上限")
    private Integer groupPersonType;

    @ApiModelProperty("群来源: 0-自有群;")
    private Integer groupSource;

    @ApiModelProperty("入群目标类型 0-手机号")
    private Integer inGroupType;

    @ApiModelProperty("单群拉人数")
    private Integer singleGroupPerson;

    @ApiModelProperty("具体目标集合")
    private List<String> targetIds;

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("拉手国国家编码")
    private String countries;

    @ApiModelProperty("拉人规则")
    private JoinChatRoomRule rule;

    @Data
    @ApiModel("拉人规则")
    public static class JoinChatRoomRule {
        @ApiModelProperty("不同机器人进群间隔最小值")
        private Integer nDifferentEnterRoomIntervalMin;

        @ApiModelProperty("不同机器人进群间隔最大值")
        private Integer nDifferentEnterRoomIntervalMax;

        @ApiModelProperty("不同账号间隔时间最小值")
        private Integer nDifferentRobotIntervalMin;

        @ApiModelProperty("不同账号间隔时间最大值")
        private Integer nDifferentRobotIntervalMax;

        @ApiModelProperty("进群后拉人间隔最小值")
        private Integer nEnterRoomInviteIntervalMin;

        @ApiModelProperty("进群后拉人间隔最大值")
        private Integer nEnterRoomInviteIntervalMax;

        @ApiModelProperty("成为联系人后私信间隔最小值（单位秒）")
        private Integer nContactsEnterRoomIntervalMin;

        @ApiModelProperty("成为联系人后私信间隔最大值（单位秒）")
        private Integer nContactsEnterRoomIntervalMax;

        @ApiModelProperty("单群机器人国家数")
        private Integer nAllowCountryCount;
    }

}
