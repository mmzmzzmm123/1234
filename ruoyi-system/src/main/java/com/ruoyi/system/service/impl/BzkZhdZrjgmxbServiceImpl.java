package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BzkZhdZrjgmxbMapper;
import com.ruoyi.system.domain.BzkZhdZrjgmxb;
import com.ruoyi.system.service.IBzkZhdZrjgmxbService;

/**
 * 个体工商户信贷_标准库_准入监管明细Service业务层处理
 * 
 * @author genius
 * @date 2021-11-30
 */
@Service
public class BzkZhdZrjgmxbServiceImpl implements IBzkZhdZrjgmxbService 
{
    @Autowired
    private BzkZhdZrjgmxbMapper bzkZhdZrjgmxbMapper;

    /**
     * 查询个体工商户信贷_标准库_准入监管明细
     * 
     * @param tyshxydm 个体工商户信贷_标准库_准入监管明细ID
     * @return 个体工商户信贷_标准库_准入监管明细
     */
    @Override
    public BzkZhdZrjgmxb selectBzkZhdZrjgmxbById(String tyshxydm)
    {
        return bzkZhdZrjgmxbMapper.selectBzkZhdZrjgmxbById(tyshxydm);
    }

    /**
     * 查询个体工商户信贷_标准库_准入监管明细列表
     * 
     * @param bzkZhdZrjgmxb 个体工商户信贷_标准库_准入监管明细
     * @return 个体工商户信贷_标准库_准入监管明细
     */
    @Override
    public List<BzkZhdZrjgmxb> selectBzkZhdZrjgmxbList(BzkZhdZrjgmxb bzkZhdZrjgmxb)
    {
        return bzkZhdZrjgmxbMapper.selectBzkZhdZrjgmxbList(bzkZhdZrjgmxb);
    }

    /**
     * 新增个体工商户信贷_标准库_准入监管明细
     * 
     * @param bzkZhdZrjgmxb 个体工商户信贷_标准库_准入监管明细
     * @return 结果
     */
    @Override
    public int insertBzkZhdZrjgmxb(BzkZhdZrjgmxb bzkZhdZrjgmxb)
    {
        return bzkZhdZrjgmxbMapper.insertBzkZhdZrjgmxb(bzkZhdZrjgmxb);
    }

    /**
     * 修改个体工商户信贷_标准库_准入监管明细
     * 
     * @param bzkZhdZrjgmxb 个体工商户信贷_标准库_准入监管明细
     * @return 结果
     */
    @Override
    public int updateBzkZhdZrjgmxb(BzkZhdZrjgmxb bzkZhdZrjgmxb)
    {
        return bzkZhdZrjgmxbMapper.updateBzkZhdZrjgmxb(bzkZhdZrjgmxb);
    }

    /**
     * 批量删除个体工商户信贷_标准库_准入监管明细
     * 
     * @param tyshxydms 需要删除的个体工商户信贷_标准库_准入监管明细ID
     * @return 结果
     */
    @Override
    public int deleteBzkZhdZrjgmxbByIds(String[] tyshxydms)
    {
        return bzkZhdZrjgmxbMapper.deleteBzkZhdZrjgmxbByIds(tyshxydms);
    }

    /**
     * 删除个体工商户信贷_标准库_准入监管明细信息
     * 
     * @param tyshxydm 个体工商户信贷_标准库_准入监管明细ID
     * @return 结果
     */
    @Override
    public int deleteBzkZhdZrjgmxbById(String tyshxydm)
    {
        return bzkZhdZrjgmxbMapper.deleteBzkZhdZrjgmxbById(tyshxydm);
    }
}
