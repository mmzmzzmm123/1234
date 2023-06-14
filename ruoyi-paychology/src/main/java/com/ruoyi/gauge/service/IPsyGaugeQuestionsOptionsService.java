package com.ruoyi.gauge.service;

import java.util.List;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsOptions;

/**
 * 心理测评问题选项Service接口
 * 
 * @author ruoyi
 * @date 2022-09-06
 */
public interface IPsyGaugeQuestionsOptionsService 
{
    /**
     * 查询心理测评问题选项
     * 
     * @param id 心理测评问题选项主键
     * @return 心理测评问题选项
     */
    public PsyGaugeQuestionsOptions selectPsyGaugeQuestionsOptionsById(Integer id);

    /**
     * 查询心理测评问题选项列表
     * 
     * @param psyGaugeQuestionsOptions 心理测评问题选项
     * @return 心理测评问题选项集合
     */
    public List<PsyGaugeQuestionsOptions> selectPsyGaugeQuestionsOptionsList(PsyGaugeQuestionsOptions psyGaugeQuestionsOptions);

    /**
     * 新增心理测评问题选项
     * 
     * @param psyGaugeQuestionsOptions 心理测评问题选项
     * @return 结果
     */
    public int insertPsyGaugeQuestionsOptions(PsyGaugeQuestionsOptions psyGaugeQuestionsOptions);

    /**
     * 修改心理测评问题选项
     * 
     * @param psyGaugeQuestionsOptions 心理测评问题选项
     * @return 结果
     */
    public int updatePsyGaugeQuestionsOptions(PsyGaugeQuestionsOptions psyGaugeQuestionsOptions);

    /**
     * 批量删除心理测评问题选项
     * 
     * @param ids 需要删除的心理测评问题选项主键集合
     * @return 结果
     */
    public int deletePsyGaugeQuestionsOptionsByIds(Integer[] ids);

    /**
     * 删除心理测评问题选项信息
     * 
     * @param id 心理测评问题选项主键
     * @return 结果
     */
    public int deletePsyGaugeQuestionsOptionsById(Integer id);
}
