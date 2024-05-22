package com.jjpt.business.service.impl;

import java.util.List;

import com.jjpt.business.service.IElPaperQuAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjpt.business.mapper.ElPaperQuAnswerMapper;
import com.jjpt.business.domain.ElPaperQuAnswer;


/**
 * 考试记录答案Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
@Service
public class ElPaperQuAnswerServiceImpl implements IElPaperQuAnswerService
{
    @Autowired
    private ElPaperQuAnswerMapper elPaperQuAnswerMapper;

    /**
     * 查询考试记录答案
     * 
     * @param id 考试记录答案主键
     * @return 考试记录答案
     */
    @Override
    public ElPaperQuAnswer selectElPaperQuAnswerById(String id)
    {
        return elPaperQuAnswerMapper.selectElPaperQuAnswerById(id);
    }

    /**
     * 查询考试记录答案列表
     * 
     * @param elPaperQuAnswer 考试记录答案
     * @return 考试记录答案
     */
    @Override
    public List<ElPaperQuAnswer> selectElPaperQuAnswerList(ElPaperQuAnswer elPaperQuAnswer)
    {
        return elPaperQuAnswerMapper.selectElPaperQuAnswerList(elPaperQuAnswer);
    }

    /**
     * 新增考试记录答案
     * 
     * @param elPaperQuAnswer 考试记录答案
     * @return 结果
     */
    @Override
    public int insertElPaperQuAnswer(ElPaperQuAnswer elPaperQuAnswer)
    {
        return elPaperQuAnswerMapper.insertElPaperQuAnswer(elPaperQuAnswer);
    }

    /**
     * 修改考试记录答案
     * 
     * @param elPaperQuAnswer 考试记录答案
     * @return 结果
     */
    @Override
    public int updateElPaperQuAnswer(ElPaperQuAnswer elPaperQuAnswer)
    {
        return elPaperQuAnswerMapper.updateElPaperQuAnswer(elPaperQuAnswer);
    }

    /**
     * 批量删除考试记录答案
     * 
     * @param ids 需要删除的考试记录答案主键
     * @return 结果
     */
    @Override
    public int deleteElPaperQuAnswerByIds(String[] ids)
    {
        return elPaperQuAnswerMapper.deleteElPaperQuAnswerByIds(ids);
    }

    /**
     * 删除考试记录答案信息
     * 
     * @param id 考试记录答案主键
     * @return 结果
     */
    @Override
    public int deleteElPaperQuAnswerById(String id)
    {
        return elPaperQuAnswerMapper.deleteElPaperQuAnswerById(id);
    }
}
