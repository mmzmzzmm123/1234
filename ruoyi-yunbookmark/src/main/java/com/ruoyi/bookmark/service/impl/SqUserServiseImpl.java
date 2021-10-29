package com.ruoyi.bookmark.service.impl;

import com.ruoyi.bookmark.service.ISqTagService;
import com.ruoyi.bookmark.service.ISqUserServise;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisUtil;
import com.ruoyi.common.utils.bookmarkhtml.Const;
import com.ruoyi.common.utils.redis.KeyAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Wang
 * @Date: 2021/10/29 17:36
 * 功能描述:
 */
@Service
public class SqUserServiseImpl implements ISqUserServise {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISqTagService sqTagService;


    @Override
    public AjaxResult getPersonalMessage(Long userId) {

        String key = KeyAll.PERSONAL_MESSAGE + userId.toString();
        if (!redisUtil.hasKey(key)){
            //数据不存在 新用户 进行初始化数据
            Map<Object, Object> mapPersonalMessage = new HashMap<>();
            //TAG 标签总数量
//            mapPersonalMessage.put("tagTotal",sqTagService.countByuserId(userId));
        }
        return AjaxResult.success(redisUtil.hGetAll(key));
    }




}
