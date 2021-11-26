package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZtkZhdPfmxbMapper;
import com.ruoyi.system.domain.ZtkZhdPfmxb;
import com.ruoyi.system.service.IZtkZhdPfmxbService;

/**
 * 福建省泉州市个体工商户信贷_主题库_评分模型Service业务层处理
 * 
 * @author genius
 * @date 2021-11-26
 */
@Service
public class ZtkZhdPfmxbServiceImpl implements IZtkZhdPfmxbService 
{
    @Autowired
    private ZtkZhdPfmxbMapper ztkZhdPfmxbMapper;

    /**
     * 查询福建省泉州市个体工商户信贷_主题库_评分模型
     * 
     * @param zjlid 福建省泉州市个体工商户信贷_主题库_评分模型ID
     * @return 福建省泉州市个体工商户信贷_主题库_评分模型
     */
    @Override
    public ZtkZhdPfmxb selectZtkZhdPfmxbById(String zjlid)
    {
        return ztkZhdPfmxbMapper.selectZtkZhdPfmxbById(zjlid);
    }

    /**
     * 查询福建省泉州市个体工商户信贷_主题库_评分模型列表
     * 
     * @param ztkZhdPfmxb 福建省泉州市个体工商户信贷_主题库_评分模型
     * @return 福建省泉州市个体工商户信贷_主题库_评分模型
     */
    @Override
    public List<ZtkZhdPfmxb> selectZtkZhdPfmxbList(ZtkZhdPfmxb ztkZhdPfmxb)
    {
        return ztkZhdPfmxbMapper.selectZtkZhdPfmxbList(ztkZhdPfmxb);
    }

    /**
     * 新增福建省泉州市个体工商户信贷_主题库_评分模型
     * 
     * @param ztkZhdPfmxb 福建省泉州市个体工商户信贷_主题库_评分模型
     * @return 结果
     */
    @Override
    public int insertZtkZhdPfmxb(ZtkZhdPfmxb ztkZhdPfmxb)
    {
        return ztkZhdPfmxbMapper.insertZtkZhdPfmxb(ztkZhdPfmxb);
    }

    /**
     * 修改福建省泉州市个体工商户信贷_主题库_评分模型
     * 
     * @param ztkZhdPfmxb 福建省泉州市个体工商户信贷_主题库_评分模型
     * @return 结果
     */
    @Override
    public int updateZtkZhdPfmxb(ZtkZhdPfmxb ztkZhdPfmxb)
    {
        return ztkZhdPfmxbMapper.updateZtkZhdPfmxb(ztkZhdPfmxb);
    }

    /**
     * 批量删除福建省泉州市个体工商户信贷_主题库_评分模型
     * 
     * @param zjlids 需要删除的福建省泉州市个体工商户信贷_主题库_评分模型ID
     * @return 结果
     */
    @Override
    public int deleteZtkZhdPfmxbByIds(String[] zjlids)
    {
        return ztkZhdPfmxbMapper.deleteZtkZhdPfmxbByIds(zjlids);
    }

    /**
     * 删除福建省泉州市个体工商户信贷_主题库_评分模型信息
     * 
     * @param zjlid 福建省泉州市个体工商户信贷_主题库_评分模型ID
     * @return 结果
     */
    @Override
    public int deleteZtkZhdPfmxbById(String zjlid)
    {
        return ztkZhdPfmxbMapper.deleteZtkZhdPfmxbById(zjlid);
    }
}
