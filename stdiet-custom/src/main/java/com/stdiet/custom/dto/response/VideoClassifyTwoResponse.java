package com.stdiet.custom.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class VideoClassifyTwoResponse  implements Serializable {

    private static final long serialVersionUID = 1L;

    //目录分类ID
    private Long id;

    //目录分类名称
    private String cateName;

    //子分类
    private List<VideoClassifyThreeResponse> childrenClassify;

    //子视频
    private List<Map<String, Object>> childrenVideo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<VideoClassifyThreeResponse> getChildrenClassify() {
        return childrenClassify;
    }

    public void setChildrenClassify(List<VideoClassifyThreeResponse> childrenClassify) {
        this.childrenClassify = childrenClassify;
    }

    public List<Map<String, Object>> getChildrenVideo() {
        return childrenVideo;
    }

    public void setChildrenVideo(List<Map<String, Object>> childrenVideo) {
        this.childrenVideo = childrenVideo;
    }
}
