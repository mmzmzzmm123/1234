package com.ruoyi.system.domain.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 朱少波
 * @date 2024/1/15/015 15:18
 */
@Data
@ApiModel("群内成员查询DTO")
public class GroupMemberQueryDTO extends PageBaseDTO {

    @ApiModelProperty(value = "群id")
    private String groupId;

    @ApiModelProperty(value = "名称")
    private String fullName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "成员编号")
    private String memberId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后在线时间-开始")
    private Date wasOnlineStart;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后在线时间-结束")
    private Date wasOnlineEnd;

    @ApiModelProperty(value = "成员身份 0普通 1群主 2管理员 9-bot")
    private Integer memberType;

}
