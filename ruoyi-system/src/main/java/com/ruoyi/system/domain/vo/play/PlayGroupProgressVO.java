package com.ruoyi.system.domain.vo.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/15/015 9:10
 * @Description :
 */
@Data
@ApiModel("炒群进度VO")
public class PlayGroupProgressVO {

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("剧本ID")
    private String playId;

    @ApiModelProperty("炒群状态 0-待执行 1-炒群中 2-已完成 3-已暂停 4-已取消")
    private Integer state;

    @ApiModelProperty("推送ID")
    private Integer pushId;

    @ApiModelProperty("推送状态 1:待发送 2:进行中 3:已结束 4:人工暂停 5:推送失败 6:取消")
    private Integer pushState;

    @ApiModelProperty("群Id")
    private String groupId;
    @ApiModelProperty("群编号")
    private String groupSerialNo;
    @ApiModelProperty("群名称")
    private String groupName;
    @ApiModelProperty("群备注")
    private String groupRemark;
    @ApiModelProperty("群简介")
    private String groupAbout;
    @ApiModelProperty("群主链接")
    private String groupLink;
    @ApiModelProperty("群私密链接")
    private String groupPrivateLink;
    @ApiModelProperty("群类型 10私密群 20公开群")
    private Integer groupType;
    @ApiModelProperty("群邀请链接")
    private String groupInviteLink;


    @ApiModelProperty("群状态 0-正常 1-封群")
    private Integer groupStatus;
    @ApiModelProperty("群状态变化时间(封群时间)")
    private Date groupStatusTime;

    @ApiModelProperty("群成员数")
    private Integer memberCount;
    @ApiModelProperty("进群数")
    private Integer joinCount;
    @ApiModelProperty("退群数")
    private Integer exitCount;
    @ApiModelProperty("链接进群人数(拉手号)")
    private Integer linkJoinCount;
    @ApiModelProperty("水军号")
    private Integer navyCount;


    @ApiModelProperty("总进度数量")
    private Integer totalNum;
    @ApiModelProperty("当前进度数量")
    private Integer currentNum;

    @ApiModelProperty("提示")
    private String tip;

    @ApiModelProperty("群主封号状态 10:未封号 20:疑似封号 30:封号离线")
    private Integer groupMasterSealStatus;


    @ApiModelProperty(value = "是否超级群 0-普通群 1-超级群")
    private Integer superGroup;


    @ApiModelProperty(value = "升超级群时间")
    private Date upgradeTime;

    @ApiModelProperty("待发送")
    private Integer waitNum;
    @ApiModelProperty("发送成功")
    private Integer successNum;
    @ApiModelProperty("发送成功")
    private Integer failNum;

}
