package com.ruoyi.bookmark.service;

import com.ruoyi.common.core.domain.AjaxResult;

/**
 * @Auther: Wang
 * @Date: 2021/10/29 17:36
 * 功能描述:
 */
public interface ISqUserServise {
    /**
     *
     *  获取当前用户 首页的信息展示 redis
     *
     */
    AjaxResult getPersonalMessage(Long userId);
}
