package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysCustomerPhysicalSignsMapper;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.service.ISysCustomerPhysicalSignsService;

/**
 * 客户体征信息Service业务层处理
 *
 * @author xzj
 * @date 2020-12-31
 */
@Service
public class SysCustomerPhysicalSignsServiceImpl implements ISysCustomerPhysicalSignsService
{
    @Autowired
    private SysCustomerPhysicalSignsMapper sysCustomerPhysicalSignsMapper;

    /**
     * 查询客户体征信息
     *
     * @param id 客户体征信息ID
     * @return 客户体征信息
     */
    @Override
    public SysCustomerPhysicalSigns selectSysCustomerPhysicalSignsById(Long id)
    {
        return sysCustomerPhysicalSignsMapper.selectSysCustomerPhysicalSignsById(id);
    }

    /**
     * 查询客户体征信息列表
     *
     * @param sysCustomerPhysicalSigns 客户体征信息
     * @return 客户体征信息
     */
    @Override
    public List<SysCustomerPhysicalSigns> selectSysCustomerPhysicalSignsList(SysCustomerPhysicalSigns sysCustomerPhysicalSigns)
    {
        return sysCustomerPhysicalSignsMapper.selectSysCustomerPhysicalSignsList(sysCustomerPhysicalSigns);
    }

    /**
     * 新增客户体征信息
     *
     * @param sysCustomerPhysicalSigns 客户体征信息
     * @return 结果
     */
    @Override
    public int insertSysCustomerPhysicalSigns(SysCustomerPhysicalSigns sysCustomerPhysicalSigns)
    {
        sysCustomerPhysicalSigns.setCreateTime(DateUtils.getNowDate());
        return sysCustomerPhysicalSignsMapper.insertSysCustomerPhysicalSigns(sysCustomerPhysicalSigns);
    }

    /**
     * 修改客户体征信息
     *
     * @param sysCustomerPhysicalSigns 客户体征信息
     * @return 结果
     */
    @Override
    public int updateSysCustomerPhysicalSigns(SysCustomerPhysicalSigns sysCustomerPhysicalSigns)
    {
        sysCustomerPhysicalSigns.setUpdateTime(DateUtils.getNowDate());
        return sysCustomerPhysicalSignsMapper.updateSysCustomerPhysicalSigns(sysCustomerPhysicalSigns);
    }

    /**
     * 批量删除客户体征信息
     *
     * @param ids 需要删除的客户体征信息ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerPhysicalSignsByIds(Long[] ids)
    {
        return sysCustomerPhysicalSignsMapper.deleteSysCustomerPhysicalSignsByIds(ids);
    }

    /**
     * 删除客户体征信息信息
     *
     * @param id 客户体征信息ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerPhysicalSignsById(Long id)
    {
        return sysCustomerPhysicalSignsMapper.deleteSysCustomerPhysicalSignsById(id);
    }
}