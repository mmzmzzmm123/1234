package com.ruoyi.common.core.domain.entity;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.dto.VibeRuleTargetParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


@Data
@TableName("t_vibe_rule")
public class VibeRule {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 单个水军每天入群限制(全局)
     */
    private Integer joinLimitByDay;

    /**
     * 单个水军累计入群限制(全局)
     */
    private Integer joinLimitByTotal;

    /**
     * 单个水军每日设为群管理员上限(全局)
     */
    private Integer setManageLimitByDay;

    /**
     * 单个水军累计设为群管理员上限(全局)
     */
    private Integer setManageLimitByTotal;

    /**
     * 单个水军带群数(全局)
     */
    private Integer groupNum;

    /**
     * 单个群主号可设置管理员的上限(全局)
     */
    private Integer setManageLimit;

    /**
     * 单个群累计发送失败次数上限（群维度）
     */
    private Integer errLimit;

    /**
     * 单个群累计双向数上限（群维度）
     */
    private Integer bothwayLimit;

    /**
     * 单个群累计封号数上限（群维度）
     */
    private Integer bannedLimit;

    /**
     * 按代理IP段离散: 1是 0否（群维度）
     */
    private Integer periodByIp;

    /**
     * B段离散: 1是 0否（群维度）
     */
    private Integer periodByB;

    /**
     * C段离散: 1是 0否（群维度）
     */
    private Integer periodByC;

    /**
     * 备用号 水军倍数（群维度）
     */
    private Integer standbyNum;

    /**
     * 同一个机器人不同群退群间隔 开始(全局)
     */
    private Integer robotIntervalStart;

    /**
     * 同一个机器人不同群退群间隔 结束(全局)
     */
    private Integer robotIntervalEnd;

    /**
     * 不同的机器人在相同的群间隔  开始(全局)
     */
    private Integer diffRobotIntervalStart;

    /**
     * 不同的机器人在相同的群间隔 结束(全局)
     */
    private Integer diffRobotIntervalEnd;

    /**
     * 同时进一个群的水军数量(全局)
     */
    private Integer joinGroupLimit;

    /**
     * 同时进一个群的水军时间间隔 开始(全局)
     */
    private Integer joinGroupStart;

    /**
     * 同时进一个群的水军时间间隔 结束(全局)
     */
    private Integer joinGroupEnd;

    /**
     * 根据内容目标国优选群和水军（全局）
     */
    private String targetParams;

    private Long userId;

    /**
     * 状态：1正常 0删除
     */
    private Integer status;

    public List<VibeRuleTargetParam> covertTargetParams() {
        if (StringUtils.isBlank(this.targetParams)) {
            return null;
        }
        return JSONArray.parseArray(this.targetParams, VibeRuleTargetParam.class);
    }
}
