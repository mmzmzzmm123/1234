package com.ruoyi.onethinker.service;

import java.util.List;
import com.ruoyi.onethinker.domain.SubscribeMsgCtrl;

/**
 * 订阅消息控制Service接口
 *
 * @author yangyouqi
 * @date 2024-01-15
 */
public interface ISubscribeMsgCtrlService {
    /**
     * 查询订阅消息控制
     *
     * @param id 订阅消息控制主键
     * @return 订阅消息控制
     */
    public SubscribeMsgCtrl selectSubscribeMsgCtrlById(Long id);

    /**
     * 查询订阅消息控制列表
     *
     * @param subscribeMsgCtrl 订阅消息控制
     * @return 订阅消息控制集合
     */
    public List<SubscribeMsgCtrl> selectSubscribeMsgCtrlList(SubscribeMsgCtrl subscribeMsgCtrl);

    /**
     * 新增订阅消息控制
     *
     * @param subscribeMsgCtrl 订阅消息控制
     * @return 结果
     */
    public int insertSubscribeMsgCtrl(SubscribeMsgCtrl subscribeMsgCtrl);

    /**
     * 修改订阅消息控制
     *
     * @param subscribeMsgCtrl 订阅消息控制
     * @return 结果
     */
    public int updateSubscribeMsgCtrl(SubscribeMsgCtrl subscribeMsgCtrl);

    /**
     * 批量删除订阅消息控制
     *
     * @param ids 需要删除的订阅消息控制主键集合
     * @return 结果
     */
    public int deleteSubscribeMsgCtrlByIds(Long[] ids);

    /**
     * 删除订阅消息控制信息
     *
     * @param id 订阅消息控制主键
     * @return 结果
     */
    public int deleteSubscribeMsgCtrlById(Long id);

    /**
     * 通过模版id获取模版信息
     * @param templateId
     * @return
     */
    SubscribeMsgCtrl findEntryByTemplateId(String templateId);
}
