package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Title: HeFeiCompanyAdvice
 * @Description:
 * @author: zdw
 * @date: 2022/3/29 23:57
 */
@Slf4j
@Data
public class HeFeiCompanyAdvice {

    //唯一ID
    private String id;

    //创建人ID
    private int creator;

    //标题
    private String title;


    //冗余标题，
    private String seccondTitle;

    //标签
    private String tag;


    //描述
    private String description;

    //评论数量
    private int commentCount;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    //评论，对应数据库中的映射

    private String comment;


    //评论，对应ES中的映射
    private List<String> commentList;


    //请求参数
    private Map<String, Object> params;

    private  int  pageNum;

    private  int pageSize;


}


/**
 * 评论
 */
class Comment {
    //评论时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime commentTime;

    //评论内容
    private String content;


}