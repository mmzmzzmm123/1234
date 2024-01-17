package com.ruoyi.system.openapi.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.Date;
import java.util.List;

@Data
public class ExtTgSelectGroupByMerchantVO {

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
    private String robotSerialNo;
    @ApiModelProperty("机器人信息列表")
    private List<ExtTgEasyRobotInfo> robots = Lists.newArrayList();

    @Data
    public static class ExtTgEasyRobotInfo {
        @ApiModelProperty("机器人编号")
        private String robotSerialNo;
        @ApiModelProperty("名字")
        private String firstName;
        @ApiModelProperty("姓氏")
        private String lastName;
        private String chatroomSerialNo;
    }
}
