package com.ruoyi.bk.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bk.mapper.ImChatUserMessageMapper;
import com.ruoyi.bk.domain.ImChatUserMessage;
import com.ruoyi.bk.service.IImChatUserMessageService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 单聊记录Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ImChatUserMessageServiceImpl extends ServiceImpl<ImChatUserMessageMapper,ImChatUserMessage> implements IImChatUserMessageService {
    @Resource
    private ImChatUserMessageMapper imChatUserMessageMapper;

    /**
     * 查询单聊记录
     *
     * @param id 单聊记录主键
     * @return 单聊记录
     */
    @Override
    public ImChatUserMessage selectImChatUserMessageById(Long id) {
        return imChatUserMessageMapper.selectImChatUserMessageById(id);
    }

    /**
     * 查询单聊记录列表
     *
     * @param imChatUserMessage 单聊记录
     * @return 单聊记录
     */
    @Override
    public List<ImChatUserMessage> selectImChatUserMessageList(ImChatUserMessage imChatUserMessage) {
        return imChatUserMessageMapper.selectImChatUserMessageList(imChatUserMessage);
    }

    /**
     * 新增单聊记录
     *
     * @param imChatUserMessage 单聊记录
     * @return 结果
     */
    @Override
    public int insertImChatUserMessage(ImChatUserMessage imChatUserMessage) {
                imChatUserMessage.setCreateTime(DateUtils.getNowDate());
            return imChatUserMessageMapper.insertImChatUserMessage(imChatUserMessage);
    }

    /**
     * 修改单聊记录
     *
     * @param imChatUserMessage 单聊记录
     * @return 结果
     */
    @Override
    public int updateImChatUserMessage(ImChatUserMessage imChatUserMessage) {
        return imChatUserMessageMapper.updateImChatUserMessage(imChatUserMessage);
    }

    /**
     * 批量删除单聊记录
     *
     * @param ids 需要删除的单聊记录主键
     * @return 结果
     */
    @Override
    public int deleteImChatUserMessageByIds(Long[] ids) {
        return imChatUserMessageMapper.deleteImChatUserMessageByIds(ids);
    }

    /**
     * 删除单聊记录信息
     *
     * @param id 单聊记录主键
     * @return 结果
     */
    @Override
    public int deleteImChatUserMessageById(Long id) {
        return imChatUserMessageMapper.deleteImChatUserMessageById(id);
    }
}
