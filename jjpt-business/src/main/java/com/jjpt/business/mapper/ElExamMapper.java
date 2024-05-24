package com.jjpt.business.mapper;

import com.jjpt.business.domain.ElExam;

import java.util.List;


/**
 * 考试主Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-18
 */
public interface ElExamMapper 
{
    /**
     * 查询考试主
     * 
     * @param id 考试主主键
     * @return 考试主
     */
    public ElExam selectElExamById(String id);

    /**
     * 查询考试主列表
     * 
     * @param elExam 考试主
     * @return 考试主集合
     */
    public List<ElExam> selectElExamList(ElExam elExam);

    /**
     * 新增考试主
     * 
     * @param elExam 考试主
     * @return 结果
     */
    public int insertElExam(ElExam elExam);

    /**
     * 修改考试主
     * 
     * @param elExam 考试主
     * @return 结果
     */
    public int updateElExam(ElExam elExam);

    /**
     * 删除考试主
     * 
     * @param id 考试主主键
     * @return 结果
     */
    public int deleteElExamById(String id);

    /**
     * 批量删除考试主
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteElExamByIds(String[] ids);
}
