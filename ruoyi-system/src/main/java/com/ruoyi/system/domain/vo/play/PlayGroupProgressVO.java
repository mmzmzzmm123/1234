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

    @ApiModelProperty("剧本ID")
    private String playId;

    @ApiModelProperty("炒群状态")
    private Integer state;


    @ApiModelProperty("群Id")
    private String groupId;
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
    private String groupType;
    @ApiModelProperty("群是否是频道 0-不是 1-是")
    private String isChannel;
    @ApiModelProperty("群成员数")
    private String memberCount;
    @ApiModelProperty("被踢人数")
    private String kickedCount;
    @ApiModelProperty("封号数")
    private String bannedCount;

    @ApiModelProperty("总进度数量")
    private Integer totalNum;

    @ApiModelProperty("当前进度数量")
    private Integer currentNum;

}
