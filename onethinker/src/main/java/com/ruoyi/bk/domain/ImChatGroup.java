package com.ruoyi.bk.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 聊天群对象 bk_im_chat_group
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_im_chat_group")
public class ImChatGroup extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 群名称 */
    @Excel(name = "群名称")
    private String groupName;

    /** 群主用户ID */
    @Excel(name = "群主用户ID")
    private Long masterUserId;

    /** 群头像 */
    @Excel(name = "群头像")
    private String avatar;

    /** 简介 */
    @Excel(name = "简介")
    private String introduction;

    /** 公告 */
    @Excel(name = "公告")
    private String notice;

    /** 进入方式[0:无需验证，1:需要群主或管理员同意] */
    @Excel(name = "进入方式[0:无需验证，1:需要群主或管理员同意]")
    private Integer inType;

    /** 类型[1:聊天群，2:话题] */
    @Excel(name = "类型[1:聊天群，2:话题]")
    private Integer groupType;


}
