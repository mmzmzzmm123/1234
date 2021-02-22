package com.ruoyi.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.BusCbszdxxMapper;
import com.ruoyi.business.domain.BusCbszdxx;
import com.ruoyi.business.service.IBusCbszdxxService;

/**
 * 承包商站点信息Service业务层处理
 * 
 * @author yaowei
 * @date 2021-02-22
 */
@Service
public class BusCbszdxxServiceImpl implements IBusCbszdxxService 
{
    @Autowired
    private BusCbszdxxMapper busCbszdxxMapper;

    /**
     * 查询承包商站点信息
     * 
     * @param id 承包商站点信息ID
     * @return 承包商站点信息
     */
    @Override
    public BusCbszdxx selectBusCbszdxxById(Long id)
    {
        return busCbszdxxMapper.selectBusCbszdxxById(id);
    }

    /**
     * 查询承包商站点信息列表
     * 
     * @param busCbszdxx 承包商站点信息
     * @return 承包商站点信息
     */
    @Override
    public List<BusCbszdxx> selectBusCbszdxxList(BusCbszdxx busCbszdxx)
    {
        return busCbszdxxMapper.selectBusCbszdxxList(busCbszdxx);
    }

    /**
     * 新增承包商站点信息
     * 
     * @param busCbszdxx 承包商站点信息
     * @return 结果
     */
    @Override
    public int insertBusCbszdxx(BusCbszdxx busCbszdxx)
    {
        return busCbszdxxMapper.insertBusCbszdxx(busCbszdxx);
    }

    /**
     * 修改承包商站点信息
     * 
     * @param busCbszdxx 承包商站点信息
     * @return 结果
     */
    @Override
    public int updateBusCbszdxx(BusCbszdxx busCbszdxx)
    {
        return busCbszdxxMapper.updateBusCbszdxx(busCbszdxx);
    }

    /**
     * 批量删除承包商站点信息
     * 
     * @param ids 需要删除的承包商站点信息ID
     * @return 结果
     */
    @Override
    public int deleteBusCbszdxxByIds(Long[] ids)
    {
        return busCbszdxxMapper.deleteBusCbszdxxByIds(ids);
    }

    /**
     * 删除承包商站点信息信息
     * 
     * @param id 承包商站点信息ID
     * @return 结果
     */
    @Override
    public int deleteBusCbszdxxById(Long id)
    {
        return busCbszdxxMapper.deleteBusCbszdxxById(id);
    }
}
