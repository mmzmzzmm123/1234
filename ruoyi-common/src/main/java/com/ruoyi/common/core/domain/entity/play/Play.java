package com.ruoyi.common.core.domain.entity.play;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_play_info")
public class Play {
    @TableId
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 群来源：0-平台提供 1-外部群
     */
    private Integer groupSource;

    /**
     * 群注册天数
     */
    private Integer groupDay;

    /**
     * 炒群条件：0-人数 1-时间
     */
    private Integer groupCondition;

    /**
     * 群人数
     */
    private Integer userNum;

    /**
     * 时间条件开始类型：0-立即开始 1定时开始
     */
    private Integer startType;

    /**
     * 群邀请链接
     */
    private String groupUrls;

    /**
     * 群需求数量
     */
    private Integer groupNum;

    /**
     * 每个群演员数
     */
    private Integer robotNum;

    /**
     * 混淆状态：0-不混淆 1-混淆
     */
    private Integer isConfound;

    /**
     * 剧本状态：1-调度中 2-炒群中 3-已暂停 4-已取消 5-已完成
     */
    private Integer state;

    /**
     * 发送机制配置
     */
    private String sendMechanism;

    /**
     * 广告监控配置
     */
    private String adMonitor;

    /**
     * 扩展配置
     */
    private String playExt;

    /**
     * 接粉号池
     */
    private String urlPool;

    /**
     * 操作员id
     */
    private String sysUserId;

    /**
     * 是否删除: 0否 1是
     */
    private Integer isDelete;

    /**
     * 速度
     */
    private BigDecimal speed;
}
