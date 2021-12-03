package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZtkZhdPfmxjgbMapper;
import com.ruoyi.system.domain.ZtkZhdPfmxjgb;
import com.ruoyi.system.service.IZtkZhdPfmxjgbService;

/**
 * 个体工商户信贷_主题库_评分模型结果Service业务层处理
 * 
 * @author genius
 * @date 2021-11-30
 */
@Service
@DataSource(value = DataSourceType.API)
public class ZtkZhdPfmxjgbServiceImpl implements IZtkZhdPfmxjgbService 
{
    @Autowired
    private ZtkZhdPfmxjgbMapper ztkZhdPfmxjgbMapper;

    /**
     * 查询个体工商户信贷_主题库_评分模型结果
     * 
     * @param tyshxydm 个体工商户信贷_主题库_评分模型结果ID
     * @return 个体工商户信贷_主题库_评分模型结果
     */
    @Override
    public ZtkZhdPfmxjgb selectZtkZhdPfmxjgbById(String tyshxydm)
    {
        return ztkZhdPfmxjgbMapper.selectZtkZhdPfmxjgbById(tyshxydm);
    }

    /**
     * 查询个体工商户信贷_主题库_评分模型结果列表
     * 
     * @param ztkZhdPfmxjgb 个体工商户信贷_主题库_评分模型结果
     * @return 个体工商户信贷_主题库_评分模型结果
     */
    @Override
    public List<ZtkZhdPfmxjgb> selectZtkZhdPfmxjgbList(ZtkZhdPfmxjgb ztkZhdPfmxjgb)
    {
        return ztkZhdPfmxjgbMapper.selectZtkZhdPfmxjgbList(ztkZhdPfmxjgb);
    }

    /**
     * 新增个体工商户信贷_主题库_评分模型结果
     * 
     * @param ztkZhdPfmxjgb 个体工商户信贷_主题库_评分模型结果
     * @return 结果
     */
    @Override
    public int insertZtkZhdPfmxjgb(ZtkZhdPfmxjgb ztkZhdPfmxjgb)
    {
        return ztkZhdPfmxjgbMapper.insertZtkZhdPfmxjgb(ztkZhdPfmxjgb);
    }

    /**
     * 修改个体工商户信贷_主题库_评分模型结果
     * 
     * @param ztkZhdPfmxjgb 个体工商户信贷_主题库_评分模型结果
     * @return 结果
     */
    @Override
    public int updateZtkZhdPfmxjgb(ZtkZhdPfmxjgb ztkZhdPfmxjgb)
    {
        return ztkZhdPfmxjgbMapper.updateZtkZhdPfmxjgb(ztkZhdPfmxjgb);
    }

    /**
     * 批量删除个体工商户信贷_主题库_评分模型结果
     * 
     * @param tyshxydms 需要删除的个体工商户信贷_主题库_评分模型结果ID
     * @return 结果
     */
    @Override
    public int deleteZtkZhdPfmxjgbByIds(String[] tyshxydms)
    {
        return ztkZhdPfmxjgbMapper.deleteZtkZhdPfmxjgbByIds(tyshxydms);
    }

    /**
     * 删除个体工商户信贷_主题库_评分模型结果信息
     * 
     * @param tyshxydm 个体工商户信贷_主题库_评分模型结果ID
     * @return 结果
     */
    @Override
    public int deleteZtkZhdPfmxjgbById(String tyshxydm)
    {
        return ztkZhdPfmxjgbMapper.deleteZtkZhdPfmxjgbById(tyshxydm);
    }
}
