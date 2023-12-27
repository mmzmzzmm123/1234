package com.ruoyi.system.extend.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetPlatformRobotPoolPageInputDTO extends UtTouchQueryPage {

    /**
     * 排序字段名称(1私信今日成功、2私信累计成功、3私信今日失败、4私信累计失败、5同步通讯录今日成功、6同步通讯录累计成功、7同步通讯录今日失败、8同步通讯录累计失败、9搜索用户名今日成功、10搜索用户名累计成功、11搜索用户名今日失败、12搜索用户名累计失败、13今日被拉黑次数、14累计被拉黑次数、15今日邀请入群成功次数、16累计邀请入群成功次数、17今日邀请入群失败次数、18累计邀请入群失败次数)
     */
    private String sortName = "";

    /**
     * 搜索内容类型：1姓名、2用户名、3登录号码、4机器人编码
     */
    private Integer searchContentType = 0;
    /**
     * 搜索内容
     */
    private String searchContent;
    /**
     * 最近上线时间开始区间
     */
    private LocalDateTime lastLoginTimeStart;
    /**
     * 最近上线时间结束区间
     */
    private LocalDateTime lastLoginTimeEnd;

    /**
     * 最近离线时间-开始
     */
    private LocalDateTime lastOfflineTimeStart;
    /**
     * 最近离线时间-结束
     */
    private LocalDateTime lastOfflineTimeEnd;
    /**
     * 最后状态同步时间-开始
     */
    private LocalDateTime lastSyncTimeStart;
    /**
     * 最后状态同步时间-结束
     */
    private LocalDateTime lastSyncTimeEnd;
    /**
     * 最后封号时间-开始
     */
    private LocalDateTime lastBlackTimeStart;
    /**
     * 最后封号时间-结束
     */
    private LocalDateTime lastBlackTimeEnd;
    /**
     * 收录创建时间开始
     */
    private LocalDateTime dtCreateTimeStart;
    /**
     * 收录创建时间结束
     */
    private LocalDateTime dtCreateTimeEnd;
    /**
     * Sync(Sync)=0 Normal(Normal)=1 Prohibit(Prohibit)=-4 Bothway(Bothway)=-3 Black(Black)=-2 Offline(Offline)=-1
     * 0同步中 1正常 -4禁止 -3双向 -2封号 -1离线
     */
    private Integer robotLogicStatus;
    /**
     * 国家编码
     */
    private String countryCode;
    /**
     * 最后双向时间-开始
     */
    private LocalDateTime lastBothwayTimeStart;
    /**
     * 最后双向时间-结束
     */
    private LocalDateTime lastBothwayTimeEnd;
    /**
     * 最后禁止时间-开始
     */
    private LocalDateTime lastProhibitStatusTimeStart;
    /**
     * 最后禁止时间-结束
     */
    private LocalDateTime lastProhibitStatusTimeEnd;
    /**
     * 锁定（0未锁定、1已锁定）
     */
    private Integer lock;
    /**
     * 锁定原因
     */
    private String lockReason;
    /**
     * 是否初始化PostBot机器人
     */
    private Boolean isSearchAddPostBotRobot;
    /**
     * 设置隐私手机号码谁可见（-1设置失败、0未设置、1设置成功）
     */
    private Integer setPhoneVisibilityStatus;
    private List<String> modelNames;
    /**
     * integer($int32)
     * nullable: true
     * 注册国家是否与登录国家一致（-1没有同步查询、0不相同、1相同）
     */
    private Integer registerLoginCountryFlag;
    /**
     * 人设分组ID
     */
    private Integer groupId;
    /**
     * 是否超级商家号（0划拨号、1超级商家号）
     */
    private Integer isSuperMerchantNo;
}
