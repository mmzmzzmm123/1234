package com.stdiet.custom.domain.wechat;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxFileUploadResult implements Serializable {

    @JSONField(name = "media_id")
    private String mediaId;

    @JSONField(name = "url")
    private String url;
}
