package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysAskNutritionQuestion;

/**
 * 营养小知识提问Mapper接口
 *
 * @author xzj
 * @date 2021-04-23
 */
public interface SysAskNutritionQuestionMapper
{
    /**
     * 查询营养小知识提问
     *
     * @param id 营养小知识提问ID
     * @return 营养小知识提问
     */
    public SysAskNutritionQuestion selectSysAskNutritionQuestionById(Long id);

    /**
     * 查询营养小知识提问列表
     *
     * @param sysAskNutritionQuestion 营养小知识提问
     * @return 营养小知识提问集合
     */
    public List<SysAskNutritionQuestion> selectSysAskNutritionQuestionList(SysAskNutritionQuestion sysAskNutritionQuestion);

    /**
     * 新增营养小知识提问
     *
     * @param sysAskNutritionQuestion 营养小知识提问
     * @return 结果
     */
    public int insertSysAskNutritionQuestion(SysAskNutritionQuestion sysAskNutritionQuestion);

    /**
     * 修改营养小知识提问
     *
     * @param sysAskNutritionQuestion 营养小知识提问
     * @return 结果
     */
    public int updateSysAskNutritionQuestion(SysAskNutritionQuestion sysAskNutritionQuestion);

    /**
     * 删除营养小知识提问
     *
     * @param id 营养小知识提问ID
     * @return 结果
     */
    public int deleteSysAskNutritionQuestionById(Long id);

    /**
     * 批量删除营养小知识提问
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAskNutritionQuestionByIds(Long[] ids);
}