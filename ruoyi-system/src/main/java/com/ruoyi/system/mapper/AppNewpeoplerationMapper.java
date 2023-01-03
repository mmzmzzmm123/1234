package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AppNewpeopleration;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public interface AppNewpeoplerationMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public AppNewpeopleration selectAppNewpeoplerationByMId(Long mId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appNewpeopleration 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AppNewpeopleration> selectAppNewpeoplerationList(AppNewpeopleration appNewpeopleration);

    /**
     * 新增【请填写功能名称】
     * 
     * @param appNewpeopleration 【请填写功能名称】
     * @return 结果
     */
    public int insertAppNewpeopleration(AppNewpeopleration appNewpeopleration);

    /**
     * 修改【请填写功能名称】
     * 
     * @param appNewpeopleration 【请填写功能名称】
     * @return 结果
     */
    public int updateAppNewpeopleration(AppNewpeopleration appNewpeopleration);

    /**
     * 删除【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteAppNewpeoplerationByMId(Long mId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppNewpeoplerationByMIds(Long[] mIds);
}
