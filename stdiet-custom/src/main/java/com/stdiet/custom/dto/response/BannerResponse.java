package com.stdiet.custom.dto.response;

import com.stdiet.common.annotation.Excel;
import lombok.Data;

@Data
public class BannerResponse {

    /**
     * banner图标题
     */
    @Excel(name = "banner图标题")
    private String title;

    /**
     * banner图内容
     */
    @Excel(name = "banner图内容")
    private String content;

    /**
     * banner图片地址
     */
    @Excel(name = "banner图片地址")
    private String bannerUrl;

    /**
     * 点击跳转地址
     */
    @Excel(name = "点击跳转地址")
    private String jumpUrl;
}
