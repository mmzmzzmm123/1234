package com.ruoyi.push.service;

import com.ruoyi.push.domain.vo.CommandVo;

import java.util.List;

/**
 * @Author lixin
 * @Date 2022/12/29 14:39
 */
public interface IPushService {
    /**
     * 拉取消息
     * @param groupName 消息组 默认U+指定用户id，全局是G
     * @param lastTime 如果为0 就拉取10s内的消息
     * @return
     */
    public List<CommandVo> pull(String groupName,long lastTime);

    /**
     * 清除过期
     */
    public void cleanExpirationAll();

    /**
     * 添加消息
     * @param groupName 消息组 默认U+指定用户id，全局是G 详细请看 CommandConst.GROUP_NAME_XXX
     * @param vo
     */
    public void addCommand(String groupName,CommandVo vo);

    /**
     * 拉取自己的消息
     * @param lastTime
     * @param lastTime 如果为0 就拉取10s内的消息
     * @return
     */
    public List<CommandVo> pullSelf(long userId, long lastTime);

    /**
     * 添加用户拉取站内消息
     * @param userId
     * @param content
     */
    public void addPullSitemsg(long userId,Object content);

    /**
     * 获用户取站内信未书数量
     * @param userId
     */
    public void addPullSitecount(long userId);
    /**
     * 添加全局消息
     * @param content
     */
    public void addGlobalNotice(Object content);

}
