package com.ruoyi.common.enums;

public enum AppConfKey implements ConfKey {

    ALIYUN_CDN_IMAGES("aliyun.cdn.image", "http://imagecdn.yesoulchina.com/"),
    ALIYUN_CDN_VIDEO("aliyun.cdn.video","http://video2cdn.yesoulchina.com/"),
    ALIYUN_OSS_ENDPOINT("aliyun.oss.endpoint", "http://oss-cn-shanghai.aliyuncs.com"),
    ALIYUN_OSS_BUCKET_IMG_NAME("aliyun.oss.bucket.img.name", "image-yesoulchina-com"),
    ALIYUN_OSS_BUCKET_VIDEO_NAME("aliyun.oss.bucket.video.name", "video-yesoulchina-com"),
    ALIYUN_OSS_BUCKET_AUDIO_NAME("aliyun.oss.bucket.audio.name", "video-yesoulchina-com"),

    ALIYUN_OSS_ACCESS_KEY_ID("aliyun.oss.access.key.id", "LTAIvKtJWk3zZ3kI"),
    ALIYUN_OSS_ACCESS_KEY_SECRET("aliyun.oss.access.key.secret",
            "ONWTotCTp9y8MIkRjgNLx2QiL375T5"),

    ALIYUN_OSS_DIR("aliyun.oss.dir.ms", "yesoulhome"),

    PLAN_ACTIVITY_HOST("plan.activity.host", "http://www.yesoulchina.cn/"),

    LIMITED_RESOURCE_ACCESS_CONTROL("limited.resource.access.control", "top"),
    ;

    AppConfKey(String key, String defaultKey) {
        this.key = key;
        this.defaultKey = defaultKey;
    }

    private String key;
    private String defaultKey;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultKey;
    }
}

