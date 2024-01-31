package com.ruoyi.system.domain.mongdb;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Sharly
 */
@Data
@Document("auto_reply_log")
@Accessors(chain = true)
public class AutoReplyLog {

    @Id
    private String id;

    @Indexed
    private String playId;

    @Indexed
    private String groupId;

    private String robotId;

    private String friendId;

    private String requestParams;

    private Integer requestTimes;

    private LocalDateTime createTime;

    @Indexed
    private String firstRequestId;

    private String failMessage;

    private boolean isSuccess;
}
