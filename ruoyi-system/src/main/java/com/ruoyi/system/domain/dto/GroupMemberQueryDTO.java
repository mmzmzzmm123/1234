package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 朱少波
 * @date 2024/1/15/015 15:18
 */
@Data
@ApiModel("群内成员查询DTO")
public class GroupMemberQueryDTO extends PageBaseDTO {

    @ApiModelProperty(value = "名称")
    private String fullName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "成员编号")
    private String memberId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "最后在线时间-开始")
    private LocalDateTime wasOnlineStart;

    @ApiModelProperty(value = "最后在线时间-结束")
    private LocalDateTime wasOnlineEnd;

    @ApiModelProperty(value = "成员身份 0普通 1群主 2管理员 9-bot")
    private Integer memberType;

}
