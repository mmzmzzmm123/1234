package com.ruoyi.system.bot.mode.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/18/018 8:53
 */
@Data
public class AdMonitorDTO {


    @ApiModelProperty("config id")
    private String configId;

    @ApiModelProperty(value = "monitor target", allowableValues = "1-photo,2-video,4-links")
    private List<Integer> monitorTarget;

    @ApiModelProperty(value = "keywords")
    private List<String> keywords;

    @ApiModelProperty(value = "frequency")
    private Integer monitorFrequency;

    @ApiModelProperty(value = "time")
    private Integer monitorTime;

    @ApiModelProperty(value = "DAYS, HOURS, MICROSECONDS, MILLISECONDS, MINUTES, NANOSECONDS, SECONDS")
    private String timeUnit;

    @ApiModelProperty(value = "deal function", allowableValues = "RESTRICT,KICK_OUT")
    private List<String> dealFunction;

    @ApiModelProperty(value = "is delete other statement")
    private Integer deleteOtherStatement;

    @ApiModelProperty(value = "is restrict other members")
    private Integer restrictMember;

    @ApiModelProperty(value = "apply groups")
    private List<String> groupIds;

}
