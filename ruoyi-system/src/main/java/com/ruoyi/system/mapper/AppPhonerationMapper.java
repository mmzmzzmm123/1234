package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AppPhoneration;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-29
 */
public interface AppPhonerationMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public AppPhoneration selectAppPhonerationByMId(Long mId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param appPhoneration 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AppPhoneration> selectAppPhonerationList(AppPhoneration appPhoneration);

    /**
     * 新增【请填写功能名称】
     * 
     * @param appPhoneration 【请填写功能名称】
     * @return 结果
     */
    public int insertAppPhoneration(AppPhoneration appPhoneration);

    /**
     * 修改【请填写功能名称】
     * 
     * @param appPhoneration 【请填写功能名称】
     * @return 结果
     */
    public int updateAppPhoneration(AppPhoneration appPhoneration);

    /**
     * 删除【请填写功能名称】
     * 
     * @param mId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteAppPhonerationByMId(Long mId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param mIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAppPhonerationByMIds(Long[] mIds);
}
