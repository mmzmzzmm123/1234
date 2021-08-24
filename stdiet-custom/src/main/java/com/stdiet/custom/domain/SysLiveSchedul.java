package com.stdiet.custom.domain;

import java.math.BigDecimal;
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
    private Integer liveRoom;

    /** 排班日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "直播日期", width = 30, dateFormat = "yyyy-MM-dd", sort = 1)
    private Date liveSchedulDate;

    /** 直播开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date liveStartTime;

    @Excel(name = "直播时间", width = 30, sort = 3)
    private String liveStartTimeString;

    /** 直播结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date liveEndTime;

    /** 直播营养师 */
    private Long liveNutritionistId;

    /** 直播运营 */
    private Long liveOperatorId;

    /** 直播策划 */
    private Long livePlannerId;

    /** 微信账号管理的进粉渠道 */
    private Integer fanChannel;

    /** 直播状态，0未开播  1已开播  2已下播 */
    @Excel(name = "直播状态", readConverterExp="0=未开播,1=已开播,2=已下播",sort = 4)
    private Long liveStatus;

    /** 删除标识，默认0 */
    private Integer delFlag;

    //微信账号ID
    private Long wxAccountId;

    //直播间名称
    @Excel(name = "直播间", sort = 2)
    private String liveRoomName;

    @Excel(name = "直播营养师", sort = 5)
    private String liveNutritionistName;

    private String livePlannerName;

    private String liveOperatorName;

    @Excel(name = "所属账号",sort = 6)
    private String fanChannelName;

    //导粉情况
    private List<Map<String,Object>> wxAccountList;

    //导粉数量
    @Excel(name = "导粉数量",sort = 7,isStatistics = true,cellType = Excel.ColumnType.NUMERIC)
    private Integer importFanSize;

    //进粉情况
    private List<Map<String,Object>> addFanList;

    //进粉数量
    @Excel(name = "进粉数量",sort = 8, isStatistics=true,cellType = Excel.ColumnType.NUMERIC)
    private Integer  addFanSize;

    @Excel(name = "直播时长",sort = 9, isStatistics=true,cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal liveTotalHour;
}