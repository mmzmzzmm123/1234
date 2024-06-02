package com.baoli.sysmanage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BlSysMessageMapper;
import com.baoli.sysmanage.domain.BlSysMessage;
import com.baoli.sysmanage.service.IBlSysMessageService;

/**
 * 消息通知Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-10
 */
@Service
public class BlSysMessageServiceImpl implements IBlSysMessageService 
{
    @Autowired
    private BlSysMessageMapper blSysMessageMapper;

    /**
     * 查询消息通知
     * 
     * @param id 消息通知主键
     * @return 消息通知
     */
    @Override
    public BlSysMessage selectBlSysMessageById(Long id)
    {
        return blSysMessageMapper.selectBlSysMessageById(id);
    }

    /**
     * 查询消息通知列表
     * 
     * @param blSysMessage 消息通知
     * @return 消息通知
     */
    @Override
    public List<BlSysMessage> selectBlSysMessageList(BlSysMessage blSysMessage)
    {
        return blSysMessageMapper.selectBlSysMessageList(blSysMessage);
    }

    /**
     * 新增消息通知
     * 
     * @param blSysMessage 消息通知
     * @return 结果
     */
    @Override
    public int insertBlSysMessage(BlSysMessage blSysMessage)
    {
        return blSysMessageMapper.insertBlSysMessage(blSysMessage);
    }

    /**
     * 修改消息通知
     * 
     * @param blSysMessage 消息通知
     * @return 结果
     */
    @Override
    public int updateBlSysMessage(BlSysMessage blSysMessage)
    {
        return blSysMessageMapper.updateBlSysMessage(blSysMessage);
    }

    /**
     * 批量删除消息通知
     * 
     * @param ids 需要删除的消息通知主键
     * @return 结果
     */
    @Override
    public int deleteBlSysMessageByIds(Long[] ids)
    {
        return blSysMessageMapper.deleteBlSysMessageByIds(ids);
    }

    /**
     * 删除消息通知信息
     * 
     * @param id 消息通知主键
     * @return 结果
     */
    @Override
    public int deleteBlSysMessageById(Long id)
    {
        return blSysMessageMapper.deleteBlSysMessageById(id);
    }
}
