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
 * 群成员信息(GroupMember)表实体类
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:25
 */
@ApiModel(value = "群成员信息")
@TableName("t_group_member")
@Data
public class GroupMember {
    
    /**
    * id
    */
    @ApiModelProperty(value = "id")
    @TableId
    private String id;
    
    /**
    * 群Id
    */
    @ApiModelProperty(value = "群Id")
    @TableField("group_id")
    private String groupId;
    
    /**
    * 成员编号
    */
    @ApiModelProperty(value = "成员编号")
    @TableField("member_id")
    private String memberId;
    
    /**
    * 名
    */
    @ApiModelProperty(value = "名")
    @TableField("first_name")
    private String firstName;
    
    /**
    * 姓
    */
    @ApiModelProperty(value = "姓")
    @TableField("last_name")
    private String lastName;
    
    /**
    * 全称
    */
    @ApiModelProperty(value = "全称")
    @TableField("full_name")
    private String fullName;
    
    /**
    * 用户名
    */
    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String userName;
    
    /**
    * 手机号
    */
    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;
    
    /**
    * 最后在线时间
    */
    @ApiModelProperty(value = "最后在线时间")
    @TableField("was_online")
    private LocalDateTime wasOnline;
    
    /**
    * 成员在线状态：-1未知 10在线 20离线 30近期曾上线 40上周 50上个月
    */
    @ApiModelProperty(value = "成员在线状态：-1未知 10在线 20离线 30近期曾上线 40上周 50上个月")
    @TableField("member_status")
    private Integer memberStatus;
    
    /**
    * 最后在线时长
    */
    @ApiModelProperty(value = "最后在线时长")
    @TableField("was_online_date")
    private String wasOnlineDate;
    
    /**
    * 成员身份 0普通 1群主 2管理员
    */
    @ApiModelProperty(value = "成员身份 0普通 1群主 2管理员")
    @TableField("member_type")
    private Integer memberType;
    
    /**
    * 多久时间在线
    */
    @ApiModelProperty(value = "多久时间在线")
    @TableField("last_seen_ago")
    private String lastSeenAgo;
    
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
