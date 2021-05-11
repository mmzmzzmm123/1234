package com.stdiet.custom.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class NutritionalVideoResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    //分类ID
    public Long cateId;

    //分类名称
    public String cateName;

    //标题
    public String title;

    //标签
    public String tags;

    //视频ID
    public String videoId;

    //封面URL
    public String coverUrl;

    //视频描述
    public String description;

    //播放链接
    public String playUrl;

    //播放量
    private Integer playNum;

    public String createTime;

}
