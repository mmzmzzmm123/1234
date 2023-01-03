package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AppEntertimeration;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public interface AppEntertimerationMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public AppEntertimeration selectAppEntertimerationByMId(Long mId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appEntertimeration 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AppEntertimeration> selectAppEntertimerationList(AppEntertimeration appEntertimeration);

    /**
     * 新增【请填写功能名称】
     * 
     * @param appEntertimeration 【请填写功能名称】
     * @return 结果
     */
    public int insertAppEntertimeration(AppEntertimeration appEntertimeration);

    /**
     * 修改【请填写功能名称】
     * 
     * @param appEntertimeration 【请填写功能名称】
     * @return 结果
     */
    public int updateAppEntertimeration(AppEntertimeration appEntertimeration);

    /**
     * 删除【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteAppEntertimerationByMId(Long mId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppEntertimerationByMIds(Long[] mIds);
}
