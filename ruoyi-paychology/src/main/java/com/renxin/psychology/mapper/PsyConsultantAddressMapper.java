package com.renxin.psychology.mapper;

import com.renxin.psychology.domain.PsyConsultantAddress;

import java.util.List;

/**
 * 咨询师地址Mapper接口
 * 
 * @author renxin
 * @date 2024-06-20
 */
public interface PsyConsultantAddressMapper 
{
    /**
     * 查询咨询师地址
     * 
     * @param addressId 咨询师地址主键
     * @return 咨询师地址
     */
    public PsyConsultantAddress selectPsyConsultantAddressByAddressId(Long addressId);

    /**
     * 查询咨询师地址列表
     * 
     * @param psyConsultantAddress 咨询师地址
     * @return 咨询师地址集合
     */
    public List<PsyConsultantAddress> selectPsyConsultantAddressList(PsyConsultantAddress psyConsultantAddress);

    /**
     * 新增咨询师地址
     * 
     * @param psyConsultantAddress 咨询师地址
     * @return 结果
     */
    public int insertPsyConsultantAddress(PsyConsultantAddress psyConsultantAddress);

    /**
     * 修改咨询师地址
     * 
     * @param psyConsultantAddress 咨询师地址
     * @return 结果
     */
    public int updatePsyConsultantAddress(PsyConsultantAddress psyConsultantAddress);

    /**
     * 删除咨询师地址
     * 
     * @param addressId 咨询师地址主键
     * @return 结果
     */
    public int deletePsyConsultantAddressByAddressId(Long addressId);

    /**
     * 批量删除咨询师地址
     * 
     * @param addressIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsyConsultantAddressByAddressIds(Long[] addressIds);
}
