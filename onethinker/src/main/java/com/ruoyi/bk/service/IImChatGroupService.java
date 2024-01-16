package com.ruoyi.bk.service;

import java.util.List;
import com.ruoyi.bk.domain.ImChatGroup;

/**
 * 聊天群Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IImChatGroupService {
    /**
     * 查询聊天群
     *
     * @param id 聊天群主键
     * @return 聊天群
     */
    public ImChatGroup selectImChatGroupById(Long id);

    /**
     * 查询聊天群列表
     *
     * @param imChatGroup 聊天群
     * @return 聊天群集合
     */
    public List<ImChatGroup> selectImChatGroupList(ImChatGroup imChatGroup);

    /**
     * 新增聊天群
     *
     * @param imChatGroup 聊天群
     * @return 结果
     */
    public int insertImChatGroup(ImChatGroup imChatGroup);

    /**
     * 修改聊天群
     *
     * @param imChatGroup 聊天群
     * @return 结果
     */
    public int updateImChatGroup(ImChatGroup imChatGroup);

    /**
     * 批量删除聊天群
     *
     * @param ids 需要删除的聊天群主键集合
     * @return 结果
     */
    public int deleteImChatGroupByIds(Long[] ids);

    /**
     * 删除聊天群信息
     *
     * @param id 聊天群主键
     * @return 结果
     */
    public int deleteImChatGroupById(Long id);
}
