package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AppNetrationMapper;
import com.ruoyi.system.domain.AppNetration;
import com.ruoyi.system.service.IAppNetrationService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
@Service
public class AppNetrationServiceImpl implements IAppNetrationService 
{
    @Autowired
    private AppNetrationMapper appNetrationMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public AppNetration selectAppNetrationByMId(Long mId)
    {
        return appNetrationMapper.selectAppNetrationByMId(mId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appNetration 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AppNetration> selectAppNetrationList(AppNetration appNetration)
    {
        return appNetrationMapper.selectAppNetrationList(appNetration);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param appNetration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAppNetration(AppNetration appNetration)
    {
        return appNetrationMapper.insertAppNetration(appNetration);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param appNetration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAppNetration(AppNetration appNetration)
    {
        return appNetrationMapper.updateAppNetration(appNetration);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppNetrationByMIds(Long[] mIds)
    {
        return appNetrationMapper.deleteAppNetrationByMIds(mIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppNetrationByMId(Long mId)
    {
        return appNetrationMapper.deleteAppNetrationByMId(mId);
    }
}
