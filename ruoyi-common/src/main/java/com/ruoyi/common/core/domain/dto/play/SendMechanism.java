package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 发送机制
 */
@Data
public class SendMechanism {
    @ApiModelProperty(value = "多条内容间隔开始(秒)")
    private Integer msgSepStart;

    @ApiModelProperty(value = "多条内容间隔结束(秒)")
    private Integer msgSepEnd;

    @ApiModelProperty(value = "演员之间发送间隔开始(秒)")
    private Integer performerSepStart;

    @ApiModelProperty(value = "演员之间发送间隔结束(秒)")
    private Integer performerSepEnd;

    @ApiModelProperty(value = "单条消息发送失败后状态：1-继续推送 2-终止推送")
    private Integer sendErrorType;
}
