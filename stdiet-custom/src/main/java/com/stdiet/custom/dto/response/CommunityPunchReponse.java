package com.stdiet.custom.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 打卡社区
 */
@Data
public class CommunityPunchReponse {

    //加密ID
    private String id;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 打卡日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date logTime;

    /**
     * 营养师评论
     */
    private String comment;

    /**
     * 点赞数
     */
    private Integer thumbsupNum;

    /**
     * 健康宣言
     */
    private String healthManifesto;

    /**
     * 近期打卡体重信息  logTime: 打卡日期  weight: 体重
     */
    private List<Map<String,Object>> weightData;

    /**
     * 点赞的openid
     */
    private List<Map<String,Object>> thumbsupUser;
}
