package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ZtkZhdPfmxjgb;

/**
 * 个体工商户信贷_主题库_评分模型结果Service接口
 * 
 * @author genius
 * @date 2021-11-30
 */
public interface IZtkZhdPfmxjgbService 
{
    /**
     * 查询个体工商户信贷_主题库_评分模型结果
     * 
     * @param tyshxydm 个体工商户信贷_主题库_评分模型结果ID
     * @return 个体工商户信贷_主题库_评分模型结果
     */
    public ZtkZhdPfmxjgb selectZtkZhdPfmxjgbById(String tyshxydm);

    /**
     * 查询个体工商户信贷_主题库_评分模型结果列表
     * 
     * @param ztkZhdPfmxjgb 个体工商户信贷_主题库_评分模型结果
     * @return 个体工商户信贷_主题库_评分模型结果集合
     */
    public List<ZtkZhdPfmxjgb> selectZtkZhdPfmxjgbList(ZtkZhdPfmxjgb ztkZhdPfmxjgb);

    /**
     * 新增个体工商户信贷_主题库_评分模型结果
     * 
     * @param ztkZhdPfmxjgb 个体工商户信贷_主题库_评分模型结果
     * @return 结果
     */
    public int insertZtkZhdPfmxjgb(ZtkZhdPfmxjgb ztkZhdPfmxjgb);

    /**
     * 修改个体工商户信贷_主题库_评分模型结果
     * 
     * @param ztkZhdPfmxjgb 个体工商户信贷_主题库_评分模型结果
     * @return 结果
     */
    public int updateZtkZhdPfmxjgb(ZtkZhdPfmxjgb ztkZhdPfmxjgb);

    /**
     * 批量删除个体工商户信贷_主题库_评分模型结果
     * 
     * @param tyshxydms 需要删除的个体工商户信贷_主题库_评分模型结果ID
     * @return 结果
     */
    public int deleteZtkZhdPfmxjgbByIds(String[] tyshxydms);

    /**
     * 删除个体工商户信贷_主题库_评分模型结果信息
     * 
     * @param tyshxydm 个体工商户信贷_主题库_评分模型结果ID
     * @return 结果
     */
    public int deleteZtkZhdPfmxjgbById(String tyshxydm);
}
