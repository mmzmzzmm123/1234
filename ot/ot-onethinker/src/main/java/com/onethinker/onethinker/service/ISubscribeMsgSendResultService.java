package com.onethinker.onethinker.service;

import com.onethinker.onethinker.domain.SubscribeMsgSendResult;

import java.util.List;

/**
 * 订阅消息发送结果Service接口
 *
 * @author yangyouqi
 * @date 2024-01-15
 */
public interface ISubscribeMsgSendResultService {
    /**
     * 查询订阅消息发送结果
     *
     * @param id 订阅消息发送结果主键
     * @return 订阅消息发送结果
     */
    public SubscribeMsgSendResult selectSubscribeMsgSendResultById(Long id);

    /**
     * 查询订阅消息发送结果列表
     *
     * @param subscribeMsgSendResult 订阅消息发送结果
     * @return 订阅消息发送结果集合
     */
    public List<SubscribeMsgSendResult> selectSubscribeMsgSendResultList(SubscribeMsgSendResult subscribeMsgSendResult);

    /**
     * 新增订阅消息发送结果
     *
     * @param subscribeMsgSendResult 订阅消息发送结果
     * @return 结果
     */
    public int insertSubscribeMsgSendResult(SubscribeMsgSendResult subscribeMsgSendResult);

    /**
     * 修改订阅消息发送结果
     *
     * @param subscribeMsgSendResult 订阅消息发送结果
     * @return 结果
     */
    public int updateSubscribeMsgSendResult(SubscribeMsgSendResult subscribeMsgSendResult);

    /**
     * 批量删除订阅消息发送结果
     *
     * @param ids 需要删除的订阅消息发送结果主键集合
     * @return 结果
     */
    public int deleteSubscribeMsgSendResultByIds(Long[] ids);

    /**
     * 删除订阅消息发送结果信息
     *
     * @param id 订阅消息发送结果主键
     * @return 结果
     */
    public int deleteSubscribeMsgSendResultById(Long id);
}
