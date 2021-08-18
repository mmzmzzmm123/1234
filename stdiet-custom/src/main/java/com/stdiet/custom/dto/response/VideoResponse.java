package com.stdiet.custom.dto.response;

import com.stdiet.common.utils.AliyunVideoUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import lombok.Data;

import java.io.Serializable;

@Data
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

    // 播放等级
    private Integer playLevel;

    public void setCoverUrl(String coverUrl) {
        if (StringUtils.isNotEmpty(coverUrl)) {
            this.coverUrl = AliyunOSSUtils.generatePresignedUrl(coverUrl);
        } else {
            this.coverUrl = AliyunVideoUtils.getVideoCoverUrl(this.videoId);
        }
    }
}
