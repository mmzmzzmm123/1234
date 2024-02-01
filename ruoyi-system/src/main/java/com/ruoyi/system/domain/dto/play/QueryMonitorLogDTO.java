package com.ruoyi.system.domain.dto.play;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/19/019 20:55
 * @Description :
 */
@Data
@ApiModel("查询执行日志DTO")
public class QueryMonitorLogDTO extends PageBaseDTO {

    @ApiModelProperty("剧本ID")
    @NotBlank(message = "剧本ID不能为空")
    private String playId;

    @ApiModelProperty("触发时间-开始")
    private LocalDateTime localDateTimeStart;


    @ApiModelProperty("触发时间-结束")
    private LocalDateTime localDateTimeEnd;

    @ApiModelProperty("群编号")
    private String groupSerialNo;

    @ApiModelProperty("群名称")
    private String groupName;

    @ApiModelProperty("触发方式 KICK_OUT:踢人 KICK_REPEAL:踢人并删除历史消息 ,RESTRICT:禁言,DELETE_MESSAGE:删除消息")
    private String dealFunction;

}
