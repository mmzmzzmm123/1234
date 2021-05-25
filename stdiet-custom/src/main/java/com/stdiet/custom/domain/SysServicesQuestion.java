package com.stdiet.custom.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SysServicesQuestion {
    /**
     * 问题状态id
     */
    Long id;

    /**
     * 问题id(搜索用)
     */
    String queId;

    /**
     * 客户id
     */
    Long cusId;

    /**
     * 问题内容
     */
    String content;


    /**
     * 图片地址
     */
    JSONArray img;

    /**
     * 问题类型
     */
    Integer type;

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

    // 非持久化字段
    /**
     * 角色名字
     */
    String name;

    /**
     * 角色id
     */
    Long userId;

    SysServicesQuestionUserInfo userInfo;

}
