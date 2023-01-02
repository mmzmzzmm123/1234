package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AppOperatorrationMapper;
import com.ruoyi.system.domain.AppOperatorration;
import com.ruoyi.system.service.IAppOperatorrationService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
@Service
public class AppOperatorrationServiceImpl implements IAppOperatorrationService 
{
    @Autowired
    private AppOperatorrationMapper appOperatorrationMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public AppOperatorration selectAppOperatorrationByMId(Long mId)
    {
        return appOperatorrationMapper.selectAppOperatorrationByMId(mId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appOperatorration 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AppOperatorration> selectAppOperatorrationList(AppOperatorration appOperatorration)
    {
        return appOperatorrationMapper.selectAppOperatorrationList(appOperatorration);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param appOperatorration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAppOperatorration(AppOperatorration appOperatorration)
    {
        return appOperatorrationMapper.insertAppOperatorration(appOperatorration);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param appOperatorration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAppOperatorration(AppOperatorration appOperatorration)
    {
        return appOperatorrationMapper.updateAppOperatorration(appOperatorration);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppOperatorrationByMIds(Long[] mIds)
    {
        return appOperatorrationMapper.deleteAppOperatorrationByMIds(mIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppOperatorrationByMId(Long mId)
    {
        return appOperatorrationMapper.deleteAppOperatorrationByMId(mId);
    }
}
