package com.onethinker.bk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.bk.domain.ImChatGroup;
import com.onethinker.bk.mapper.ImChatGroupMapper;
import com.onethinker.bk.service.IImChatGroupService;
import com.onethinker.common.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 聊天群Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ImChatGroupServiceImpl extends ServiceImpl<ImChatGroupMapper, ImChatGroup> implements IImChatGroupService {
    @Resource
    private ImChatGroupMapper imChatGroupMapper;

    /**
     * 查询聊天群
     *
     * @param id 聊天群主键
     * @return 聊天群
     */
    @Override
    public ImChatGroup selectImChatGroupById(Long id) {
        return imChatGroupMapper.selectImChatGroupById(id);
    }

    /**
     * 查询聊天群列表
     *
     * @param imChatGroup 聊天群
     * @return 聊天群
     */
    @Override
    public List<ImChatGroup> selectImChatGroupList(ImChatGroup imChatGroup) {
        return imChatGroupMapper.selectImChatGroupList(imChatGroup);
    }

    /**
     * 新增聊天群
     *
     * @param imChatGroup 聊天群
     * @return 结果
     */
    @Override
    public int insertImChatGroup(ImChatGroup imChatGroup) {
        imChatGroup.setCreateTime(DateUtils.getNowDate());
        return imChatGroupMapper.insertImChatGroup(imChatGroup);
    }

    /**
     * 修改聊天群
     *
     * @param imChatGroup 聊天群
     * @return 结果
     */
    @Override
    public int updateImChatGroup(ImChatGroup imChatGroup) {
        return imChatGroupMapper.updateImChatGroup(imChatGroup);
    }

    /**
     * 批量删除聊天群
     *
     * @param ids 需要删除的聊天群主键
     * @return 结果
     */
    @Override
    public int deleteImChatGroupByIds(Long[] ids) {
        return imChatGroupMapper.deleteImChatGroupByIds(ids);
    }

    /**
     * 删除聊天群信息
     *
     * @param id 聊天群主键
     * @return 结果
     */
    @Override
    public int deleteImChatGroupById(Long id) {
        return imChatGroupMapper.deleteImChatGroupById(id);
    }
}
