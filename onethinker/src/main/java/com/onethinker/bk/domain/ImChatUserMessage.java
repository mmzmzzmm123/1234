package com.onethinker.bk.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 单聊记录对象 bk_im_chat_user_message
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_im_chat_user_message")
public class ImChatUserMessage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 发送ID */
    @Excel(name = "发送ID")
    private Long fromId;

    /** 接收ID */
    @Excel(name = "接收ID")
    private Long toId;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 是否已读[0:未读，1:已读] */
    @Excel(name = "是否已读[0:未读，1:已读]")
    private Integer messageStatus;


}
