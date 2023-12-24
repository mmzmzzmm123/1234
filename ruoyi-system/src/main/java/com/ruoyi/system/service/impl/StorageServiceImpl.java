package com.ruoyi.system.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.StorageMapper;
import com.ruoyi.system.domain.Storage;
import com.ruoyi.system.service.IStorageService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-19
 */
@Service
public class StorageServiceImpl implements IStorageService 
{
    @Autowired
    private StorageMapper storageMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public Storage selectStorageById(Integer id)
    {
        return storageMapper.selectStorageById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param storage 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Storage> selectStorageList(Storage storage)
    {
        Long userId = SecurityUtils.getUserId();
        System.out.println("输出userId"+userId);
        if (userId!=1L&&userId!=100L){
            storage.setDeptId(SecurityUtils.getDeptId());
        }
        return storageMapper.selectStorageList(storage);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param storage 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertStorage(Storage storage)
    {
        Long userId = SecurityUtils.getUserId();
        storage.setUserId(userId);
        storage.setEntryDate(new Date());
        storage.setDeptId(SecurityUtils.getDeptId());
        return storageMapper.insertStorage(storage);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param storage 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateStorage(Storage storage)
    {
        if (storage.getKpStatus()==0){
            storage.setImgSrc("");
        }
        storage.setEditDate(new Date());
        Long userId = SecurityUtils.getUserId();
        storage.setUserId(userId);
        return storageMapper.updateStorage(storage);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteStorageByIds(Integer[] ids)
    {
        return storageMapper.deleteStorageByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteStorageById(Integer id)
    {
        return storageMapper.deleteStorageById(id);
    }

    @Override
    public List<SysUser> getUserList(SysUser user) {
        return storageMapper.getUserList(user);
    }
}
