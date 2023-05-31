package com.ruoyi.office.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 店铺房间对象 t_room
 * 
 * @author ruoyi
 * @date 2023-05-29
 */
@Data
public class TRoom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    private Long storeId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** wifi */
    @Excel(name = "wifi")
    private String wifi;

    /** 包厢控制 */
    @Excel(name = "包厢控制")
    private Long equipId;

    /** 桌台控制 */
    @Excel(name = "桌台控制")
    private String tableCode;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 技术时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "技术时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date stopTime;

    /** 状态(空闲/已预约/使用中/预约超时未使用/不可用) */
    @Excel(name = "状态(空闲/已预约/使用中/预约超时未使用/不可用)")
    private String status;

}
