package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AppIosrationMapper;
import com.ruoyi.system.domain.AppIosration;
import com.ruoyi.system.service.IAppIosrationService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
@Service
public class AppIosrationServiceImpl implements IAppIosrationService 
{
    @Autowired
    private AppIosrationMapper appIosrationMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public AppIosration selectAppIosrationByMId(Long mId)
    {
        return appIosrationMapper.selectAppIosrationByMId(mId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appIosration 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AppIosration> selectAppIosrationList(AppIosration appIosration)
    {
        return appIosrationMapper.selectAppIosrationList(appIosration);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param appIosration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAppIosration(AppIosration appIosration)
    {
        return appIosrationMapper.insertAppIosration(appIosration);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param appIosration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAppIosration(AppIosration appIosration)
    {
        return appIosrationMapper.updateAppIosration(appIosration);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppIosrationByMIds(Long[] mIds)
    {
        return appIosrationMapper.deleteAppIosrationByMIds(mIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppIosrationByMId(Long mId)
    {
        return appIosrationMapper.deleteAppIosrationByMId(mId);
    }
}
