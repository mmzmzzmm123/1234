package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ThirdTgSelectGroupMemberDTO {

    @ApiModelProperty(
            value = "群编",
            required = true
    )
    private String chatroomSerialNo;
    @ApiModelProperty("成员编号")
    private List<String> memberSerialNos;
    @ApiModelProperty("最后在线时间-开始时间")
    private Date startTime;
    @ApiModelProperty("最后在线时间-结束时间")
    private Date endTime;
    @ApiModelProperty("成员身份 0普通 1群主 2管理员")
    private Integer memberType;
    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("用户名")
    private String userName;

}
