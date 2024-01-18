package com.ruoyi.system.domain.vo.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty("推送状态 1:待发送 2:进行中 3:已结束 4:人工暂停 5:推送失败 6:系统暂停")
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

}
