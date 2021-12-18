package com.ruoyi.carpool.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.carpool.mapper.PAddressMapper;
import com.ruoyi.carpool.domain.PAddress;
import com.ruoyi.carpool.service.IPAddressService;

/**
 * 地址详情Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-18
 */
@Service
public class PAddressServiceImpl implements IPAddressService 
{
    @Autowired
    private PAddressMapper pAddressMapper;

    /**
     * 查询地址详情
     * 
     * @param id 地址详情主键
     * @return 地址详情
     */
    @Override
    public PAddress selectPAddressById(Long id)
    {
        return pAddressMapper.selectPAddressById(id);
    }

    /**
     * 查询地址详情列表
     * 
     * @param pAddress 地址详情
     * @return 地址详情
     */
    @Override
    public List<PAddress> selectPAddressList(PAddress pAddress)
    {
        return pAddressMapper.selectPAddressList(pAddress);
    }

    /**
     * 新增地址详情
     * 
     * @param pAddress 地址详情
     * @return 结果
     */
    @Override
    public int insertPAddress(PAddress pAddress)
    {
        pAddress.setCreateTime(DateUtils.getNowDate());
        return pAddressMapper.insertPAddress(pAddress);
    }

    /**
     * 修改地址详情
     * 
     * @param pAddress 地址详情
     * @return 结果
     */
    @Override
    public int updatePAddress(PAddress pAddress)
    {
        pAddress.setUpdateTime(DateUtils.getNowDate());
        return pAddressMapper.updatePAddress(pAddress);
    }

    /**
     * 批量删除地址详情
     * 
     * @param ids 需要删除的地址详情主键
     * @return 结果
     */
    @Override
    public int deletePAddressByIds(Long[] ids)
    {
        return pAddressMapper.deletePAddressByIds(ids);
    }

    /**
     * 删除地址详情信息
     * 
     * @param id 地址详情主键
     * @return 结果
     */
    @Override
    public int deletePAddressById(Long id)
    {
        return pAddressMapper.deletePAddressById(id);
    }
}
