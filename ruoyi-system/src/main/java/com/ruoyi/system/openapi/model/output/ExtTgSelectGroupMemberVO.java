package com.ruoyi.system.openapi.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ExtTgSelectGroupMemberVO {

    @ApiModelProperty("群编号")
    private String chatroomSerialNo;
    @ApiModelProperty("群成员编号")
    private String memberSerialNo;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("姓氏")
    private String lastName;
    @ApiModelProperty("名字")
    private String firstName;
    @ApiModelProperty("用户号")
    private String userName;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("最后在线时间")
    private Date wasOnline;
    @ApiModelProperty("成员身份 0普通 1群主 2管理员")
    private Integer memberType;
    @ApiModelProperty("名称")
    private String name;

}
