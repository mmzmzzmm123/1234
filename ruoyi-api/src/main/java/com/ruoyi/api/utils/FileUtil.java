package com.ruoyi.api.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * author: 周周
 */
@Slf4j
public class FileUtil {
    public static String interceptUrl(String url){
        int indexOf = url.indexOf("2");
        return url.substring(indexOf);
    }

}