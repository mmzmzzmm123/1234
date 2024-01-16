package com.ruoyi.common.core.domain.entity.robot;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@TableName("t_robot_statistics")
public class RobotStatistics {
    @TableId
    private String id;
    @ApiModelProperty(value = "号编号")
    private String robotSerialNo;
    @ApiModelProperty(value = "号每日入群数量")
    private Integer oneDayJoinGroupCount;
    @ApiModelProperty(value = "号累计入群数量")
    private Integer totalJoinGroupCount;
    @ApiModelProperty(value = "号每日设管理员数量")
    private Integer oneDaySetAdminCount;
    @ApiModelProperty(value = "号累计设管理员数量")
    private Integer totalSetAdminCount;
    @ApiModelProperty(value = "号带群数")
    private Integer groupCount;
    @ApiModelProperty(value = "是否锁定号,0-否,1-是")
    private Integer isLock;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "群主号已设置管理员数")
    private Integer totalLeaderSetAdminCount;

}
