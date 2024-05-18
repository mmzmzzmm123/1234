package com.jjpt.business.service.impl;

import java.util.List;

import com.jjpt.business.domain.ElExam;
import com.jjpt.business.mapper.ElExamMapper;
import com.jjpt.business.service.IElExamService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 考试主Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-18
 */
@Service
public class ElExamServiceImpl implements IElExamService
{
    @Autowired
    private ElExamMapper elExamMapper;

    /**
     * 查询考试主
     * 
     * @param id 考试主主键
     * @return 考试主
     */
    @Override
    public ElExam selectElExamById(String id)
    {
        return elExamMapper.selectElExamById(id);
    }

    /**
     * 查询考试主列表
     * 
     * @param elExam 考试主
     * @return 考试主
     */
    @Override
    public List<ElExam> selectElExamList(ElExam elExam)
    {
        return elExamMapper.selectElExamList(elExam);
    }

    /**
     * 新增考试主
     * 
     * @param elExam 考试主
     * @return 结果
     */
    @Override
    public int insertElExam(ElExam elExam)
    {
        elExam.setCreateTime(DateUtils.getNowDate());
        return elExamMapper.insertElExam(elExam);
    }

    /**
     * 修改考试主
     * 
     * @param elExam 考试主
     * @return 结果
     */
    @Override
    public int updateElExam(ElExam elExam)
    {
        elExam.setUpdateTime(DateUtils.getNowDate());
        return elExamMapper.updateElExam(elExam);
    }

    /**
     * 批量删除考试主
     * 
     * @param ids 需要删除的考试主主键
     * @return 结果
     */
    @Override
    public int deleteElExamByIds(String[] ids)
    {
        return elExamMapper.deleteElExamByIds(ids);
    }

    /**
     * 删除考试主信息
     * 
     * @param id 考试主主键
     * @return 结果
     */
    @Override
    public int deleteElExamById(String id)
    {
        return elExamMapper.deleteElExamById(id);
    }
}
