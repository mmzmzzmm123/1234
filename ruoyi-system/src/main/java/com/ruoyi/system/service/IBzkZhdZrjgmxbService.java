package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BzkZhdZrjgmxb;

/**
 * 个体工商户信贷_标准库_准入监管明细Service接口
 * 
 * @author genius
 * @date 2021-11-30
 */
public interface IBzkZhdZrjgmxbService 
{
    /**
     * 查询个体工商户信贷_标准库_准入监管明细
     * 
     * @param tyshxydm 个体工商户信贷_标准库_准入监管明细ID
     * @return 个体工商户信贷_标准库_准入监管明细
     */
    public BzkZhdZrjgmxb selectBzkZhdZrjgmxbById(String tyshxydm);

    /**
     * 查询个体工商户信贷_标准库_准入监管明细列表
     * 
     * @param bzkZhdZrjgmxb 个体工商户信贷_标准库_准入监管明细
     * @return 个体工商户信贷_标准库_准入监管明细集合
     */
    public List<BzkZhdZrjgmxb> selectBzkZhdZrjgmxbList(BzkZhdZrjgmxb bzkZhdZrjgmxb);

    /**
     * 新增个体工商户信贷_标准库_准入监管明细
     * 
     * @param bzkZhdZrjgmxb 个体工商户信贷_标准库_准入监管明细
     * @return 结果
     */
    public int insertBzkZhdZrjgmxb(BzkZhdZrjgmxb bzkZhdZrjgmxb);

    /**
     * 修改个体工商户信贷_标准库_准入监管明细
     * 
     * @param bzkZhdZrjgmxb 个体工商户信贷_标准库_准入监管明细
     * @return 结果
     */
    public int updateBzkZhdZrjgmxb(BzkZhdZrjgmxb bzkZhdZrjgmxb);

    /**
     * 批量删除个体工商户信贷_标准库_准入监管明细
     * 
     * @param tyshxydms 需要删除的个体工商户信贷_标准库_准入监管明细ID
     * @return 结果
     */
    public int deleteBzkZhdZrjgmxbByIds(String[] tyshxydms);

    /**
     * 删除个体工商户信贷_标准库_准入监管明细信息
     * 
     * @param tyshxydm 个体工商户信贷_标准库_准入监管明细ID
     * @return 结果
     */
    public int deleteBzkZhdZrjgmxbById(String tyshxydm);
}
