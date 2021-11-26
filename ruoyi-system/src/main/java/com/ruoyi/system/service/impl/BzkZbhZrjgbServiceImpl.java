package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BzkZbhZrjgbMapper;
import com.ruoyi.system.domain.BzkZbhZrjgb;
import com.ruoyi.system.service.IBzkZbhZrjgbService;

/**
 * 个体工商户信贷_标准库_准入监管Service业务层处理
 * 
 * @author genius
 * @date 2021-11-26
 */
@Service
public class BzkZbhZrjgbServiceImpl implements IBzkZbhZrjgbService 
{
    @Autowired
    private BzkZbhZrjgbMapper bzkZbhZrjgbMapper;

    /**
     * 查询个体工商户信贷_标准库_准入监管
     * 
     * @param zjlid 个体工商户信贷_标准库_准入监管ID
     * @return 个体工商户信贷_标准库_准入监管
     */
    @Override
    public BzkZbhZrjgb selectBzkZbhZrjgbById(String zjlid)
    {
        return bzkZbhZrjgbMapper.selectBzkZbhZrjgbById(zjlid);
    }

    /**
     * 查询个体工商户信贷_标准库_准入监管列表
     * 
     * @param bzkZbhZrjgb 个体工商户信贷_标准库_准入监管
     * @return 个体工商户信贷_标准库_准入监管
     */
    @Override
    public List<BzkZbhZrjgb> selectBzkZbhZrjgbList(BzkZbhZrjgb bzkZbhZrjgb)
    {
        return bzkZbhZrjgbMapper.selectBzkZbhZrjgbList(bzkZbhZrjgb);
    }

    /**
     * 新增个体工商户信贷_标准库_准入监管
     * 
     * @param bzkZbhZrjgb 个体工商户信贷_标准库_准入监管
     * @return 结果
     */
    @Override
    public int insertBzkZbhZrjgb(BzkZbhZrjgb bzkZbhZrjgb)
    {
        return bzkZbhZrjgbMapper.insertBzkZbhZrjgb(bzkZbhZrjgb);
    }

    /**
     * 修改个体工商户信贷_标准库_准入监管
     * 
     * @param bzkZbhZrjgb 个体工商户信贷_标准库_准入监管
     * @return 结果
     */
    @Override
    public int updateBzkZbhZrjgb(BzkZbhZrjgb bzkZbhZrjgb)
    {
        return bzkZbhZrjgbMapper.updateBzkZbhZrjgb(bzkZbhZrjgb);
    }

    /**
     * 批量删除个体工商户信贷_标准库_准入监管
     * 
     * @param zjlids 需要删除的个体工商户信贷_标准库_准入监管ID
     * @return 结果
     */
    @Override
    public int deleteBzkZbhZrjgbByIds(String[] zjlids)
    {
        return bzkZbhZrjgbMapper.deleteBzkZbhZrjgbByIds(zjlids);
    }

    /**
     * 删除个体工商户信贷_标准库_准入监管信息
     * 
     * @param zjlid 个体工商户信贷_标准库_准入监管ID
     * @return 结果
     */
    @Override
    public int deleteBzkZbhZrjgbById(String zjlid)
    {
        return bzkZbhZrjgbMapper.deleteBzkZbhZrjgbById(zjlid);
    }
}
