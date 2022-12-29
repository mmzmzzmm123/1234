package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AppOperatorration;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public interface AppOperatorrationMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public AppOperatorration selectAppOperatorrationByMId(Long mId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appOperatorration 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AppOperatorration> selectAppOperatorrationList(AppOperatorration appOperatorration);

    /**
     * 新增【请填写功能名称】
     * 
     * @param appOperatorration 【请填写功能名称】
     * @return 结果
     */
    public int insertAppOperatorration(AppOperatorration appOperatorration);

    /**
     * 修改【请填写功能名称】
     * 
     * @param appOperatorration 【请填写功能名称】
     * @return 结果
     */
    public int updateAppOperatorration(AppOperatorration appOperatorration);

    /**
     * 删除【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteAppOperatorrationByMId(Long mId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppOperatorrationByMIds(Long[] mIds);
}
