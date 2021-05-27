package com.stdiet.custom.domain;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysServicesTopic {
    /**
     *
     */
    String id;

    /**
     * 问题id
     */
    String topicId;

    /**
     * 问题类型
     */
    Integer topicType;

    String commentId;

    String replyId;

    Integer replyType;

    /**
     * 角色
     */
    Long uid;

    Long fromUid;

    Long toUid;

    /**
     * 问题内容
     */
    String content;

    /**
     * 图片地址
     */
    JSONArray img;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    /**
     * 0-未读 1-已读
     */
    Integer read;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date updateTime;

    /**
     * 角色
     */
    String role;

    String fromRole;

    String toRole;

    // 非持久化字段
    /**
     * 角色名字
     */
    String fromName;
    String toName;
    String name;

    List<SysServicesTopic> comments;

    List<SysServicesTopic> replys;

}
