package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.AppIosration;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public interface IAppIosrationService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public AppIosration selectAppIosrationByMId(Long mId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appIosration 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AppIosration> selectAppIosrationList(AppIosration appIosration);

    /**
     * 新增【请填写功能名称】
     * 
     * @param appIosration 【请填写功能名称】
     * @return 结果
     */
    public int insertAppIosration(AppIosration appIosration);

    /**
     * 修改【请填写功能名称】
     * 
     * @param appIosration 【请填写功能名称】
     * @return 结果
     */
    public int updateAppIosration(AppIosration appIosration);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteAppIosrationByMIds(Long[] mIds);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteAppIosrationByMId(Long mId);
}
