package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.Storage;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-12-19
 */
public interface IStorageService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public Storage selectStorageById(Integer id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param storage 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Storage> selectStorageList(Storage storage);

    /**
     * 新增【请填写功能名称】
     * 
     * @param storage 【请填写功能名称】
     * @return 结果
     */
    public int insertStorage(Storage storage);

    /**
     * 修改【请填写功能名称】
     * 
     * @param storage 【请填写功能名称】
     * @return 结果
     */
    public int updateStorage(Storage storage);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteStorageByIds(Integer[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteStorageById(Integer id);

    List<SysUser> getUserList(SysUser user);
}
