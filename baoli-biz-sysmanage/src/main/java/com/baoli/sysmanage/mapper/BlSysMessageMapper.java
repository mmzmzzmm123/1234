package com.baoli.sysmanage.mapper;

import java.util.List;
import com.baoli.sysmanage.domain.BlSysMessage;

/**
 * 消息通知Mapper接口
 * 
 * @author niujs
 * @date 2024-04-10
 */
public interface BlSysMessageMapper 
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
     * 删除消息通知
     * 
     * @param id 消息通知主键
     * @return 结果
     */
    public int deleteBlSysMessageById(Long id);

    /**
     * 批量删除消息通知
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBlSysMessageByIds(Long[] ids);
}
