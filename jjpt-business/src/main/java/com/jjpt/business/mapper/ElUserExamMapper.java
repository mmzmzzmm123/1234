package com.jjpt.business.mapper;

import java.util.List;
import com.jjpt.business.domain.ElUserExam;
import com.jjpt.business.domain.dto.UserExamRespDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 考试记录Mapper接口
 * 
 * @author 卢亚峰
 * @date 2024-05-22
 */
public interface ElUserExamMapper 
{
    /**
     * 查询考试记录
     * 
     * @param id 考试记录主键
     * @return 考试记录
     */
    public ElUserExam selectElUserExamById(String id);

    /**
     * 查询考试记录列表
     * 
     * @param elUserExam 考试记录
     * @return 考试记录集合
     */
    public List<UserExamRespDTO> selectElUserExamList(@Param("query") UserExamRespDTO query);

    /**
     * 新增考试记录
     * 
     * @param elUserExam 考试记录
     * @return 结果
     */
    public int insertElUserExam(ElUserExam elUserExam);

    /**
     * 修改考试记录
     * 
     * @param elUserExam 考试记录
     * @return 结果
     */
    public int updateElUserExam(ElUserExam elUserExam);

    /**
     * 删除考试记录
     * 
     * @param id 考试记录主键
     * @return 结果
     */
    public int deleteElUserExamById(String id);

    /**
     * 批量删除考试记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteElUserExamByIds(String[] ids);

    ElUserExam selectElUserExamByKey(ElUserExam record);
}
