package com.ruoyi.bk.service;

import java.util.List;
import com.ruoyi.bk.domain.ImChatGroupUser;

/**
 * 聊天群成员Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IImChatGroupUserService {
    /**
     * 查询聊天群成员
     *
     * @param id 聊天群成员主键
     * @return 聊天群成员
     */
    public ImChatGroupUser selectImChatGroupUserById(Long id);

    /**
     * 查询聊天群成员列表
     *
     * @param imChatGroupUser 聊天群成员
     * @return 聊天群成员集合
     */
    public List<ImChatGroupUser> selectImChatGroupUserList(ImChatGroupUser imChatGroupUser);

    /**
     * 新增聊天群成员
     *
     * @param imChatGroupUser 聊天群成员
     * @return 结果
     */
    public int insertImChatGroupUser(ImChatGroupUser imChatGroupUser);

    /**
     * 修改聊天群成员
     *
     * @param imChatGroupUser 聊天群成员
     * @return 结果
     */
    public int updateImChatGroupUser(ImChatGroupUser imChatGroupUser);

    /**
     * 批量删除聊天群成员
     *
     * @param ids 需要删除的聊天群成员主键集合
     * @return 结果
     */
    public int deleteImChatGroupUserByIds(Long[] ids);

    /**
     * 删除聊天群成员信息
     *
     * @param id 聊天群成员主键
     * @return 结果
     */
    public int deleteImChatGroupUserById(Long id);
}
