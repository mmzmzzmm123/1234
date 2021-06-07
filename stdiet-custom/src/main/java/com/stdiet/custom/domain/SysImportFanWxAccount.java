package com.stdiet.custom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 导粉记录中对应微信记录对象 sys_import_fan_wx_account
 *
 * @author xzj
 * @date 2021-05-17
 */
@Data
public class SysImportFanWxAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 导粉记录ID */
    @Excel(name = "导粉记录ID")
    private Long importFanRecordId;

    /** 导粉微信ID */
    @Excel(name = "导粉微信ID")
    private Long importWxAccountId;

    /** 导粉数量 */
    @Excel(name = "导粉数量")
    private Long importFanNum;

    /** 导粉时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "导粉时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date importTime;

    /** 删除标识 0未删除 1已删除 */
    private Long delFlag;

    //导粉日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date importFanDate;

    //售前
    private Long preSaleId;
}