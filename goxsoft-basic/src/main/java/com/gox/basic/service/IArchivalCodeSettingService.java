package com.gox.basic.service;

import com.gox.basic.domain.ArchivalCodeNum;
import com.gox.basic.domain.ArchivalCodeSetting;

import java.util.List;

/**
 * 档号设置Service接口
 *
 * @author gox
 * @date 2021-01-23
 */
public interface IArchivalCodeSettingService {
    /**
     * 查询档号设置
     *
     * @param id 档号设置ID
     * @return 档号设置
     */
    public ArchivalCodeSetting selectArchivalCodeSettingById(Long id);

    int updateArchivalCodeNum(String archival, int num);

    int insertArchivalCodeNum(ArchivalCodeNum archivalCodeNum);

    public ArchivalCodeSetting selectArchivalCsByNodeIdAndDeptId(Long nodeId, Long deptId);
    /**
     * 查询档号设置列表
     *
     * @param archivalCodeSetting 档号设置
     * @return 档号设置集合
     */
    public List<ArchivalCodeSetting> selectArchivalCodeSettingList(ArchivalCodeSetting archivalCodeSetting);

    /**
     * 新增档号设置
     *
     * @param archivalCodeSetting 档号设置
     * @return 结果
     */
    public int insertArchivalCodeSetting(ArchivalCodeSetting archivalCodeSetting);

    /**
     * 修改档号设置
     *
     * @param archivalCodeSetting 档号设置
     * @return 结果
     */
    public int updateArchivalCodeSetting(ArchivalCodeSetting archivalCodeSetting);

    /**
     * 批量删除档号设置
     *
     * @param ids 需要删除的档号设置ID
     * @return 结果
     */
    public int deleteArchivalCodeSettingByIds(Long[] ids);

    /**
     * 删除档号设置信息
     *
     * @param id 档号设置ID
     * @return 结果
     */
    public int deleteArchivalCodeSettingById(Long id);
}
