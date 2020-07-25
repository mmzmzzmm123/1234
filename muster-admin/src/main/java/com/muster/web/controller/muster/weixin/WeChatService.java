package com.muster.web.controller.muster.weixin;

/**
 * Description
 * <p>
 * </p>
 * DATE 2020-07-08.
 *
 * @author 刘江涛.
 */
public interface WeChatService {

    int query(final String openId);

    boolean login(final WeiXinUserInfo userInfo);

    boolean login(final WeiXinUserInfo userInfo, String appId, String bizId);

}
