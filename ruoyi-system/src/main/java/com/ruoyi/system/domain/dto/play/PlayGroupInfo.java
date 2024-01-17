package com.ruoyi.system.domain.dto.play;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("剧本群信息表")
@TableName("t_play_group_info")
public class PlayGroupInfo {

    @ApiModelProperty("主键")
    @TableId
    private String groupId;

    @ApiModelProperty("商家Id")
    private String merchantId;

    @ApiModelProperty("群链接")
    private String groupUrl;

    @ApiModelProperty("群唯一标识")
    private String tgGroupId;

    @ApiModelProperty("群头像")
    private String groupImageUrl;

    @ApiModelProperty("群名称")
    private String tgGroupName;

    @ApiModelProperty("群成员数量")
    private String memberCount;

    @ApiModelProperty("所属任务ID")
    private String playId;

    @ApiModelProperty("0正常 1删除")
    private Integer isDelete;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date modifyTime;

    @ApiModelProperty("炒群状态 0-待执行 1-炒群中 2-已完成 3-已暂停 4-已取消")
    private Integer state;

    @ApiModelProperty("1入群中 2入群完成 3入群失败")
    private Integer intoStatus;

    @ApiModelProperty("提示")
    private String tip;

}
