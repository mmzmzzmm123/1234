package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AppEntertimerationMapper;
import com.ruoyi.system.domain.AppEntertimeration;
import com.ruoyi.system.service.IAppEntertimerationService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
@Service
public class AppEntertimerationServiceImpl implements IAppEntertimerationService 
{
    @Autowired
    private AppEntertimerationMapper appEntertimerationMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public AppEntertimeration selectAppEntertimerationByMId(Long mId)
    {
        return appEntertimerationMapper.selectAppEntertimerationByMId(mId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appEntertimeration 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AppEntertimeration> selectAppEntertimerationList(AppEntertimeration appEntertimeration)
    {
        return appEntertimerationMapper.selectAppEntertimerationList(appEntertimeration);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param appEntertimeration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAppEntertimeration(AppEntertimeration appEntertimeration)
    {
        return appEntertimerationMapper.insertAppEntertimeration(appEntertimeration);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param appEntertimeration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAppEntertimeration(AppEntertimeration appEntertimeration)
    {
        return appEntertimerationMapper.updateAppEntertimeration(appEntertimeration);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppEntertimerationByMIds(Long[] mIds)
    {
        return appEntertimerationMapper.deleteAppEntertimerationByMIds(mIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppEntertimerationByMId(Long mId)
    {
        return appEntertimerationMapper.deleteAppEntertimerationByMId(mId);
    }
}
