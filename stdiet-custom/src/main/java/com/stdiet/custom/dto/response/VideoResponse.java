package com.stdiet.custom.dto.response;

import com.stdiet.common.utils.AliyunVideoUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import lombok.Data;

import java.io.Serializable;

public class VideoResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    //阿里云中对应视频ID
    private String videoId;

    //视频标题
    private String title;

    //视频封面
    private String coverUrl;

    //播放次数
    private Integer playNum;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        if (StringUtils.isNotEmpty(this.coverUrl)) {
            return AliyunOSSUtils.generatePresignedUrl(this.coverUrl);
        } else {
            return AliyunVideoUtils.getVideoCoverUrl(this.videoId);
        }
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getPlayNum() {
        return playNum;
    }

    public void setPlayNum(Integer playNum) {
        this.playNum = playNum;
    }
}
