package com.jjpt.business.service;

import java.util.List;
import com.jjpt.business.domain.ElQuAnswer;

/**
 * 试题答案选项Service接口
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
public interface IElQuAnswerService 
{
    /**
     * 查询试题答案选项
     * 
     * @param id 试题答案选项主键
     * @return 试题答案选项
     */
    public ElQuAnswer selectElQuAnswerById(String id);

    /**
     * 查询试题答案选项列表
     * 
     * @param elQuAnswer 试题答案选项
     * @return 试题答案选项集合
     */
    public List<ElQuAnswer> selectElQuAnswerList(ElQuAnswer elQuAnswer);

    /**
     * 新增试题答案选项
     * 
     * @param elQuAnswer 试题答案选项
     * @return 结果
     */
    public int insertElQuAnswer(ElQuAnswer elQuAnswer);

    /**
     * 修改试题答案选项
     * 
     * @param elQuAnswer 试题答案选项
     * @return 结果
     */
    public int updateElQuAnswer(ElQuAnswer elQuAnswer);

    /**
     * 批量删除试题答案选项
     * 
     * @param ids 需要删除的试题答案选项主键集合
     * @return 结果
     */
    public int deleteElQuAnswerByIds(String[] ids);

    /**
     * 删除试题答案选项信息
     * 
     * @param id 试题答案选项主键
     * @return 结果
     */
    public int deleteElQuAnswerById(String id);

    void saveAll(String quId, List<ElQuAnswer> answerList);

    List<ElQuAnswer> listAnswerByRandom(String quId);
}
