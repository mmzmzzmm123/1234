package com.baoli.sysmanage.service;

import java.util.List;
import com.baoli.sysmanage.domain.BlSysMessage;

/**
 * 消息通知Service接口
 * 
 * @author niujs
 * @date 2024-04-10
 */
public interface IBlSysMessageService 
{
    /**
     * 查询消息通知
     * 
     * @param id 消息通知主键
     * @return 消息通知
     */
    public BlSysMessage selectBlSysMessageById(Long id);

    /**
     * 查询消息通知列表
     * 
     * @param blSysMessage 消息通知
     * @return 消息通知集合
     */
    public List<BlSysMessage> selectBlSysMessageList(BlSysMessage blSysMessage);

    /**
     * 新增消息通知
     * 
     * @param blSysMessage 消息通知
     * @return 结果
     */
    public int insertBlSysMessage(BlSysMessage blSysMessage);

    /**
     * 修改消息通知
     * 
     * @param blSysMessage 消息通知
     * @return 结果
     */
    public int updateBlSysMessage(BlSysMessage blSysMessage);

    /**
     * 批量删除消息通知
     * 
     * @param ids 需要删除的消息通知主键集合
     * @return 结果
     */
    public int deleteBlSysMessageByIds(Long[] ids);

    /**
     * 删除消息通知信息
     * 
     * @param id 消息通知主键
     * @return 结果
     */
    public int deleteBlSysMessageById(Long id);
}
