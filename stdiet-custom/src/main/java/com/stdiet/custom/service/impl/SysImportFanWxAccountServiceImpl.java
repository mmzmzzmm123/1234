package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysImportFanWxAccountMapper;
import com.stdiet.custom.domain.SysImportFanWxAccount;
import com.stdiet.custom.service.ISysImportFanWxAccountService;

/**
 * 导粉记录中对应微信记录Service业务层处理
 *
 * @author xzj
 * @date 2021-05-17
 */
@Service
public class SysImportFanWxAccountServiceImpl implements ISysImportFanWxAccountService
{
    @Autowired
    private SysImportFanWxAccountMapper sysImportFanWxAccountMapper;

    /**
     * 查询导粉记录中对应微信记录
     *
     * @param id 导粉记录中对应微信记录ID
     * @return 导粉记录中对应微信记录
     */
    @Override
    public SysImportFanWxAccount selectSysImportFanWxAccountById(Long id)
    {
        return sysImportFanWxAccountMapper.selectSysImportFanWxAccountById(id);
    }

    /**
     * 查询导粉记录中对应微信记录列表
     *
     * @param sysImportFanWxAccount 导粉记录中对应微信记录
     * @return 导粉记录中对应微信记录
     */
    @Override
    public List<SysImportFanWxAccount> selectSysImportFanWxAccountList(SysImportFanWxAccount sysImportFanWxAccount)
    {
        return sysImportFanWxAccountMapper.selectSysImportFanWxAccountList(sysImportFanWxAccount);
    }

    /**
     * 新增导粉记录中对应微信记录
     *
     * @param sysImportFanWxAccount 导粉记录中对应微信记录
     * @return 结果
     */
    @Override
    public int insertSysImportFanWxAccount(SysImportFanWxAccount sysImportFanWxAccount)
    {
        sysImportFanWxAccount.setCreateTime(DateUtils.getNowDate());
        return sysImportFanWxAccountMapper.insertSysImportFanWxAccount(sysImportFanWxAccount);
    }

    /**
     * 修改导粉记录中对应微信记录
     *
     * @param sysImportFanWxAccount 导粉记录中对应微信记录
     * @return 结果
     */
    @Override
    public int updateSysImportFanWxAccount(SysImportFanWxAccount sysImportFanWxAccount)
    {
        sysImportFanWxAccount.setUpdateTime(DateUtils.getNowDate());
        return sysImportFanWxAccountMapper.updateSysImportFanWxAccount(sysImportFanWxAccount);
    }

    /**
     * 批量删除导粉记录中对应微信记录
     *
     * @param ids 需要删除的导粉记录中对应微信记录ID
     * @return 结果
     */
    @Override
    public int deleteSysImportFanWxAccountByIds(Long[] ids)
    {
        return sysImportFanWxAccountMapper.deleteSysImportFanWxAccountByIds(ids);
    }

    /**
     * 删除导粉记录中对应微信记录信息
     *
     * @param id 导粉记录中对应微信记录ID
     * @return 结果
     */
    @Override
    public int deleteSysImportFanWxAccountById(Long id)
    {
        return sysImportFanWxAccountMapper.deleteSysImportFanWxAccountById(id);
    }

    /**
     * 根据导粉记录ID、微信ID查询对应微信记录
     * @param sysImportFanWxAccount
     * @return
     */
    public SysImportFanWxAccount getWxAccountByFanRecordId(SysImportFanWxAccount sysImportFanWxAccount){
        return sysImportFanWxAccountMapper.getWxAccountByFanRecordId(sysImportFanWxAccount);
    }
}