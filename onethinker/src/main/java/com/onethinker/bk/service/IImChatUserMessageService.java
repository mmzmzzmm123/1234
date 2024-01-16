package com.onethinker.bk.service;

import java.util.List;
import com.onethinker.bk.domain.ImChatUserMessage;

/**
 * 单聊记录Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IImChatUserMessageService {
    /**
     * 查询单聊记录
     *
     * @param id 单聊记录主键
     * @return 单聊记录
     */
    public ImChatUserMessage selectImChatUserMessageById(Long id);

    /**
     * 查询单聊记录列表
     *
     * @param imChatUserMessage 单聊记录
     * @return 单聊记录集合
     */
    public List<ImChatUserMessage> selectImChatUserMessageList(ImChatUserMessage imChatUserMessage);

    /**
     * 新增单聊记录
     *
     * @param imChatUserMessage 单聊记录
     * @return 结果
     */
    public int insertImChatUserMessage(ImChatUserMessage imChatUserMessage);

    /**
     * 修改单聊记录
     *
     * @param imChatUserMessage 单聊记录
     * @return 结果
     */
    public int updateImChatUserMessage(ImChatUserMessage imChatUserMessage);

    /**
     * 批量删除单聊记录
     *
     * @param ids 需要删除的单聊记录主键集合
     * @return 结果
     */
    public int deleteImChatUserMessageByIds(Long[] ids);

    /**
     * 删除单聊记录信息
     *
     * @param id 单聊记录主键
     * @return 结果
     */
    public int deleteImChatUserMessageById(Long id);
}
