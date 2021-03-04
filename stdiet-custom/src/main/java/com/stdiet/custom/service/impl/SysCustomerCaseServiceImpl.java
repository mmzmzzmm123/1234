package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysCustomerCaseMapper;
import com.stdiet.custom.domain.SysCustomerCase;
import com.stdiet.custom.service.ISysCustomerCaseService;

/**
 * 客户案例管理Service业务层处理
 *
 * @author xiezhijun
 * @date 2021-03-04
 */
@Service
public class SysCustomerCaseServiceImpl implements ISysCustomerCaseService
{
    @Autowired
    private SysCustomerCaseMapper sysCustomerCaseMapper;

    /**
     * 查询客户案例管理
     *
     * @param id 客户案例管理ID
     * @return 客户案例管理
     */
    @Override
    public SysCustomerCase selectSysCustomerCaseById(Long id)
    {
        return sysCustomerCaseMapper.selectSysCustomerCaseById(id);
    }

    /**
     * 查询客户案例管理列表
     *
     * @param sysCustomerCase 客户案例管理
     * @return 客户案例管理
     */
    @Override
    public List<SysCustomerCase> selectSysCustomerCaseList(SysCustomerCase sysCustomerCase)
    {
        return sysCustomerCaseMapper.selectSysCustomerCaseList(sysCustomerCase);
    }

    /**
     * 新增客户案例管理
     *
     * @param sysCustomerCase 客户案例管理
     * @return 结果
     */
    @Override
    public int insertSysCustomerCase(SysCustomerCase sysCustomerCase)
    {
        sysCustomerCase.setCreateTime(DateUtils.getNowDate());
        return sysCustomerCaseMapper.insertSysCustomerCase(sysCustomerCase);
    }

    /**
     * 修改客户案例管理
     *
     * @param sysCustomerCase 客户案例管理
     * @return 结果
     */
    @Override
    public int updateSysCustomerCase(SysCustomerCase sysCustomerCase)
    {
        sysCustomerCase.setUpdateTime(DateUtils.getNowDate());
        return sysCustomerCaseMapper.updateSysCustomerCase(sysCustomerCase);
    }

    /**
     * 批量删除客户案例管理
     *
     * @param ids 需要删除的客户案例管理ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerCaseByIds(Long[] ids)
    {
        return sysCustomerCaseMapper.deleteSysCustomerCaseByIds(ids);
    }

    /**
     * 删除客户案例管理信息
     *
     * @param id 客户案例管理ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerCaseById(Long id)
    {
        return sysCustomerCaseMapper.deleteSysCustomerCaseById(id);
    }
}