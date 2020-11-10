package com.ruoyi.jxzxkhgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxzxkhgl.mapper.TsbzDsjbxxMapper;
import com.ruoyi.jxzxkhgl.domain.TsbzDsjbxx;
import com.ruoyi.jxzxkhgl.service.ITsbzDsjbxxService;

/**
 * 导师基本信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-11-10
 */
@Service
public class TsbzDsjbxxServiceImpl implements ITsbzDsjbxxService 
{
    @Autowired
    private TsbzDsjbxxMapper tsbzDsjbxxMapper;

    /**
     * 查询导师基本信息
     * 
     * @param id 导师基本信息ID
     * @return 导师基本信息
     */
    @Override
    public TsbzDsjbxx selectTsbzDsjbxxById(Long id)
    {
        return tsbzDsjbxxMapper.selectTsbzDsjbxxById(id);
    }

    /**
     * 查询导师基本信息列表
     * 
     * @param tsbzDsjbxx 导师基本信息
     * @return 导师基本信息
     */
    @Override
    public List<TsbzDsjbxx> selectTsbzDsjbxxList(TsbzDsjbxx tsbzDsjbxx)
    {
        return tsbzDsjbxxMapper.selectTsbzDsjbxxList(tsbzDsjbxx);
    }

    /**
     * 新增导师基本信息
     * 
     * @param tsbzDsjbxx 导师基本信息
     * @return 结果
     */
    @Override
    public int insertTsbzDsjbxx(TsbzDsjbxx tsbzDsjbxx)
    {
        tsbzDsjbxx.setCreateTime(DateUtils.getNowDate());
        return tsbzDsjbxxMapper.insertTsbzDsjbxx(tsbzDsjbxx);
    }

    /**
     * 修改导师基本信息
     * 
     * @param tsbzDsjbxx 导师基本信息
     * @return 结果
     */
    @Override
    public int updateTsbzDsjbxx(TsbzDsjbxx tsbzDsjbxx)
    {
        return tsbzDsjbxxMapper.updateTsbzDsjbxx(tsbzDsjbxx);
    }

    /**
     * 批量删除导师基本信息
     * 
     * @param ids 需要删除的导师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzDsjbxxByIds(Long[] ids)
    {
        return tsbzDsjbxxMapper.deleteTsbzDsjbxxByIds(ids);
    }

    /**
     * 删除导师基本信息信息
     * 
     * @param id 导师基本信息ID
     * @return 结果
     */
    @Override
    public int deleteTsbzDsjbxxById(Long id)
    {
        return tsbzDsjbxxMapper.deleteTsbzDsjbxxById(id);
    }
}
