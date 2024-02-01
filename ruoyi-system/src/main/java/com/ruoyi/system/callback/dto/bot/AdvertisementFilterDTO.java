package com.ruoyi.system.callback.dto.bot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/24/024 15:16
 * @Description :
 */
@Data
public class AdvertisementFilterDTO {

    @ApiModelProperty("会话Id")
    private Long chatId;

    @ApiModelProperty("消息Id")
    private String messageId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("全名")
    private String fullName;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("拦截器类型")
    private String filterType;

    @ApiModelProperty("处理方式")
    private List<String> dealFunctions;

    @ApiModelProperty("文字消息内容")
    private String messageText;

    @ApiModelProperty("回复消息ID")
    private String replyMessageId;

}
