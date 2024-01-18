package com.onethinker.bk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onethinker.bk.domain.ImChatGroup;

import java.util.List;

/**
 * 聊天群Mapper接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface ImChatGroupMapper extends BaseMapper<ImChatGroup> {
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
     * 删除聊天群
     *
     * @param id 聊天群主键
     * @return 结果
     */
    public int deleteImChatGroupById(Long id);

    /**
     * 批量删除聊天群
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteImChatGroupByIds(Long[] ids);
}
