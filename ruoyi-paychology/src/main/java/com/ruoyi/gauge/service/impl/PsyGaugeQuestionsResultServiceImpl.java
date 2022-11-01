package com.ruoyi.gauge.service.impl;

import com.ruoyi.common.core.domain.dto.GaugeCommitResultDTO;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsOptions;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsResult;
import com.ruoyi.gauge.mapper.PsyGaugeQuestionsOptionsMapper;
import com.ruoyi.gauge.mapper.PsyGaugeQuestionsResultMapper;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsResultService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 心理测评问题结果Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
@Service
public class PsyGaugeQuestionsResultServiceImpl implements IPsyGaugeQuestionsResultService 
{
    @Resource
    private PsyGaugeQuestionsResultMapper psyGaugeQuestionsResultMapper;

    @Resource
    private PsyGaugeQuestionsOptionsMapper psyGaugeQuestionsOptionsMapper;

    /**
     * 查询心理测评问题结果
     * 
     * @param id 心理测评问题结果主键
     * @return 心理测评问题结果
     */
    @Override
    public PsyGaugeQuestionsResult selectPsyGaugeQuestionsResultById(Long id)
    {
        return psyGaugeQuestionsResultMapper.selectPsyGaugeQuestionsResultById(id);
    }

    /**
     * 查询心理测评问题结果列表
     * 
     * @param psyGaugeQuestionsResult 心理测评问题结果
     * @return 心理测评问题结果
     */
    @Override
    public List<PsyGaugeQuestionsResult> selectPsyGaugeQuestionsResultList(PsyGaugeQuestionsResult psyGaugeQuestionsResult)
    {
        return psyGaugeQuestionsResultMapper.selectPsyGaugeQuestionsResultList(psyGaugeQuestionsResult);
    }

    /**
     * 新增心理测评问题结果
     * 
     * @param psyGaugeQuestionsResult 心理测评问题结果
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int answer(PsyGaugeQuestionsResult psyGaugeQuestionsResult ,LoginDTO loginDTO)
    {
        //先删除该问题的答案
        psyGaugeQuestionsResult.setUserId(loginDTO.getUserId());
        psyGaugeQuestionsResultMapper.deleteResult(psyGaugeQuestionsResult);

        //查询问题分数，进行数据绑定，插入数据
        List<PsyGaugeQuestionsOptions> psyGaugeQuestionsOptions = psyGaugeQuestionsOptionsMapper.queryOptionsByIds(psyGaugeQuestionsResult.getQuestionsOptionsIdList());
        Map<Long, Long> collect = psyGaugeQuestionsOptions.stream().collect(Collectors.toMap(PsyGaugeQuestionsOptions::getId, PsyGaugeQuestionsOptions::getValue));

        List<PsyGaugeQuestionsResult> results = Lists.newArrayList();
        for (Long id : psyGaugeQuestionsResult.getQuestionsOptionsIdList()) {
            PsyGaugeQuestionsResult build = PsyGaugeQuestionsResult.builder()
                    .gaugeId(psyGaugeQuestionsResult.getGaugeId())
                    .questionsId(psyGaugeQuestionsResult.getQuestionsId())
                    .questionsOptionsId(id)
                    .score(collect.get(id).toString())
                    .userId(loginDTO.getUserId())
                    .orderId(psyGaugeQuestionsResult.getOrderId())
                    .build();
            build.setCreateTime(DateUtils.getNowDate());
            results.add(build);
        }
        return psyGaugeQuestionsResultMapper.batchInsert(results);
    }

    /**
     * 修改心理测评问题结果
     * 
     * @param psyGaugeQuestionsResult 心理测评问题结果
     * @return 结果
     */
    @Override
    public int updatePsyGaugeQuestionsResult(PsyGaugeQuestionsResult psyGaugeQuestionsResult)
    {
        return psyGaugeQuestionsResultMapper.updatePsyGaugeQuestionsResult(psyGaugeQuestionsResult);
    }

    /**
     * 批量删除心理测评问题结果
     * 
     * @param ids 需要删除的心理测评问题结果主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeQuestionsResultByIds(Long[] ids)
    {
        return psyGaugeQuestionsResultMapper.deletePsyGaugeQuestionsResultByIds(ids);
    }

    /**
     * 删除心理测评问题结果信息
     * 
     * @param id 心理测评问题结果主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeQuestionsResultById(Long id)
    {
        return psyGaugeQuestionsResultMapper.deletePsyGaugeQuestionsResultById(id);
    }

    @Override
    public void commitResult(GaugeCommitResultDTO gaugeCommitResultDTO) {
        //将该订单答题情况改为已完成




    }
}
