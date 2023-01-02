package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AppNewpeoplerationMapper;
import com.ruoyi.system.domain.AppNewpeopleration;
import com.ruoyi.system.service.IAppNewpeoplerationService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
@Service
public class AppNewpeoplerationServiceImpl implements IAppNewpeoplerationService 
{
    @Autowired
    private AppNewpeoplerationMapper appNewpeoplerationMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public AppNewpeopleration selectAppNewpeoplerationByMId(Long mId)
    {
        return appNewpeoplerationMapper.selectAppNewpeoplerationByMId(mId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appNewpeopleration 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<AppNewpeopleration> selectAppNewpeoplerationList(AppNewpeopleration appNewpeopleration)
    {
        return appNewpeoplerationMapper.selectAppNewpeoplerationList(appNewpeopleration);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param appNewpeopleration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAppNewpeopleration(AppNewpeopleration appNewpeopleration)
    {
        return appNewpeoplerationMapper.insertAppNewpeopleration(appNewpeopleration);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param appNewpeopleration 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAppNewpeopleration(AppNewpeopleration appNewpeopleration)
    {
        return appNewpeoplerationMapper.updateAppNewpeopleration(appNewpeopleration);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppNewpeoplerationByMIds(Long[] mIds)
    {
        return appNewpeoplerationMapper.deleteAppNewpeoplerationByMIds(mIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteAppNewpeoplerationByMId(Long mId)
    {
        return appNewpeoplerationMapper.deleteAppNewpeoplerationByMId(mId);
    }
}
