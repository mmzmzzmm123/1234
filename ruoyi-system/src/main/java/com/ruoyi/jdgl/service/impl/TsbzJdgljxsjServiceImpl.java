package com.ruoyi.jdgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jdgl.mapper.TsbzJdgljxsjMapper;
import com.ruoyi.jdgl.domain.TsbzJdgljxsj;
import com.ruoyi.jdgl.service.ITsbzJdgljxsjService;

/**
 * 基地管理教学实践Service业务层处理
 * 
 * @author tsbz
 * @date 2021-05-17
 */
@Service
public class TsbzJdgljxsjServiceImpl implements ITsbzJdgljxsjService 
{
    @Autowired
    private TsbzJdgljxsjMapper tsbzJdgljxsjMapper;

    /**
     * 查询基地管理教学实践
     * 
     * @param id 基地管理教学实践ID
     * @return 基地管理教学实践
     */
    @Override
    public TsbzJdgljxsj selectTsbzJdgljxsjById(Long id)
    {
        return tsbzJdgljxsjMapper.selectTsbzJdgljxsjById(id);
    }

    /**
     * 查询基地管理教学实践列表
     * 
     * @param tsbzJdgljxsj 基地管理教学实践
     * @return 基地管理教学实践
     */
    @Override
    public List<TsbzJdgljxsj> selectTsbzJdgljxsjList(TsbzJdgljxsj tsbzJdgljxsj)
    {
        return tsbzJdgljxsjMapper.selectTsbzJdgljxsjList(tsbzJdgljxsj);
    }

    /**
     * 新增基地管理教学实践
     * 
     * @param tsbzJdgljxsj 基地管理教学实践
     * @return 结果
     */
    @Override
    public int insertTsbzJdgljxsj(TsbzJdgljxsj tsbzJdgljxsj)
    {
        tsbzJdgljxsj.setCreateTime(DateUtils.getNowDate());
        return tsbzJdgljxsjMapper.insertTsbzJdgljxsj(tsbzJdgljxsj);
    }

    /**
     * 修改基地管理教学实践
     * 
     * @param tsbzJdgljxsj 基地管理教学实践
     * @return 结果
     */
    @Override
    public int updateTsbzJdgljxsj(TsbzJdgljxsj tsbzJdgljxsj)
    {
        return tsbzJdgljxsjMapper.updateTsbzJdgljxsj(tsbzJdgljxsj);
    }

    /**
     * 批量删除基地管理教学实践
     * 
     * @param ids 需要删除的基地管理教学实践ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdgljxsjByIds(Long[] ids)
    {
        return tsbzJdgljxsjMapper.deleteTsbzJdgljxsjByIds(ids);
    }

    /**
     * 删除基地管理教学实践信息
     * 
     * @param id 基地管理教学实践ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdgljxsjById(Long id)
    {
        return tsbzJdgljxsjMapper.deleteTsbzJdgljxsjById(id);
    }
}
