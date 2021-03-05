package com.stdiet.custom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 订单服务暂停对象 sys_order_pause
 *
 * @author xzj
 * @date 2021-01-07
 */
@Data
public class SysOrderPause {

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    private Long cusId;

    /**
     * 服务暂停开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "服务暂停开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pauseStartDate;

    /**
     * 服务暂停结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "服务暂停结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pauseEndDate;

    /**
     * 服务暂停理由
     */
    @Excel(name = "服务暂停理由")
    private String reason;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 删除标识 0未删除 1已删除，默认0
     */
    private Integer delFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String createBy;

    private String updateBy;
}