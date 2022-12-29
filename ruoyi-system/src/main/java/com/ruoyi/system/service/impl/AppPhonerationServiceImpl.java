package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AppPhonerationMapper;
import com.ruoyi.system.domain.AppPhoneration;
import com.ruoyi.system.service.IAppPhonerationService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
@Service
public class AppPhonerationServiceImpl implements IAppPhonerationService 
{
    @Autowired
    private AppPhonerationMapper appPhonerationMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public AppPhoneration selectAppPhonerationByMId(Long mId)
    {
        return appPhonerationMapper.selectAppPhonerationByMId(mId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appPhoneration 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AppPhoneration> selectAppPhonerationList(AppPhoneration appPhoneration)
    {
        return appPhonerationMapper.selectAppPhonerationList(appPhoneration);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param appPhoneration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAppPhoneration(AppPhoneration appPhoneration)
    {
        return appPhonerationMapper.insertAppPhoneration(appPhoneration);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param appPhoneration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAppPhoneration(AppPhoneration appPhoneration)
    {
        return appPhonerationMapper.updateAppPhoneration(appPhoneration);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppPhonerationByMIds(Long[] mIds)
    {
        return appPhonerationMapper.deleteAppPhonerationByMIds(mIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppPhonerationByMId(Long mId)
    {
        return appPhonerationMapper.deleteAppPhonerationByMId(mId);
    }
}
