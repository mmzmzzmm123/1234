package com.stdiet.custom.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 导粉管理对象 sys_import_fan_record
 *
 * @author xzj
 * @date 2021-05-17
 */
@Data
public class SysImportFanRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 导粉日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "导粉日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date importFanDate;

    /** 导粉渠道 */
    @Excel(name = "导粉渠道")
    private Long importFanChannel;

    /** 进粉直播间 */
    @Excel(name = "进粉直播间")
    private Long importFanLive;

    /** 删除标识，0未删除 1已删除 */
    private Long delFlag;

    //导粉时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date importTime;

    //微信ID
    private Long wxAccountId;

    //进粉数量
    private Long fanNum;

    private String importFanChannelName;

    private String liveRoomName;

    private String liveNutritionistName;

    private List<Map<String,Object>> wxAccountList;


    private String importFanChannels;

    private String importFanLives;

    private String wxAccountIds;

    private String fanNums;

    private String importTimes;


    //总进粉数量
    private Integer totalFanNum;
}