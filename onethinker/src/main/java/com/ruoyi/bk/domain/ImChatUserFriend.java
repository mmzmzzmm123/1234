package com.ruoyi.bk.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 好友对象 bk_im_chat_user_friend
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_im_chat_user_friend")
public class ImChatUserFriend extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 好友ID */
    @Excel(name = "好友ID")
    private Long friendId;

    /** 朋友状态[0:未审核，1:审核通过] */
    @Excel(name = "朋友状态[0:未审核，1:审核通过]")
    private Integer friendStatus;


}
