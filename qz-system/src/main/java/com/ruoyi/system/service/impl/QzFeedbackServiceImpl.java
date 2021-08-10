package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.QzFeedbackMapper;
import com.ruoyi.system.domain.QzFeedback;
import com.ruoyi.system.service.IQzFeedbackService;

/**
 * 意见反馈Service业务层处理
 * 
 * @author zlx
 * @date 2021-08-10
 */
@Service
public class QzFeedbackServiceImpl implements IQzFeedbackService 
{
    @Autowired
    private QzFeedbackMapper qzFeedbackMapper;

    /**
     * 查询意见反馈
     * 
     * @param id 意见反馈主键
     * @return 意见反馈
     */
    @Override
    public QzFeedback selectQzFeedbackById(Long id)
    {
        return qzFeedbackMapper.selectQzFeedbackById(id);
    }

    /**
     * 查询意见反馈列表
     * 
     * @param qzFeedback 意见反馈
     * @return 意见反馈
     */
    @Override
    public List<QzFeedback> selectQzFeedbackList(QzFeedback qzFeedback)
    {
        return qzFeedbackMapper.selectQzFeedbackList(qzFeedback);
    }

    /**
     * 新增意见反馈
     * 
     * @param qzFeedback 意见反馈
     * @return 结果
     */
    @Override
    public int insertQzFeedback(QzFeedback qzFeedback)
    {
        qzFeedback.setCreateTime(DateUtils.getNowDate());
        return qzFeedbackMapper.insertQzFeedback(qzFeedback);
    }

    /**
     * 修改意见反馈
     * 
     * @param qzFeedback 意见反馈
     * @return 结果
     */
    @Override
    public int updateQzFeedback(QzFeedback qzFeedback)
    {
        qzFeedback.setUpdateTime(DateUtils.getNowDate());
        return qzFeedbackMapper.updateQzFeedback(qzFeedback);
    }

    /**
     * 批量删除意见反馈
     * 
     * @param ids 需要删除的意见反馈主键
     * @return 结果
     */
    @Override
    public int deleteQzFeedbackByIds(Long[] ids)
    {
        return qzFeedbackMapper.deleteQzFeedbackByIds(ids);
    }

    /**
     * 删除意见反馈信息
     * 
     * @param id 意见反馈主键
     * @return 结果
     */
    @Override
    public int deleteQzFeedbackById(Long id)
    {
        return qzFeedbackMapper.deleteQzFeedbackById(id);
    }
}
