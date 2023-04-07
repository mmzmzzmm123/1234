package com.ruoyi.gauge.service;

import java.util.List;
import com.ruoyi.gauge.domain.PsyGauge;

/**
 * 心理测评Service接口
 * 
 * @author ruoyi
 * @date 2022-08-30
 */
public interface IPsyGaugeService 
{
    /**
     * 查询心理测评
     * 
     * @param id 心理测评主键
     * @return 心理测评
     */
    public PsyGauge selectPsyGaugeById(Integer id);

    /**
     * 查询心理测评列表
     * 
     * @param psyGauge 心理测评
     * @return 心理测评集合
     */
    public List<PsyGauge> selectPsyGaugeList(PsyGauge psyGauge);

    /**
     * 新增心理测评
     * 
     * @param psyGauge 心理测评
     * @return 结果
     */
    public int insertPsyGauge(PsyGauge psyGauge);

    /**
     * 修改心理测评
     * 
     * @param psyGauge 心理测评
     * @return 结果
     */
    public int updatePsyGauge(PsyGauge psyGauge);

    /**
     * 批量删除心理测评
     * 
     * @param ids 需要删除的心理测评主键集合
     * @return 结果
     */
    public int deletePsyGaugeByIds(Integer[] ids);

    /**
     * 删除心理测评信息
     * 
     * @param id 心理测评主键
     * @return 结果
     */
    public int deletePsyGaugeById(Integer id);
}
