package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysAskNutritionQuestionMapper;
import com.stdiet.custom.domain.SysAskNutritionQuestion;
import com.stdiet.custom.service.ISysAskNutritionQuestionService;

/**
 * 营养小知识提问Service业务层处理
 *
 * @author xzj
 * @date 2021-04-23
 */
@Service
public class SysAskNutritionQuestionServiceImpl implements ISysAskNutritionQuestionService
{
    @Autowired
    private SysAskNutritionQuestionMapper sysAskNutritionQuestionMapper;

    /**
     * 查询营养小知识提问
     *
     * @param id 营养小知识提问ID
     * @return 营养小知识提问
     */
    @Override
    public SysAskNutritionQuestion selectSysAskNutritionQuestionById(Long id)
    {
        return sysAskNutritionQuestionMapper.selectSysAskNutritionQuestionById(id);
    }

    /**
     * 查询营养小知识提问列表
     *
     * @param sysAskNutritionQuestion 营养小知识提问
     * @return 营养小知识提问
     */
    @Override
    public List<SysAskNutritionQuestion> selectSysAskNutritionQuestionList(SysAskNutritionQuestion sysAskNutritionQuestion)
    {
        return sysAskNutritionQuestionMapper.selectSysAskNutritionQuestionList(sysAskNutritionQuestion);
    }

    /**
     * 新增营养小知识提问
     *
     * @param sysAskNutritionQuestion 营养小知识提问
     * @return 结果
     */
    @Override
    public int insertSysAskNutritionQuestion(SysAskNutritionQuestion sysAskNutritionQuestion)
    {
        sysAskNutritionQuestion.setCreateTime(DateUtils.getNowDate());
        return sysAskNutritionQuestionMapper.insertSysAskNutritionQuestion(sysAskNutritionQuestion);
    }

    /**
     * 修改营养小知识提问
     *
     * @param sysAskNutritionQuestion 营养小知识提问
     * @return 结果
     */
    @Override
    public int updateSysAskNutritionQuestion(SysAskNutritionQuestion sysAskNutritionQuestion)
    {
        sysAskNutritionQuestion.setUpdateTime(DateUtils.getNowDate());
        return sysAskNutritionQuestionMapper.updateSysAskNutritionQuestion(sysAskNutritionQuestion);
    }

    /**
     * 批量删除营养小知识提问
     *
     * @param ids 需要删除的营养小知识提问ID
     * @return 结果
     */
    @Override
    public int deleteSysAskNutritionQuestionByIds(Long[] ids)
    {
        return sysAskNutritionQuestionMapper.deleteSysAskNutritionQuestionByIds(ids);
    }

    /**
     * 删除营养小知识提问信息
     *
     * @param id 营养小知识提问ID
     * @return 结果
     */
    @Override
    public int deleteSysAskNutritionQuestionById(Long id)
    {
        return sysAskNutritionQuestionMapper.deleteSysAskNutritionQuestionById(id);
    }
}