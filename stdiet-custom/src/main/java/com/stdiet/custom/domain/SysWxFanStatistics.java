package com.stdiet.custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 进粉统计对象 sys_wx_fan_statistics
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
@Data
public class SysWxFanStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    @Excel(name = "销售")
    private String userName;

    //销售ID
    private Long userId;

    @Excel(name = "进粉账号")
    private String account;

    private Long accountId;

    //销售组别
    private String saleGroup;

    /** 微信号ID */
    private Long wxId;

    @Excel(name = "微信昵称")
    private String wxNickName;

    @Excel(name = "微信号")
    private String wxAccount;

    /** 进粉时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进粉时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fanTime;

    /** 进粉个数 */
    @Excel(name = "进粉个数")
    private Integer fanNum;

    /** 删除标识 0未删除 1已删除，默认0 */
    private Integer delFlag;

    //进粉总数量
    private Integer totalFanNum;

    //销售组别ID
    private Integer saleGroupId;

    //排序参数，null或者0：按照id倒序 1：按照组别ID、id顺序正序
    private Integer sortFlag;

    //进粉开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fanStartTime;

    //进粉结束时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fanEndTime;
}