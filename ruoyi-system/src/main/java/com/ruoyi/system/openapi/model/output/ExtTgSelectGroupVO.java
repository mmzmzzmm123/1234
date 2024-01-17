package com.ruoyi.system.openapi.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.Date;
import java.util.List;

@Data
public class ExtTgSelectGroupVO {

    @ApiModelProperty("群编号")
    private String chatroomSerialNo;
    @ApiModelProperty("群名称")
    private String title;
    @ApiModelProperty("成员数量")
    private Integer memberCount;
    @ApiModelProperty("群的创建时间")
    private String dateTime;
    @ApiModelProperty("入库时间")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("头像")
    private String headImgUrl;
    @ApiModelProperty("邀请链接")
    private String inviteLink;
}
