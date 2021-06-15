package com.stdiet.custom.dto.response;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class VideoClassifyResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    //目录分类ID
    private Long id;

    //目录分类名称
    private String cateName;

    //用户类型 0 普通用户 1付费客户
    private Integer userType;

    //子分类
    private List<VideoClassifyTwoResponse> childrenClassify;

    //子视频
    private List<VideoResponse> childrenVideo;
}
