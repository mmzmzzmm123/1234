package com.ruoyi.baidu.servise.impl;

import cn.hutool.http.HttpUtil;
import com.ruoyi.baidu.servise.BaiduWangPanServise;
import com.ruoyi.common.constant.BaiduUrl;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Auther: Wang
 * @Date: 2021/07/24 22:34
 * 功能描述: 百度网盘操作
 */
@Service
public class BaiduWangPanServiseImpl implements BaiduWangPanServise {


    @Override
    public String getUserInfo(String access_token) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", access_token);
        paramMap.put("method", "uinfo");
        return HttpUtil.get(BaiduUrl.USER, paramMap);
    }

    @Override
    public String getCapacity(String access_token) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", access_token);
        paramMap.put("checkfree", 1);
        paramMap.put("checkexpire", 1);
        return HttpUtil.get(BaiduUrl.CAPACITY, paramMap);
    }

    @Override
    public String getFileList(String access_token) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", access_token);
        paramMap.put("method", "list");
        return HttpUtil.get(BaiduUrl.FILE_LIST, paramMap);
    }

    @Override
    public String getFileRecursionList(String access_token,String filePath) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", access_token);
        paramMap.put("path", filePath);
        return HttpUtil.get(BaiduUrl.RECURSION, paramMap);
    }

    @Override
    public String getSouSuoFileList(String access_token, String fileName) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", access_token);
        paramMap.put("method", "search");
        paramMap.put("key", "imageAi");
        return HttpUtil.get(BaiduUrl.FILE_NAME, paramMap);
    }

    @Override
    public String getfilemetas(String access_token, String fileId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", access_token);
        paramMap.put("method", "filemetas");
        paramMap.put("fsids", fileId);
        return HttpUtil.get(BaiduUrl.FILE_NAME, paramMap);
    }


}
