package com.jjpt.business.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjpt.business.mapper.ElPaperMapper;
import com.jjpt.business.domain.ElPaper;
import com.jjpt.business.service.IElPaperService;

/**
 * 考试记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
@Service
public class ElPaperServiceImpl implements IElPaperService 
{
    @Autowired
    private ElPaperMapper elPaperMapper;

    /**
     * 查询考试记录
     * 
     * @param id 考试记录主键
     * @return 考试记录
     */
    @Override
    public ElPaper selectElPaperById(String id)
    {
        return elPaperMapper.selectElPaperById(id);
    }

    /**
     * 查询考试记录列表
     * 
     * @param elPaper 考试记录
     * @return 考试记录
     */
    @Override
    public List<ElPaper> selectElPaperList(ElPaper elPaper)
    {
        return elPaperMapper.selectElPaperList(elPaper);
    }

    /**
     * 新增考试记录
     * 
     * @param elPaper 考试记录
     * @return 结果
     */
    @Override
    public int insertElPaper(ElPaper elPaper)
    {
        elPaper.setCreateTime(DateUtils.getNowDate());
        return elPaperMapper.insertElPaper(elPaper);
    }

    /**
     * 修改考试记录
     * 
     * @param elPaper 考试记录
     * @return 结果
     */
    @Override
    public int updateElPaper(ElPaper elPaper)
    {
        elPaper.setUpdateTime(DateUtils.getNowDate());
        return elPaperMapper.updateElPaper(elPaper);
    }

    /**
     * 批量删除考试记录
     * 
     * @param ids 需要删除的考试记录主键
     * @return 结果
     */
    @Override
    public int deleteElPaperByIds(String[] ids)
    {
        return elPaperMapper.deleteElPaperByIds(ids);
    }

    /**
     * 删除考试记录信息
     * 
     * @param id 考试记录主键
     * @return 结果
     */
    @Override
    public int deleteElPaperById(String id)
    {
        return elPaperMapper.deleteElPaperById(id);
    }
}
