package com.ruoyi.zcrpsgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zcrpsgl.mapper.TsbzZcrjdcjMapper;
import com.ruoyi.zcrpsgl.domain.TsbzZcrjdcj;
import com.ruoyi.zcrpsgl.service.ITsbzZcrjdcjService;

/**
 * 主持人基地Service业务层处理
 * 
 * @author tsbz
 * @date 2021-04-23
 */
@Service
public class TsbzZcrjdcjServiceImpl implements ITsbzZcrjdcjService 
{
    @Autowired
    private TsbzZcrjdcjMapper tsbzZcrjdcjMapper;

    /**
     * 查询主持人基地
     * 
     * @param id 主持人基地ID
     * @return 主持人基地
     */
    @Override
    public TsbzZcrjdcj selectTsbzZcrjdcjById(Long id)
    {
        return tsbzZcrjdcjMapper.selectTsbzZcrjdcjById(id);
    }

    /**
     * 查询主持人基地列表
     * 
     * @param tsbzZcrjdcj 主持人基地
     * @return 主持人基地
     */
    @Override
    public List<TsbzZcrjdcj> selectTsbzZcrjdcjList(TsbzZcrjdcj tsbzZcrjdcj)
    {
        return tsbzZcrjdcjMapper.selectTsbzZcrjdcjList(tsbzZcrjdcj);
    }

    /**
     * 新增主持人基地
     * 
     * @param tsbzZcrjdcj 主持人基地
     * @return 结果
     */
    @Override
    public int insertTsbzZcrjdcj(TsbzZcrjdcj tsbzZcrjdcj)
    {
        tsbzZcrjdcj.setCreateTime(DateUtils.getNowDate());
        return tsbzZcrjdcjMapper.insertTsbzZcrjdcj(tsbzZcrjdcj);
    }

    /**
     * 修改主持人基地
     * 
     * @param tsbzZcrjdcj 主持人基地
     * @return 结果
     */
    @Override
    public int updateTsbzZcrjdcj(TsbzZcrjdcj tsbzZcrjdcj)
    {
        return tsbzZcrjdcjMapper.updateTsbzZcrjdcj(tsbzZcrjdcj);
    }

    /**
     * 批量删除主持人基地
     * 
     * @param ids 需要删除的主持人基地ID
     * @return 结果
     */
    @Override
    public int deleteTsbzZcrjdcjByIds(Long[] ids)
    {
        return tsbzZcrjdcjMapper.deleteTsbzZcrjdcjByIds(ids);
    }

    /**
     * 删除主持人基地信息
     * 
     * @param id 主持人基地ID
     * @return 结果
     */
    @Override
    public int deleteTsbzZcrjdcjById(Long id)
    {
        return tsbzZcrjdcjMapper.deleteTsbzZcrjdcjById(id);
    }
}
