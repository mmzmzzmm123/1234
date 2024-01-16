package com.ruoyi.bk.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bk.mapper.ImChatGroupMapper;
import com.ruoyi.bk.domain.ImChatGroup;
import com.ruoyi.bk.service.IImChatGroupService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 聊天群Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ImChatGroupServiceImpl extends ServiceImpl<ImChatGroupMapper,ImChatGroup> implements IImChatGroupService {
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
