package com.geek.system.mapper;

import java.util.List;
import com.geek.system.domain.SysBanner;

/**
 * banner配置Mapper接口
 * 
 * @author xuek
 * @date 2023-03-30
 */
public interface SysBannerMapper{
    /**
     * 查询banner配置
     * 
     * @param id banner配置主键
     * @return banner配置
     */
    public SysBanner selectSysBannerById(Long id);

    /**
     * 查询banner配置列表
     * 
     * @param sysBanner banner配置
     * @return banner配置集合
     */
    public List<SysBanner> selectSysBannerList(SysBanner sysBanner);

    /**
     * 新增banner配置
     * 
     * @param sysBanner banner配置
     * @return 结果
     */
    public int insertSysBanner(SysBanner sysBanner);

    /**
     * 修改banner配置
     * 
     * @param sysBanner banner配置
     * @return 结果
     */
    public int updateSysBanner(SysBanner sysBanner);

    /**
     * 删除banner配置
     * 
     * @param id banner配置主键
     * @return 结果
     */
    public int deleteSysBannerById(Long id);

    /**
     * 批量删除banner配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysBannerByIds(Long[] ids);
}
