package com.stdiet.custom.domain;

import lombok.Data;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 客户消息通知对象 sys_message_notice
 *
 * @author xzj
 * @date 2021-04-26
 */
@Data
public class SysMessageNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 消息属性，0 公共消息 1 私有消息 */
    @Excel(name = "消息属性，0 公共消息 1 私有消息")
    private Integer messageProperty;

    /** 消息对应客户ID (公共消息时该字段为0) */
    @Excel(name = "消息对应客户ID (公共消息时该字段为0)")
    private Long messageCustomer;

    //用户加密ID，非持久化字段
    private String customerId;

    /** 消息类型 */
    @Excel(name = "消息类型")
    private Integer messageType;

    /** 消息标题 */
    @Excel(name = "消息标题")
    private String messageTitle;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String messageContent;

    /** 是否已读 0未读 1已读 */
    @Excel(name = "是否已读 0未读 1已读")
    private Integer readType;

    /** 当前消息对应关键参数，多个参数可保存json字符串 */
    @Excel(name = "当前消息对应关键参数，多个参数可保存json字符串")
    private String messageKey;

    /** 删除标识 0未删除 1已删除 */
    private Integer delFlag;
}