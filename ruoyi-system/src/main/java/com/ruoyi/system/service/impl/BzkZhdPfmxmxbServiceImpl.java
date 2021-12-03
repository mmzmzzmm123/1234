package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BzkZhdPfmxmxbMapper;
import com.ruoyi.system.domain.BzkZhdPfmxmxb;
import com.ruoyi.system.service.IBzkZhdPfmxmxbService;

/**
 * 个体工商户信贷_标准库_评分模型明细Service业务层处理
 * 
 * @author genius
 * @date 2021-11-30
 */
@Service
@DataSource(value = DataSourceType.API)
public class BzkZhdPfmxmxbServiceImpl implements IBzkZhdPfmxmxbService 
{
    @Autowired
    private BzkZhdPfmxmxbMapper bzkZhdPfmxmxbMapper;

    /**
     * 查询个体工商户信贷_标准库_评分模型明细
     * 
     * @param tyshxydm 个体工商户信贷_标准库_评分模型明细ID
     * @return 个体工商户信贷_标准库_评分模型明细
     */
    @Override
    public BzkZhdPfmxmxb selectBzkZhdPfmxmxbById(String tyshxydm)
    {
        return bzkZhdPfmxmxbMapper.selectBzkZhdPfmxmxbById(tyshxydm);
    }

    /**
     * 查询个体工商户信贷_标准库_评分模型明细列表
     * 
     * @param bzkZhdPfmxmxb 个体工商户信贷_标准库_评分模型明细
     * @return 个体工商户信贷_标准库_评分模型明细
     */
    @Override
    public List<BzkZhdPfmxmxb> selectBzkZhdPfmxmxbList(BzkZhdPfmxmxb bzkZhdPfmxmxb)
    {
        return bzkZhdPfmxmxbMapper.selectBzkZhdPfmxmxbList(bzkZhdPfmxmxb);
    }

    /**
     * 新增个体工商户信贷_标准库_评分模型明细
     * 
     * @param bzkZhdPfmxmxb 个体工商户信贷_标准库_评分模型明细
     * @return 结果
     */
    @Override
    public int insertBzkZhdPfmxmxb(BzkZhdPfmxmxb bzkZhdPfmxmxb)
    {
        return bzkZhdPfmxmxbMapper.insertBzkZhdPfmxmxb(bzkZhdPfmxmxb);
    }

    /**
     * 修改个体工商户信贷_标准库_评分模型明细
     * 
     * @param bzkZhdPfmxmxb 个体工商户信贷_标准库_评分模型明细
     * @return 结果
     */
    @Override
    public int updateBzkZhdPfmxmxb(BzkZhdPfmxmxb bzkZhdPfmxmxb)
    {
        return bzkZhdPfmxmxbMapper.updateBzkZhdPfmxmxb(bzkZhdPfmxmxb);
    }

    /**
     * 批量删除个体工商户信贷_标准库_评分模型明细
     * 
     * @param tyshxydms 需要删除的个体工商户信贷_标准库_评分模型明细ID
     * @return 结果
     */
    @Override
    public int deleteBzkZhdPfmxmxbByIds(String[] tyshxydms)
    {
        return bzkZhdPfmxmxbMapper.deleteBzkZhdPfmxmxbByIds(tyshxydms);
    }

    /**
     * 删除个体工商户信贷_标准库_评分模型明细信息
     * 
     * @param tyshxydm 个体工商户信贷_标准库_评分模型明细ID
     * @return 结果
     */
    @Override
    public int deleteBzkZhdPfmxmxbById(String tyshxydm)
    {
        return bzkZhdPfmxmxbMapper.deleteBzkZhdPfmxmxbById(tyshxydm);
    }
}
