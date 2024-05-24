package com.jjpt.business.mapper;

import java.util.List;
import com.jjpt.business.domain.ElPaperQuAnswer;
import com.jjpt.business.domain.dto.PaperQuAnswerExtDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 考试记录答案Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
public interface ElPaperQuAnswerMapper 
{
    /**
     * 查询考试记录答案
     * 
     * @param id 考试记录答案主键
     * @return 考试记录答案
     */
    public ElPaperQuAnswer selectElPaperQuAnswerById(String id);

    /**
     * 查询考试记录答案列表
     * 
     * @param elPaperQuAnswer 考试记录答案
     * @return 考试记录答案集合
     */
    public List<ElPaperQuAnswer> selectElPaperQuAnswerList(ElPaperQuAnswer elPaperQuAnswer);

    /**
     * 新增考试记录答案
     * 
     * @param elPaperQuAnswer 考试记录答案
     * @return 结果
     */
    public int insertElPaperQuAnswer(ElPaperQuAnswer elPaperQuAnswer);

    /**
     * 修改考试记录答案
     * 
     * @param elPaperQuAnswer 考试记录答案
     * @return 结果
     */
    public int updateElPaperQuAnswer(ElPaperQuAnswer elPaperQuAnswer);

    /**
     * 删除考试记录答案
     * 
     * @param id 考试记录答案主键
     * @return 结果
     */
    public int deleteElPaperQuAnswerById(String id);

    /**
     * 批量删除考试记录答案
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteElPaperQuAnswerByIds(String[] ids);

    List<PaperQuAnswerExtDTO> list(@Param("paperId") String paperId, @Param("quId") String quId);
}
