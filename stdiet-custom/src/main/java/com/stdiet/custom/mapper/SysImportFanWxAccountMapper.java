package com.stdiet.custom.mapper;

import java.util.List;
import java.util.Map;

import com.stdiet.custom.domain.SysImportFanWxAccount;

/**
 * 导粉记录中对应微信记录Mapper接口
 *
 * @author xzj
 * @date 2021-05-17
 */
public interface SysImportFanWxAccountMapper
{
    /**
     * 查询导粉记录中对应微信记录
     *
     * @param id 导粉记录中对应微信记录ID
     * @return 导粉记录中对应微信记录
     */
    public SysImportFanWxAccount selectSysImportFanWxAccountById(Long id);

    /**
     * 查询导粉记录中对应微信记录列表
     *
     * @param sysImportFanWxAccount 导粉记录中对应微信记录
     * @return 导粉记录中对应微信记录集合
     */
    public List<SysImportFanWxAccount> selectSysImportFanWxAccountList(SysImportFanWxAccount sysImportFanWxAccount);

    /**
     * 新增导粉记录中对应微信记录
     *
     * @param sysImportFanWxAccount 导粉记录中对应微信记录
     * @return 结果
     */
    public int insertSysImportFanWxAccount(SysImportFanWxAccount sysImportFanWxAccount);

    /**
     * 修改导粉记录中对应微信记录
     *
     * @param sysImportFanWxAccount 导粉记录中对应微信记录
     * @return 结果
     */
    public int updateSysImportFanWxAccount(SysImportFanWxAccount sysImportFanWxAccount);

    /**
     * 删除导粉记录中对应微信记录
     *
     * @param id 导粉记录中对应微信记录ID
     * @return 结果
     */
    public int deleteSysImportFanWxAccountById(Long id);

    /**
     * 批量删除导粉记录中对应微信记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysImportFanWxAccountByIds(Long[] ids);

    /**
     * 根据导粉记录ID、微信ID查询对应微信记录
     * @param sysImportFanWxAccount
     * @return
     */
    public SysImportFanWxAccount getWxAccountByFanRecordId(SysImportFanWxAccount sysImportFanWxAccount);

    /**
     * 根据微信号查询
     * @param sysImportFanWxAccount
     * @return
     */
    List<Map<String,Object>> getTotalImportFanNum(SysImportFanWxAccount sysImportFanWxAccount);
}