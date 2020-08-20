package com.ruoyi.jxjs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJxjsjbxxMapper;
import com.ruoyi.jxjs.domain.TsbzJxjsjbxx;
import com.ruoyi.jxjs.service.ITsbzJxjsjbxxService;

/**
 * 见习教师基本信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
@Service
public class TsbzJxjsjbxxServiceImpl implements ITsbzJxjsjbxxService 
{
    @Autowired
    private TsbzJxjsjbxxMapper tsbzJxjsjbxxMapper;

    /**
     * 查询见习教师基本信息
     * 
     * @param id 见习教师基本信息ID
     * @return 见习教师基本信息
     */
    @Override
    public TsbzJxjsjbxx selectTsbzJxjsjbxxById(Long id)
    {
        return tsbzJxjsjbxxMapper.selectTsbzJxjsjbxxById(id);
    }

    /**
     * 查询见习教师基本信息列表
     * 
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 见习教师基本信息
     */
    @Override
    public List<TsbzJxjsjbxx> selectTsbzJxjsjbxxList(TsbzJxjsjbxx tsbzJxjsjbxx)
    {
        return tsbzJxjsjbxxMapper.selectTsbzJxjsjbxxList(tsbzJxjsjbxx);
    }

    /**
     * 查询见习教师基本信息列表
     *
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 见习教师基本信息
     */
    @Override
    public List<TsbzJxjsjbxx> selectTsbzJxjsjbxxListnotjdx(TsbzJxjsjbxx tsbzJxjsjbxx)
    {
        return tsbzJxjsjbxxMapper.selectTsbzJxjsjbxxListnotjdx(tsbzJxjsjbxx);
    }

    /**
     * 新增见习教师基本信息
     * 
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 结果
     */
    @Override
    public int insertTsbzJxjsjbxx(TsbzJxjsjbxx tsbzJxjsjbxx)
    {
        tsbzJxjsjbxx.setCreateTime(DateUtils.getNowDate());
        return tsbzJxjsjbxxMapper.insertTsbzJxjsjbxx(tsbzJxjsjbxx);
    }

    /**
     * 修改见习教师基本信息
     * 
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 结果
     */
    @Override
    public int updateTsbzJxjsjbxx(TsbzJxjsjbxx tsbzJxjsjbxx)
    {
        return tsbzJxjsjbxxMapper.updateTsbzJxjsjbxx(tsbzJxjsjbxx);
    }

    /**
     * 批量删除见习教师基本信息
     * 
     * @param ids 需要删除的见习教师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxjsjbxxByIds(Long[] ids)
    {
        return tsbzJxjsjbxxMapper.deleteTsbzJxjsjbxxByIds(ids);
    }

    /**
     * 删除见习教师基本信息信息
     * 
     * @param id 见习教师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxjsjbxxById(Long id)
    {
        return tsbzJxjsjbxxMapper.deleteTsbzJxjsjbxxById(id);
    }
}
