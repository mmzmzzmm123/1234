package com.ruoyi.common.core.domain.entity.robot;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
@TableName("t_telegram_det_chatroom_admin_distr")
public class TelegramDetChatroomAdminDistr {
    @ApiModelProperty(value = "群主编号")
    @TableId
    private String adminSerialNo;
    @ApiModelProperty(value = "群主id")
    private String adminId;
    @ApiModelProperty(value = "群成员数")
    private Integer accountNum;
    @ApiModelProperty(value = "成员封号数")
    private Integer accountForbiddenNum;
    @ApiModelProperty(value = "成员封号率")
    private String accountRate;
    @ApiModelProperty(value = "群数")
    private Integer groupNum;
    @ApiModelProperty(value = "封群数")
    private Integer groupForbiddenNum;
    @ApiModelProperty(value = "封群率")
    private String groupRate;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
