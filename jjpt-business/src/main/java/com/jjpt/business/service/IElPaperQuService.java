package com.jjpt.business.service;

import java.util.List;
import com.jjpt.business.domain.ElPaperQu;

/**
 * 考试记录考题Service接口
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
public interface IElPaperQuService 
{
    /**
     * 查询考试记录考题
     * 
     * @param id 考试记录考题主键
     * @return 考试记录考题
     */
    public ElPaperQu selectElPaperQuById(String id);

    /**
     * 查询考试记录考题列表
     * 
     * @param elPaperQu 考试记录考题
     * @return 考试记录考题集合
     */
    public List<ElPaperQu> selectElPaperQuList(ElPaperQu elPaperQu);

    /**
     * 新增考试记录考题
     * 
     * @param elPaperQu 考试记录考题
     * @return 结果
     */
    public int insertElPaperQu(ElPaperQu elPaperQu);

    /**
     * 修改考试记录考题
     * 
     * @param elPaperQu 考试记录考题
     * @return 结果
     */
    public int updateElPaperQu(ElPaperQu elPaperQu);

    /**
     * 批量删除考试记录考题
     * 
     * @param ids 需要删除的考试记录考题主键集合
     * @return 结果
     */
    public int deleteElPaperQuByIds(String[] ids);

    /**
     * 删除考试记录考题信息
     * 
     * @param id 考试记录考题主键
     * @return 结果
     */
    public int deleteElPaperQuById(String id);

    int sumObjective(String paperId);

    void updateElPaperQuByKey(ElPaperQu qu);
}
