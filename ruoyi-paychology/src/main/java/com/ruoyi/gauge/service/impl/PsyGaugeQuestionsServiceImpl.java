package com.ruoyi.gauge.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsOptions;
import com.ruoyi.gauge.vo.PsyQuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gauge.mapper.PsyGaugeQuestionsMapper;
import com.ruoyi.gauge.domain.PsyGaugeQuestions;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsService;

/**
 * 心理测评问题Service业务层处理
 *
 * @author ruoyi
 * @date 2022-09-06
 */
@Service
public class PsyGaugeQuestionsServiceImpl implements IPsyGaugeQuestionsService {
    @Autowired
    private PsyGaugeQuestionsMapper psyGaugeQuestionsMapper;

    /**
     * 查询心理测评问题
     *
     * @param id 心理测评问题主键
     * @return 心理测评问题
     */
    @Override
    public PsyGaugeQuestions selectPsyGaugeQuestionsById(Integer id) {
        return psyGaugeQuestionsMapper.selectPsyGaugeQuestionsById(id);
    }

    /**
     * 查询心理测评问题列表
     *
     * @param psyGaugeQuestions 心理测评问题
     * @return 心理测评问题
     */
    @Override
    public List<PsyGaugeQuestions> selectPsyGaugeQuestionsList(PsyGaugeQuestions psyGaugeQuestions) {
        return psyGaugeQuestionsMapper.selectPsyGaugeQuestionsList(psyGaugeQuestions);
    }

    /**
     * 新增心理测评问题
     *
     * @param psyGaugeQuestions 心理测评问题
     * @return 结果
     */
    @Override
    public int insertPsyGaugeQuestions(PsyGaugeQuestions psyGaugeQuestions) {
        psyGaugeQuestions.setCreateTime(DateUtils.getNowDate());
        return psyGaugeQuestionsMapper.insertPsyGaugeQuestions(psyGaugeQuestions);
    }

    /**
     * 修改心理测评问题
     *
     * @param psyGaugeQuestions 心理测评问题
     * @return 结果
     */
    @Override
    public int updatePsyGaugeQuestions(PsyGaugeQuestions psyGaugeQuestions) {
        psyGaugeQuestions.setUpdateTime(DateUtils.getNowDate());
        return psyGaugeQuestionsMapper.updatePsyGaugeQuestions(psyGaugeQuestions);
    }

    /**
     * 批量删除心理测评问题
     *
     * @param ids 需要删除的心理测评问题主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeQuestionsByIds(Integer[] ids) {
        return psyGaugeQuestionsMapper.deletePsyGaugeQuestionsByIds(ids);
    }

    /**
     * 删除心理测评问题信息
     *
     * @param id 心理测评问题主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeQuestionsById(Integer id) {
        return psyGaugeQuestionsMapper.deletePsyGaugeQuestionsById(id);
    }

    @Override
    public List<PsyQuestionVO> appQueryQuesList(PsyGaugeQuestions psyGaugeQuestions, Integer userId) {
        psyGaugeQuestions.setUserId(userId);
        List<PsyQuestionVO> psyQuestionVOS = psyGaugeQuestionsMapper.appQueryQuesList(psyGaugeQuestions);
        psyQuestionVOS.forEach(item ->
            item.setAnswers(
                item.getOptions().stream().filter(PsyGaugeQuestionsOptions::isSelectedFlag)
                        .map(PsyGaugeQuestionsOptions::getId).collect(Collectors.toList())
            )
        );
        return psyQuestionVOS;
    }
}
