package com.jjpt.business.service;

import java.util.List;
import com.jjpt.business.domain.ElPaper;
import com.jjpt.business.domain.dto.*;

/**
 * 考试记录Service接口
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
public interface IElPaperService 
{
    /**
     * 查询考试记录
     * 
     * @param id 考试记录主键
     * @return 考试记录
     */
    public ExamDetailRespDTO selectElPaperById(String id);

    /**
     * 查询考试记录列表
     * 
     * @param elPaper 考试记录
     * @return 考试记录集合
     */
    public List<PaperListRespDTO> selectElPaperList(PaperListRespDTO elPaper);

    /**
     * 新增考试记录
     * 
     * @param elPaper 考试记录
     * @return 结果
     */
    public String insertElPaper(ElPaper elPaper);

    /**
     * 修改考试记录
     * 
     * @param elPaper 考试记录
     * @return 结果
     */
    public int updateElPaper(ElPaper elPaper);

    /**
     * 批量删除考试记录
     * 
     * @param ids 需要删除的考试记录主键集合
     * @return 结果
     */
    public int deleteElPaperByIds(String[] ids);

    /**
     * 删除考试记录信息
     * 
     * @param id 考试记录主键
     * @return 结果
     */
    public int deleteElPaperById(String id);

    void handExam(String data);

    PaperQuDetailDTO findQuDetail(String paperId, String quId);

    void fillAnswer(PaperAnswerDTO reqDTO);


    ExamResultRespDTO paperResult(String paperId);
}
