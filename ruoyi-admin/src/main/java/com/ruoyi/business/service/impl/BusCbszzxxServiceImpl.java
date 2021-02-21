package com.ruoyi.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.BusCbszzxxMapper;
import com.ruoyi.business.domain.BusCbszzxx;
import com.ruoyi.business.service.IBusCbszzxxService;

/**
 * 承包商资质信息Service业务层处理
 * 
 * @author yaowei
 * @date 2021-02-21
 */
@Service
public class BusCbszzxxServiceImpl implements IBusCbszzxxService 
{
    @Autowired
    private BusCbszzxxMapper busCbszzxxMapper;

    /**
     * 查询承包商资质信息
     * 
     * @param id 承包商资质信息ID
     * @return 承包商资质信息
     */
    @Override
    public BusCbszzxx selectBusCbszzxxById(Long id)
    {
        return busCbszzxxMapper.selectBusCbszzxxById(id);
    }

    /**
     * 查询承包商资质信息
     * 
     * @param id 承包商资质信息ID
     * @return 承包商资质信息
     */
    @Override
    public List<BusCbszzxx> selectByCbsId(Long cbsId)
    {
        return busCbszzxxMapper.selectByCbsId(cbsId);
    }
    
    /**
     * 查询承包商资质信息列表
     * 
     * @param busCbszzxx 承包商资质信息
     * @return 承包商资质信息
     */
    @Override
    public List<BusCbszzxx> selectBusCbszzxxList(BusCbszzxx busCbszzxx)
    {
        return busCbszzxxMapper.selectBusCbszzxxList(busCbszzxx);
    }

    /**
     * 新增承包商资质信息
     * 
     * @param busCbszzxx 承包商资质信息
     * @return 结果
     */
    @Override
    public int insertBusCbszzxx(BusCbszzxx busCbszzxx)
    {
        return busCbszzxxMapper.insertBusCbszzxx(busCbszzxx);
    }

    /**
     * 修改承包商资质信息
     * 
     * @param busCbszzxx 承包商资质信息
     * @return 结果
     */
    @Override
    public int updateBusCbszzxx(BusCbszzxx busCbszzxx)
    {
        return busCbszzxxMapper.updateBusCbszzxx(busCbszzxx);
    }

    /**
     * 批量删除承包商资质信息
     * 
     * @param ids 需要删除的承包商资质信息ID
     * @return 结果
     */
    @Override
    public int deleteBusCbszzxxByIds(Long[] ids)
    {
        return busCbszzxxMapper.deleteBusCbszzxxByIds(ids);
    }

    /**
     * 删除承包商资质信息信息
     * 
     * @param id 承包商资质信息ID
     * @return 结果
     */
    @Override
    public int deleteBusCbszzxxById(Long id)
    {
        return busCbszzxxMapper.deleteBusCbszzxxById(id);
    }
}
