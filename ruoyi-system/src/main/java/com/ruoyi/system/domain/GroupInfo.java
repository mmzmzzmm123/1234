package com.ruoyi.system.domain;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 群基础信息(GroupInfo)表实体类
 *
 * @author 朱少波
 * @since 2024-01-15 09:26:46
 */
@ApiModel(value = "群基础信息")
@TableName("t_group_info")
@Data
public class GroupInfo {

    /**
     * 群Id
     */
    @ApiModelProperty(value = "群Id")
    @TableId
    private String groupId;

    /**
     * 群编号
     */
    @ApiModelProperty(value = "群编号")
    @TableField("group_serial_no")
    private String groupSerialNo;

    /**
     * 群编号
     */
    @ApiModelProperty(value = "老的群编号")
    @TableField("old_original_group_id")
    private String oldGroupSerialNo;

    /**
     * 群名称
     */
    @ApiModelProperty(value = "群名称")
    @TableField("group_name")
    private String groupName;

    /**
     * 群备注
     */
    @ApiModelProperty(value = "群备注")
    @TableField("group_remark")
    private String groupRemark;

    /**
     * 群简介
     */
    @ApiModelProperty(value = "群简介")
    @TableField("group_about")
    private String groupAbout;

    /**
     * 群主链接
     */
    @ApiModelProperty(value = "群主链接")
    @TableField("group_link")
    private String groupLink;

    /**
     * 群私密链接
     */
    @ApiModelProperty(value = "群私密链接")
    @TableField("group_private_link")
    private String groupPrivateLink;

    /**
     * 群类型  10私密群 20公开群
     */
    @ApiModelProperty(value = "群类型  10私密群 20公开群")
    @TableField("group_type")
    private Integer groupType;

    /**
     * 群是否是频道 0-不是 1-是
     */
    @ApiModelProperty(value = "群是否是频道 0-不是 1-是")
    @TableField("is_channel")
    private Integer isChannel;

    /**
     * 群成员数
     */
    @ApiModelProperty(value = "群成员数")
    @TableField("member_count")
    private Integer memberCount;

    /**
     * 被踢人数
     */
    @ApiModelProperty(value = "被踢人数")
    @TableField("kicked_count")
    private Integer kickedCount;

    /**
     * 封号数
     */
    @ApiModelProperty(value = "封号数")
    @TableField("banned_count")
    private Integer bannedCount;

    /**
     * 在线人数
     */
    @ApiModelProperty(value = "在线人数")
    @TableField("online_member_count")
    private Integer onlineMemberCount;

    /**
     * 群邀请链接
     */
    @ApiModelProperty(value = "群邀请链接")
    @TableField("group_invite_link")
    private String groupInviteLink;

    /**
     * 创建类型 10-开平导入群  20-外部自建群
     */
    @ApiModelProperty(value = "创建类型 10-开平导入群  20-外部自建群")
    @TableField("create_type")
    private Integer createType;

    /**
     * 注册时间
     */
    @ApiModelProperty(value = "注册时间")
    @TableField("registration_time")
    private LocalDateTime registrationTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
    
}
