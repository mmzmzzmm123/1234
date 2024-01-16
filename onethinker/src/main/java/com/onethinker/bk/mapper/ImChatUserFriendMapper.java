package com.onethinker.bk.mapper;

import java.util.List;
import com.onethinker.bk.domain.ImChatUserFriend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 好友Mapper接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface ImChatUserFriendMapper extends BaseMapper<ImChatUserFriend>{
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
     * 删除好友
     *
     * @param id 好友主键
     * @return 结果
     */
    public int deleteImChatUserFriendById(Long id);

    /**
     * 批量删除好友
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImChatUserFriendByIds(Long[] ids);
}
