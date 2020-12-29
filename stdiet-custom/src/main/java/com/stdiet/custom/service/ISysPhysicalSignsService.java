package com.stdiet.custom.service;

import java.util.List;
import com.stdiet.custom.domain.SysPhysicalSigns;

/**
 * 体征Service接口
 *
 * @author wonder
 * @date 2020-12-23
 */
public interface ISysPhysicalSignsService
{
    /**
     * 查询体征
     *
     * @param id 体征ID
     * @return 体征
     */
    public SysPhysicalSigns selectSysPhysicalSignsById(Long id);

    /**
     * 查询体征列表
     *
     * @param sysPhysicalSigns 体征
     * @return 体征集合
     */
    public List<SysPhysicalSigns> selectSysPhysicalSignsList(SysPhysicalSigns sysPhysicalSigns);

    /**
     * 新增体征
     *
     * @param sysPhysicalSigns 体征
     * @return 结果
     */
    public int insertSysPhysicalSigns(SysPhysicalSigns sysPhysicalSigns);

    /**
     * 修改体征
     *
     * @param sysPhysicalSigns 体征
     * @return 结果
     */
    public int updateSysPhysicalSigns(SysPhysicalSigns sysPhysicalSigns);

    /**
     * 批量删除体征
     *
     * @param ids 需要删除的体征ID
     * @return 结果
     */
    public int deleteSysPhysicalSignsByIds(Long[] ids);

    /**
     * 删除体征信息
     *
     * @param id 体征ID
     * @return 结果
     */
    public int deleteSysPhysicalSignsById(Long id);
}