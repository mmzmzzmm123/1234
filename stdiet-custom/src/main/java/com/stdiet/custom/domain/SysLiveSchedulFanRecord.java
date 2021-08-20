package com.stdiet.custom.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.utils.DateUtils;
import lombok.Data;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 直播记录中每场直播对应进粉记录对象 sys_live_schedul_fan_record
 *
 * @author xzj
 * @date 2021-08-19
 */
@Data
public class SysLiveSchedulFanRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 直播ID */
    @Excel(name = "直播ID")
    private Long liveSchedulId;

    /** 微信账号ID */
    @Excel(name = "微信账号ID")
    private Long saleWxAccountId;

    /** 进粉客户微信号 */
    @Excel(name = "进粉客户微信号")
    private String fanWxNumber;

    /** 进粉时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "进粉时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date enterFanTime;

    /** 删除标识 0未删除 1已删除 */
    private Integer delFlag;
}