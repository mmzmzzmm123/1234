package com.ruoyi.custom.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.custom.mapper.SysContractMapper;
import com.ruoyi.custom.domain.SysContract;
import com.ruoyi.custom.service.ISysContractService;

/**
 * 合同Service业务层处理
 * 
 * @author wonder
 * @date 2020-10-23
 */
@Service
public class SysContractServiceImpl implements ISysContractService 
{
    @Autowired
    private SysContractMapper sysContractMapper;

    /**
     * 查询合同
     * 
     * @param id 合同ID
     * @return 合同
     */
    @Override
    public SysContract selectSysContractById(Long id)
    {
        return sysContractMapper.selectSysContractById(id);
    }

    /**
     * 查询合同列表
     * 
     * @param sysContract 合同
     * @return 合同
     */
    @Override
    public List<SysContract> selectSysContractList(SysContract sysContract)
    {
        return sysContractMapper.selectSysContractList(sysContract);
    }

    /**
     * 新增合同
     * 
     * @param sysContract 合同
     * @return 结果
     */
    @Override
    public int insertSysContract(SysContract sysContract)
    {
        sysContract.setCreateTime(DateUtils.getNowDate());
        return sysContractMapper.insertSysContract(sysContract);
    }

    /**
     * 修改合同
     * 
     * @param sysContract 合同
     * @return 结果
     */
    @Override
    public int updateSysContract(SysContract sysContract)
    {
        sysContract.setUpdateTime(DateUtils.getNowDate());
        return sysContractMapper.updateSysContract(sysContract);
    }

    /**
     * 批量删除合同
     * 
     * @param ids 需要删除的合同ID
     * @return 结果
     */
    @Override
    public int deleteSysContractByIds(Long[] ids)
    {
        return sysContractMapper.deleteSysContractByIds(ids);
    }

    /**
     * 删除合同信息
     * 
     * @param id 合同ID
     * @return 结果
     */
    @Override
    public int deleteSysContractById(Long id)
    {
        return sysContractMapper.deleteSysContractById(id);
    }
}
