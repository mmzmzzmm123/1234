package com.stdiet.custom.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    //阿里云中对应视频ID
    private String videoId;

    //视频标题
    private String title;
}
