package com.ruoyi.web;

import lombok.Data;

import java.util.Map;

/**
 * 请求参数封装
 */
@Data
public class RequestParams {
    private String key;

    //请求参数
    private Map<String, Object> params;

    private  int  pageNum;

    private  int pageSize;

    private  String title;
}
