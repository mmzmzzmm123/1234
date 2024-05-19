package com.onethinker.onethinker.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.onethinker.common.annotation.Excel;
import com.onethinker.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 订阅消息发送结果对象 t_subscribe_msg_send_result
 *
 * @author yangyouqi
 * @date 2024-01-15
 */
@Data
@TableName("t_subscribe_msg_send_result")
public class SubscribeMsgSendResult extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表主键
     */
    private Long id;

    /**
     * 是否可见，0发送失败，1发送成功
     */
    @Excel(name = "是否可见，0发送失败，1发送成功")
    private Long enabled;

    /**
     * 权重
     */
    @Excel(name = "权重")
    private Long weight;

    /**
     * 小程序模版id
     */
    @Excel(name = "小程序模版id")
    private String templateId;

    /**
     * 平台用户
     */
    @Excel(name = "平台用户")
    private Long puUserId;

    /**
     * 请求内容
     */
    @Excel(name = "请求内容")
    private String sendMsg;

    /**
     * 请求结果
     */
    @Excel(name = "请求结果")
    private String sendResult;


}
