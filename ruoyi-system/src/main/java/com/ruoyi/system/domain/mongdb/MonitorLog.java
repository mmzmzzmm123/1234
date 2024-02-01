package com.ruoyi.system.domain.mongdb;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document(collection = "t_monitor_log")
@ApiModel("监控记录")
@Accessors(chain = true)
public class MonitorLog {
    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    @Indexed
    private String id;

    @ApiModelProperty("剧本ID")
    private String playId;

    @ApiModelProperty("会话Id")
    private Long chatId;

    @Excel(name = "触发时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("触发时间")
    private LocalDateTime localDateTime;

    @ApiModelProperty("群Id")
    private String groupId;

    @Excel(name = "群名称")
    @ApiModelProperty("群名称")
    private String groupName;

    @Excel(name = "群编号")
    @ApiModelProperty("群编号")
    private String groupSerialNo;

    @ApiModelProperty("消息Id")
    private String messageId;


    @ApiModelProperty("回复消息ID")
    private String replyMessageId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @Excel(name = "处理方式")
    @ApiModelProperty("处理方式")
    private String dealFunctionValues;

    @ApiModelProperty("处理方式")
    private String dealFunctionCodes;

    @Excel(name = "触发监控类型")
    @ApiModelProperty("拦截器类型")
    private String filterType;

    @Excel(name = "群成员名字")
    @ApiModelProperty("全名")
    private String fullName;

    @Excel(name = "用户名")
    @ApiModelProperty("用户名")
    private String userName;

    @Excel(name = "发言内容")
    @ApiModelProperty("文字消息内容")
    private String messageText;

}
