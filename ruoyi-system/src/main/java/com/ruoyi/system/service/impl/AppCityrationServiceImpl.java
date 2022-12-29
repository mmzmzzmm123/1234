package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AppCityrationMapper;
import com.ruoyi.system.domain.AppCityration;
import com.ruoyi.system.service.IAppCityrationService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
@Service
public class AppCityrationServiceImpl implements IAppCityrationService 
{
    @Autowired
    private AppCityrationMapper appCityrationMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public AppCityration selectAppCityrationByMId(Long mId)
    {
        return appCityrationMapper.selectAppCityrationByMId(mId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appCityration 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AppCityration> selectAppCityrationList(AppCityration appCityration)
    {
        return appCityrationMapper.selectAppCityrationList(appCityration);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param appCityration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAppCityration(AppCityration appCityration)
    {
        return appCityrationMapper.insertAppCityration(appCityration);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param appCityration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAppCityration(AppCityration appCityration)
    {
        return appCityrationMapper.updateAppCityration(appCityration);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppCityrationByMIds(Long[] mIds)
    {
        return appCityrationMapper.deleteAppCityrationByMIds(mIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppCityrationByMId(Long mId)
    {
        return appCityrationMapper.deleteAppCityrationByMId(mId);
    }
}
