package com.ruoyi.push.consts;

import com.ruoyi.common.constant.CacheConstants;

/**
 * @Author lixin
 * @Date 2022/12/29 14:35
 */
public class CommandConst {
    /**
     * 缓存时间（秒）
     */
    public static final int MSG_CACHE_TIME = 30;
    /**
     * 拉取站内消息
     */
    public static final String CMD_PULL_SITEMSG = "PULL_SITEMSG";
    /**
     * 拉取站信未读数量
     */
    public static final String CMD_PULL_SITECOUNT = "PULL_SITECOUNT";

    /**
     * 全局通知
     */
    public static final String CMD_GLOBAL_NOTICE = "GLOBAL_NOTICE";

    /**
     * 拉取最后时间
     */
    public static final String CMD_PULL_LAST_TIME = "PULL_LAST_TIME";

    /**
     * 消息组：全部
     */
    public static final String GROUP_NAME_G = CacheConstants.USER_PUSH_MSG + "G";

    /**
     * 消息组：用户
     */
    public static final String GROUP_NAME_U = CacheConstants.USER_PUSH_MSG + "U" + CacheConstants.SEPARATE;

}
