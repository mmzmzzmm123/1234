package com.onethinker.bk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.bk.domain.ImChatUserGroupMessage;
import com.onethinker.bk.mapper.ImChatUserGroupMessageMapper;
import com.onethinker.bk.service.IImChatUserGroupMessageService;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 群聊记录Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ImChatUserGroupMessageServiceImpl extends ServiceImpl<ImChatUserGroupMessageMapper, ImChatUserGroupMessage> implements IImChatUserGroupMessageService {
    @Resource
    private ImChatUserGroupMessageMapper imChatUserGroupMessageMapper;

    /**
     * 查询群聊记录
     *
     * @param id 群聊记录主键
     * @return 群聊记录
     */
    @Override
    public ImChatUserGroupMessage selectImChatUserGroupMessageById(Long id) {
        return imChatUserGroupMessageMapper.selectImChatUserGroupMessageById(id);
    }

    /**
     * 查询群聊记录列表
     *
     * @param imChatUserGroupMessage 群聊记录
     * @return 群聊记录
     */
    @Override
    public List<ImChatUserGroupMessage> selectImChatUserGroupMessageList(ImChatUserGroupMessage imChatUserGroupMessage) {
        return imChatUserGroupMessageMapper.selectImChatUserGroupMessageList(imChatUserGroupMessage);
    }

    /**
     * 新增群聊记录
     *
     * @param imChatUserGroupMessage 群聊记录
     * @return 结果
     */
    @Override
    public int insertImChatUserGroupMessage(ImChatUserGroupMessage imChatUserGroupMessage) {
        imChatUserGroupMessage.setCreateTime(DateUtils.getNowDate());
        return imChatUserGroupMessageMapper.insertImChatUserGroupMessage(imChatUserGroupMessage);
    }

    /**
     * 修改群聊记录
     *
     * @param imChatUserGroupMessage 群聊记录
     * @return 结果
     */
    @Override
    public int updateImChatUserGroupMessage(ImChatUserGroupMessage imChatUserGroupMessage) {
        return imChatUserGroupMessageMapper.updateImChatUserGroupMessage(imChatUserGroupMessage);
    }

    /**
     * 批量删除群聊记录
     *
     * @param ids 需要删除的群聊记录主键
     * @return 结果
     */
    @Override
    public int deleteImChatUserGroupMessageByIds(Long[] ids) {
        return imChatUserGroupMessageMapper.deleteImChatUserGroupMessageByIds(ids);
    }

    /**
     * 删除群聊记录信息
     *
     * @param id 群聊记录主键
     * @return 结果
     */
    @Override
    public int deleteImChatUserGroupMessageById(Long id) {
        return imChatUserGroupMessageMapper.deleteImChatUserGroupMessageById(id);
    }
}
