package com.jjpt.business.mapper;

import java.util.List;
import com.jjpt.business.domain.ElPaper;
import com.jjpt.business.domain.dto.PaperListRespDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 考试记录Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
public interface ElPaperMapper 
{
    /**
     * 查询考试记录
     * 
     * @param id 考试记录主键
     * @return 考试记录
     */
    public ElPaper selectElPaperById(String id);

    /**
     * 查询考试记录列表
     * 
     * @param elPaper 考试记录
     * @return 考试记录集合
     */
    public List<PaperListRespDTO> selectElPaperList(@Param("query")PaperListRespDTO query);

    /**
     * 新增考试记录
     * 
     * @param elPaper 考试记录
     * @return 结果
     */
    public int insertElPaper(ElPaper elPaper);

    /**
     * 修改考试记录
     * 
     * @param elPaper 考试记录
     * @return 结果
     */
    public int updateElPaper(ElPaper elPaper);

    /**
     * 删除考试记录
     * 
     * @param id 考试记录主键
     * @return 结果
     */
    public int deleteElPaperById(String id);

    /**
     * 批量删除考试记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteElPaperByIds(String[] ids);
}
