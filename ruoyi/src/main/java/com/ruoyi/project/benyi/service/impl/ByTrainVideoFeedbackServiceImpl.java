package com.ruoyi.project.benyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByTrainVideoFeedbackMapper;
import com.ruoyi.project.benyi.domain.ByTrainVideoFeedback;
import com.ruoyi.project.benyi.service.IByTrainVideoFeedbackService;

/**
 * 培训视频评价反馈Service业务层处理
 *
 * @author tsbz
 * @date 2020-06-01
 */
@Service
public class ByTrainVideoFeedbackServiceImpl implements IByTrainVideoFeedbackService
{
    @Autowired
    private ByTrainVideoFeedbackMapper byTrainVideoFeedbackMapper;

    /**
     * 查询培训视频评价反馈
     *
     * @param id 培训视频评价反馈ID
     * @return 培训视频评价反馈
     */
    @Override
    public ByTrainVideoFeedback selectByTrainVideoFeedbackById(Long id)
    {
        return byTrainVideoFeedbackMapper.selectByTrainVideoFeedbackById(id);
    }

    /**
     * 查询培训视频评价反馈列表
     *
     * @param byTrainVideoFeedback 培训视频评价反馈
     * @return 培训视频评价反馈
     */
    @Override
    public List<ByTrainVideoFeedback> selectByTrainVideoFeedbackList(ByTrainVideoFeedback byTrainVideoFeedback)
    {
        return byTrainVideoFeedbackMapper.selectByTrainVideoFeedbackList(byTrainVideoFeedback);
    }

    /**
     * 新增培训视频评价反馈
     *
     * @param byTrainVideoFeedback 培训视频评价反馈
     * @return 结果
     */
    @Override
    public int insertByTrainVideoFeedback(ByTrainVideoFeedback byTrainVideoFeedback)
    {
        return byTrainVideoFeedbackMapper.insertByTrainVideoFeedback(byTrainVideoFeedback);
    }

    /**
     * 修改培训视频评价反馈
     *
     * @param byTrainVideoFeedback 培训视频评价反馈
     * @return 结果
     */
    @Override
    public int updateByTrainVideoFeedback(ByTrainVideoFeedback byTrainVideoFeedback)
    {
        return byTrainVideoFeedbackMapper.updateByTrainVideoFeedback(byTrainVideoFeedback);
    }

    /**
     * 批量删除培训视频评价反馈
     *
     * @param ids 需要删除的培训视频评价反馈ID
     * @return 结果
     */
    @Override
    public int deleteByTrainVideoFeedbackByIds(Long[] ids)
    {
        return byTrainVideoFeedbackMapper.deleteByTrainVideoFeedbackByIds(ids);
    }

    /**
     * 删除培训视频评价反馈信息
     *
     * @param id 培训视频评价反馈ID
     * @return 结果
     */
    @Override
    public int deleteByTrainVideoFeedbackById(Long id)
    {
        return byTrainVideoFeedbackMapper.deleteByTrainVideoFeedbackById(id);
    }
}
