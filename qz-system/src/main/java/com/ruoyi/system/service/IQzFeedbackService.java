package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.QzFeedback;

/**
 * 意见反馈Service接口
 * 
 * @author zlx
 * @date 2021-08-10
 */
public interface IQzFeedbackService 
{
    /**
     * 查询意见反馈
     * 
     * @param id 意见反馈主键
     * @return 意见反馈
     */
    public QzFeedback selectQzFeedbackById(Long id);

    /**
     * 查询意见反馈列表
     * 
     * @param qzFeedback 意见反馈
     * @return 意见反馈集合
     */
    public List<QzFeedback> selectQzFeedbackList(QzFeedback qzFeedback);

    /**
     * 新增意见反馈
     * 
     * @param qzFeedback 意见反馈
     * @return 结果
     */
    public int insertQzFeedback(QzFeedback qzFeedback);

    /**
     * 修改意见反馈
     * 
     * @param qzFeedback 意见反馈
     * @return 结果
     */
    public int updateQzFeedback(QzFeedback qzFeedback);

    /**
     * 批量删除意见反馈
     * 
     * @param ids 需要删除的意见反馈主键集合
     * @return 结果
     */
    public int deleteQzFeedbackByIds(Long[] ids);

    /**
     * 删除意见反馈信息
     * 
     * @param id 意见反馈主键
     * @return 结果
     */
    public int deleteQzFeedbackById(Long id);
}
