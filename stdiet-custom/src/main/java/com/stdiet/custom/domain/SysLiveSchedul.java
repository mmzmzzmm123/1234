package com.stdiet.custom.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 直播排班对象 sys_live_schedul
 *
 * @author xzj
 * @date 2021-05-12
 */
@Data
public class SysLiveSchedul extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 直播间序号 */
    @Excel(name = "直播间序号")
    private Integer liveRoom;

    /** 排班日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "排班日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date liveSchedulDate;

    /** 直播开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "直播开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date liveStartTime;

    private String liveStartTimeString;

    /** 直播结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "直播结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date liveEndTime;

    /** 直播营养师 */
    @Excel(name = "直播营养师")
    private Long liveNutritionistId;

    /** 直播运营 */
    @Excel(name = "直播运营")
    private Long liveOperatorId;

    /** 直播策划 */
    @Excel(name = "直播策划")
    private Long livePlannerId;

    /** 微信账号管理的进粉渠道 */
    @Excel(name = "微信账号管理的进粉渠道")
    private Integer fanChannel;

    /** 直播状态，0未开播  1已开播  2已下播 */
    @Excel(name = "直播状态，0未开播  1已开播  2已下播")
    private Long liveStatus;

    /** 删除标识，默认0 */
    private Integer delFlag;

    //微信账号ID
    private Long wxAccountId;

    //直播间名称
    private String liveRoomName;

    private String liveNutritionistName;

    private String livePlannerName;

    private String liveOperatorName;

    private String fanChannelName;

    //导粉情况
    private List<Map<String,Object>> wxAccountList;

    //进粉情况
    private List<Map<String,Object>> addFanList;
}