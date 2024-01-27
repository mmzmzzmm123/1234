package com.ruoyi.system.domain.mongdb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Sharly
 */
@Data
@Document("open_api_request_log")
public class OpenApiRequestLog {

    @Id
    private String id;

    private String chatRoomId;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 响应参数
     */
    private String responseParams;

    /**
     * 回调响应参数
     */
    private String callbackParams;

    /**
     * 重试目标Id
     */
    @Indexed
    private String lastTimeRequestId;

    private String errorMessage;

    /**
     * 请求次数
     */
    private Integer requestTimes;

    /**
     * 请求创建时间
     */
    private LocalDateTime createTime;

}
