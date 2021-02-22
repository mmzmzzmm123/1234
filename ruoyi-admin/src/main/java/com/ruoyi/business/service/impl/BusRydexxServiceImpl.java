package com.ruoyi.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.BusRydexxMapper;
import com.ruoyi.business.domain.BusRydexx;
import com.ruoyi.business.service.IBusRydexxService;

/**
 * 人员定额信息Service业务层处理
 * 
 * @author yaowei
 * @date 2021-02-23
 */
@Service
public class BusRydexxServiceImpl implements IBusRydexxService 
{
    @Autowired
    private BusRydexxMapper busRydexxMapper;

    /**
     * 查询人员定额信息
     * 
     * @param id 人员定额信息ID
     * @return 人员定额信息
     */
    @Override
    public BusRydexx selectBusRydexxById(Long id)
    {
        return busRydexxMapper.selectBusRydexxById(id);
    }

    /**
     * 查询人员定额信息列表
     * 
     * @param busRydexx 人员定额信息
     * @return 人员定额信息
     */
    @Override
    public List<BusRydexx> selectBusRydexxList(BusRydexx busRydexx)
    {
        return busRydexxMapper.selectBusRydexxList(busRydexx);
    }

    /**
     * 新增人员定额信息
     * 
     * @param busRydexx 人员定额信息
     * @return 结果
     */
    @Override
    public int insertBusRydexx(BusRydexx busRydexx)
    {
        return busRydexxMapper.insertBusRydexx(busRydexx);
    }

    /**
     * 修改人员定额信息
     * 
     * @param busRydexx 人员定额信息
     * @return 结果
     */
    @Override
    public int updateBusRydexx(BusRydexx busRydexx)
    {
        return busRydexxMapper.updateBusRydexx(busRydexx);
    }

    /**
     * 批量删除人员定额信息
     * 
     * @param ids 需要删除的人员定额信息ID
     * @return 结果
     */
    @Override
    public int deleteBusRydexxByIds(Long[] ids)
    {
        return busRydexxMapper.deleteBusRydexxByIds(ids);
    }

    /**
     * 删除人员定额信息信息
     * 
     * @param id 人员定额信息ID
     * @return 结果
     */
    @Override
    public int deleteBusRydexxById(Long id)
    {
        return busRydexxMapper.deleteBusRydexxById(id);
    }
}
