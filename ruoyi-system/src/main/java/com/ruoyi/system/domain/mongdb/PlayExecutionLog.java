package com.ruoyi.system.domain.mongdb;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/13/013 14:41
 * @Description : 剧本执行日志
 */
@Data
@Document(collection = "t_play_execution_log")
@ApiModel("剧本执行日志")
public class PlayExecutionLog {
    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    @Indexed
    private String id;

    /**
     * 剧本ID
     */
    @ApiModelProperty("剧本ID")
    private String playId;


    /**
     * 执行内容
     */
    @ApiModelProperty("执行内容")
    private String content;


    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
}
