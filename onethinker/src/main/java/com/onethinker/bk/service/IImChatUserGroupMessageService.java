package com.onethinker.bk.service;

import java.util.List;
import com.onethinker.bk.domain.ImChatUserGroupMessage;

/**
 * 群聊记录Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IImChatUserGroupMessageService {
    /**
     * 查询群聊记录
     *
     * @param id 群聊记录主键
     * @return 群聊记录
     */
    public ImChatUserGroupMessage selectImChatUserGroupMessageById(Long id);

    /**
     * 查询群聊记录列表
     *
     * @param imChatUserGroupMessage 群聊记录
     * @return 群聊记录集合
     */
    public List<ImChatUserGroupMessage> selectImChatUserGroupMessageList(ImChatUserGroupMessage imChatUserGroupMessage);

    /**
     * 新增群聊记录
     *
     * @param imChatUserGroupMessage 群聊记录
     * @return 结果
     */
    public int insertImChatUserGroupMessage(ImChatUserGroupMessage imChatUserGroupMessage);

    /**
     * 修改群聊记录
     *
     * @param imChatUserGroupMessage 群聊记录
     * @return 结果
     */
    public int updateImChatUserGroupMessage(ImChatUserGroupMessage imChatUserGroupMessage);

    /**
     * 批量删除群聊记录
     *
     * @param ids 需要删除的群聊记录主键集合
     * @return 结果
     */
    public int deleteImChatUserGroupMessageByIds(Long[] ids);

    /**
     * 删除群聊记录信息
     *
     * @param id 群聊记录主键
     * @return 结果
     */
    public int deleteImChatUserGroupMessageById(Long id);
}
