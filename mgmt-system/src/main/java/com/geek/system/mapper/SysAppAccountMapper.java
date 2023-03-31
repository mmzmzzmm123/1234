package com.geek.system.mapper;

import java.util.List;
import com.geek.system.domain.SysAppAccount;

/**
 * 系统APP账号Mapper接口
 * 
 * @author xuek
 * @date 2023-03-29
 */
public interface SysAppAccountMapper{
    /**
     * 查询系统APP账号
     * 
     * @param id 系统APP账号主键
     * @return 系统APP账号
     */
    public SysAppAccount selectSysAppAccountById(Long id);

    /**
     * 查询系统APP账号列表
     * 
     * @param sysAppAccount 系统APP账号
     * @return 系统APP账号集合
     */
    public List<SysAppAccount> selectSysAppAccountList(SysAppAccount sysAppAccount);

    /**
     * 新增系统APP账号
     * 
     * @param sysAppAccount 系统APP账号
     * @return 结果
     */
    public int insertSysAppAccount(SysAppAccount sysAppAccount);

    /**
     * 修改系统APP账号
     * 
     * @param sysAppAccount 系统APP账号
     * @return 结果
     */
    public int updateSysAppAccount(SysAppAccount sysAppAccount);

    /**
     * 删除系统APP账号
     * 
     * @param id 系统APP账号主键
     * @return 结果
     */
    public int deleteSysAppAccountById(Long id);

    /**
     * 批量删除系统APP账号
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysAppAccountByIds(Long[] ids);
}
