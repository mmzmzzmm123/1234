package com.stdiet.custom.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class VideoClassifyResponse {

    //目录分类ID
    private Long id;

    //目录分类名称
    private String cateName;

    //用户类型 0 普通用户 1付费客户
    private Integer userType;

    private String imgUrl;

    //子分类
    private List<VideoClassifyResponse> childrenClassify;

    //子视频
    private List<VideoResponse> childrenVideo;
}
