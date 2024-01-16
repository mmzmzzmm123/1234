package com.ruoyi.bk.service;

import java.util.List;
import com.ruoyi.bk.domain.ImChatUserFriend;

/**
 * 好友Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IImChatUserFriendService {
    /**
     * 查询好友
     *
     * @param id 好友主键
     * @return 好友
     */
    public ImChatUserFriend selectImChatUserFriendById(Long id);

    /**
     * 查询好友列表
     *
     * @param imChatUserFriend 好友
     * @return 好友集合
     */
    public List<ImChatUserFriend> selectImChatUserFriendList(ImChatUserFriend imChatUserFriend);

    /**
     * 新增好友
     *
     * @param imChatUserFriend 好友
     * @return 结果
     */
    public int insertImChatUserFriend(ImChatUserFriend imChatUserFriend);

    /**
     * 修改好友
     *
     * @param imChatUserFriend 好友
     * @return 结果
     */
    public int updateImChatUserFriend(ImChatUserFriend imChatUserFriend);

    /**
     * 批量删除好友
     *
     * @param ids 需要删除的好友主键集合
     * @return 结果
     */
    public int deleteImChatUserFriendByIds(Long[] ids);

    /**
     * 删除好友信息
     *
     * @param id 好友主键
     * @return 结果
     */
    public int deleteImChatUserFriendById(Long id);
}
