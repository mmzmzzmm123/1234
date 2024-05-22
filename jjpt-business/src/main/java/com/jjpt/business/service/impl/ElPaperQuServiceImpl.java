package com.jjpt.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjpt.business.mapper.ElPaperQuMapper;
import com.jjpt.business.domain.ElPaperQu;
import com.jjpt.business.service.IElPaperQuService;

/**
 * 考试记录考题Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
@Service
public class ElPaperQuServiceImpl implements IElPaperQuService 
{
    @Autowired
    private ElPaperQuMapper elPaperQuMapper;

    /**
     * 查询考试记录考题
     * 
     * @param id 考试记录考题主键
     * @return 考试记录考题
     */
    @Override
    public ElPaperQu selectElPaperQuById(String id)
    {
        return elPaperQuMapper.selectElPaperQuById(id);
    }

    /**
     * 查询考试记录考题列表
     * 
     * @param elPaperQu 考试记录考题
     * @return 考试记录考题
     */
    @Override
    public List<ElPaperQu> selectElPaperQuList(ElPaperQu elPaperQu)
    {
        return elPaperQuMapper.selectElPaperQuList(elPaperQu);
    }

    /**
     * 新增考试记录考题
     * 
     * @param elPaperQu 考试记录考题
     * @return 结果
     */
    @Override
    public int insertElPaperQu(ElPaperQu elPaperQu)
    {
        return elPaperQuMapper.insertElPaperQu(elPaperQu);
    }

    /**
     * 修改考试记录考题
     * 
     * @param elPaperQu 考试记录考题
     * @return 结果
     */
    @Override
    public int updateElPaperQu(ElPaperQu elPaperQu)
    {
        return elPaperQuMapper.updateElPaperQu(elPaperQu);
    }

    /**
     * 批量删除考试记录考题
     * 
     * @param ids 需要删除的考试记录考题主键
     * @return 结果
     */
    @Override
    public int deleteElPaperQuByIds(String[] ids)
    {
        return elPaperQuMapper.deleteElPaperQuByIds(ids);
    }

    /**
     * 删除考试记录考题信息
     * 
     * @param id 考试记录考题主键
     * @return 结果
     */
    @Override
    public int deleteElPaperQuById(String id)
    {
        return elPaperQuMapper.deleteElPaperQuById(id);
    }

    @Override
    public int sumObjective(String paperId) {

        return elPaperQuMapper.sumObjective(paperId);

    }

    @Override
    public void updateElPaperQuByKey(ElPaperQu qu) {
        elPaperQuMapper.updateElPaperQuByKey(qu);
    }
}
