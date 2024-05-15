package com.onethinker.bk.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 聊天群成员对象 bk_im_chat_group_user
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_im_chat_group_user")
public class ImChatGroupUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 群ID
     */
    @Excel(name = "群ID")
    private Integer groupId;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 审核用户ID
     */
    @Excel(name = "审核用户ID")
    private Long verifyUserId;

    /**
     * 是否管理员[0:否，1:是]
     */
    @Excel(name = "是否管理员[0:否，1:是]")
    private Integer adminFlag;

    /**
     * 用户状态[0:未审核，1:审核通过，2:禁言]
     */
    @Excel(name = "用户状态[0:未审核，1:审核通过，2:禁言]")
    private Integer userStatus;


}
