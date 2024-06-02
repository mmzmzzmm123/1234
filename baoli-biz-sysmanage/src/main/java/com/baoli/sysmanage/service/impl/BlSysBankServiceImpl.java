package com.baoli.sysmanage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BlSysBankMapper;
import com.baoli.sysmanage.domain.BlSysBank;
import com.baoli.sysmanage.service.IBlSysBankService;

/**
 * 银行管理Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-08
 */
@Service
public class BlSysBankServiceImpl implements IBlSysBankService 
{
    @Autowired
    private BlSysBankMapper blSysBankMapper;

    /**
     * 查询银行管理
     * 
     * @param id 银行管理主键
     * @return 银行管理
     */
    @Override
    public BlSysBank selectBlSysBankById(Long id)
    {
        return blSysBankMapper.selectBlSysBankById(id);
    }

    /**
     * 查询银行管理列表
     * 
     * @param blSysBank 银行管理
     * @return 银行管理
     */
    @Override
    public List<BlSysBank> selectBlSysBankList(BlSysBank blSysBank)
    {
        return blSysBankMapper.selectBlSysBankList(blSysBank);
    }

    /**
     * 新增银行管理
     * 
     * @param blSysBank 银行管理
     * @return 结果
     */
    @Override
    public int insertBlSysBank(BlSysBank blSysBank)
    {
        blSysBank.setCreateTime(DateUtils.getNowDate());
        return blSysBankMapper.insertBlSysBank(blSysBank);
    }

    /**
     * 修改银行管理
     * 
     * @param blSysBank 银行管理
     * @return 结果
     */
    @Override
    public int updateBlSysBank(BlSysBank blSysBank)
    {
        blSysBank.setUpdateTime(DateUtils.getNowDate());
        return blSysBankMapper.updateBlSysBank(blSysBank);
    }

    /**
     * 批量删除银行管理
     * 
     * @param ids 需要删除的银行管理主键
     * @return 结果
     */
    @Override
    public int deleteBlSysBankByIds(Long[] ids)
    {
        return blSysBankMapper.deleteBlSysBankByIds(ids);
    }

    /**
     * 删除银行管理信息
     * 
     * @param id 银行管理主键
     * @return 结果
     */
    @Override
    public int deleteBlSysBankById(Long id)
    {
        return blSysBankMapper.deleteBlSysBankById(id);
    }
}
