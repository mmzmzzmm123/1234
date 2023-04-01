package com.geek.system.service.impl;

import java.util.List;

import com.geek.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.geek.system.mapper.SysAppAccountMapper;
import com.geek.system.domain.SysAppAccount;
import com.geek.system.service.ISysAppAccountService;

/**
 * 系统APP账号Service业务层处理
 * 
 * @author xuek
 * @date 2023-03-29
 */
@Service
public class SysAppAccountServiceImpl implements ISysAppAccountService {
    @Autowired
    private SysAppAccountMapper sysAppAccountMapper;

    /**
     * 查询系统APP账号
     * 
     * @param id 系统APP账号主键
     * @return 系统APP账号
     */
    @Override
    public SysAppAccount selectSysAppAccountById(Long id){
        return sysAppAccountMapper.selectSysAppAccountById(id);
    }

    /**
     * 查询系统APP账号列表
     * 
     * @param sysAppAccount 系统APP账号
     * @return 系统APP账号
     */
    @Override
    public List<SysAppAccount> selectSysAppAccountList(SysAppAccount sysAppAccount){
        return sysAppAccountMapper.selectSysAppAccountList(sysAppAccount);
    }

    /**
     * 新增系统APP账号
     * 
     * @param sysAppAccount 系统APP账号
     * @return 结果
     */
    @Override
    public int insertSysAppAccount(SysAppAccount sysAppAccount){
        sysAppAccount.setAppid(IdUtils.simpleUUID().substring(0,12));
        return sysAppAccountMapper.insertSysAppAccount(sysAppAccount);
    }

    /**
     * 修改系统APP账号
     * 
     * @param sysAppAccount 系统APP账号
     * @return 结果
     */
    @Override
    public int updateSysAppAccount(SysAppAccount sysAppAccount){
        return sysAppAccountMapper.updateSysAppAccount(sysAppAccount);
    }

    /**
     * 批量删除系统APP账号
     * 
     * @param ids 需要删除的系统APP账号主键
     * @return 结果
     */
    @Override
    public int deleteSysAppAccountByIds(Long[] ids){
        return sysAppAccountMapper.deleteSysAppAccountByIds(ids);
    }

    /**
     * 删除系统APP账号信息
     * 
     * @param id 系统APP账号主键
     * @return 结果
     */
    @Override
    public int deleteSysAppAccountById(Long id){
        return sysAppAccountMapper.deleteSysAppAccountById(id);
    }
}
