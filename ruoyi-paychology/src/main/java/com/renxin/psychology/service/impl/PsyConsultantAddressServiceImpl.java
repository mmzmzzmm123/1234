package com.renxin.psychology.service.impl;

import java.util.List;
import com.renxin.common.utils.DateUtils;
import com.renxin.psychology.domain.PsyConsultantAddress;
import com.renxin.psychology.mapper.PsyConsultantAddressMapper;
import com.renxin.psychology.service.IPsyConsultantAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 咨询师地址Service业务层处理
 * 
 * @author renxin
 * @date 2024-06-20
 */
@Service
public class PsyConsultantAddressServiceImpl implements IPsyConsultantAddressService
{
    @Autowired
    private PsyConsultantAddressMapper psyConsultantAddressMapper;

    /**
     * 查询咨询师地址
     * 
     * @param addressId 咨询师地址主键
     * @return 咨询师地址
     */
    @Override
    public PsyConsultantAddress selectPsyConsultantAddressByAddressId(Long addressId)
    {
        return psyConsultantAddressMapper.selectPsyConsultantAddressByAddressId(addressId);
    }

    /**
     * 查询咨询师地址列表
     * 
     * @param psyConsultantAddress 咨询师地址
     * @return 咨询师地址
     */
    @Override
    public List<PsyConsultantAddress> selectPsyConsultantAddressList(PsyConsultantAddress psyConsultantAddress)
    {
        return psyConsultantAddressMapper.selectPsyConsultantAddressList(psyConsultantAddress);
    }

    /**
     * 新增咨询师地址
     * 
     * @param psyConsultantAddress 咨询师地址
     * @return 结果
     */
    @Override
    public int insertPsyConsultantAddress(PsyConsultantAddress psyConsultantAddress)
    {
        psyConsultantAddress.setCreateTime(DateUtils.getNowDate());
        return psyConsultantAddressMapper.insertPsyConsultantAddress(psyConsultantAddress);
    }

    /**
     * 修改咨询师地址
     * 
     * @param psyConsultantAddress 咨询师地址
     * @return 结果
     */
    @Override
    public int updatePsyConsultantAddress(PsyConsultantAddress psyConsultantAddress)
    {
        psyConsultantAddress.setUpdateTime(DateUtils.getNowDate());
        return psyConsultantAddressMapper.updatePsyConsultantAddress(psyConsultantAddress);
    }

    /**
     * 批量删除咨询师地址
     * 
     * @param addressIds 需要删除的咨询师地址主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantAddressByAddressIds(Long[] addressIds)
    {
        return psyConsultantAddressMapper.deletePsyConsultantAddressByAddressIds(addressIds);
    }

    /**
     * 删除咨询师地址信息
     * 
     * @param addressId 咨询师地址主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantAddressByAddressId(Long addressId)
    {
        return psyConsultantAddressMapper.deletePsyConsultantAddressByAddressId(addressId);
    }
}
