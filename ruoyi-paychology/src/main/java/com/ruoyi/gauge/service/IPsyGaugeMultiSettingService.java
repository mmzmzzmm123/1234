package com.ruoyi.gauge.service;

import java.util.List;
import com.ruoyi.gauge.domain.PsyGaugeMultiSetting;

/**
 * 心理测评多维设置Service接口
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
public interface IPsyGaugeMultiSettingService 
{
    /**
     * 查询心理测评多维设置
     * 
     * @param id 心理测评多维设置主键
     * @return 心理测评多维设置
     */
    public PsyGaugeMultiSetting selectPsyGaugeMultiSettingById(Integer id);

    /**
     * 查询心理测评多维设置列表
     * 
     * @param psyGaugeMultiSetting 心理测评多维设置
     * @return 心理测评多维设置集合
     */
    public List<PsyGaugeMultiSetting> selectPsyGaugeMultiSettingList(PsyGaugeMultiSetting psyGaugeMultiSetting);

    /**
     * 新增心理测评多维设置
     * 
     * @param psyGaugeMultiSetting 心理测评多维设置
     * @return 结果
     */
    public int insertPsyGaugeMultiSetting(PsyGaugeMultiSetting psyGaugeMultiSetting);

    /**
     * 修改心理测评多维设置
     * 
     * @param psyGaugeMultiSetting 心理测评多维设置
     * @return 结果
     */
    public int updatePsyGaugeMultiSetting(PsyGaugeMultiSetting psyGaugeMultiSetting);

    /**
     * 批量删除心理测评多维设置
     * 
     * @param ids 需要删除的心理测评多维设置主键集合
     * @return 结果
     */
    public int deletePsyGaugeMultiSettingByIds(Integer[] ids);

    /**
     * 删除心理测评多维设置信息
     * 
     * @param id 心理测评多维设置主键
     * @return 结果
     */
    public int deletePsyGaugeMultiSettingById(Integer id);
}
