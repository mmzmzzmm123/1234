package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BzkZbhZrjgb;

/**
 * 个体工商户信贷_标准库_准入监管Mapper接口
 * 
 * @author genius
 * @date 2021-11-26
 */
public interface BzkZbhZrjgbMapper 
{
    /**
     * 查询个体工商户信贷_标准库_准入监管
     * 
     * @param zjlid 个体工商户信贷_标准库_准入监管ID
     * @return 个体工商户信贷_标准库_准入监管
     */
    public BzkZbhZrjgb selectBzkZbhZrjgbById(String zjlid);

    /**
     * 查询个体工商户信贷_标准库_准入监管列表
     * 
     * @param bzkZbhZrjgb 个体工商户信贷_标准库_准入监管
     * @return 个体工商户信贷_标准库_准入监管集合
     */
    public List<BzkZbhZrjgb> selectBzkZbhZrjgbList(BzkZbhZrjgb bzkZbhZrjgb);

    /**
     * 新增个体工商户信贷_标准库_准入监管
     * 
     * @param bzkZbhZrjgb 个体工商户信贷_标准库_准入监管
     * @return 结果
     */
    public int insertBzkZbhZrjgb(BzkZbhZrjgb bzkZbhZrjgb);

    /**
     * 修改个体工商户信贷_标准库_准入监管
     * 
     * @param bzkZbhZrjgb 个体工商户信贷_标准库_准入监管
     * @return 结果
     */
    public int updateBzkZbhZrjgb(BzkZbhZrjgb bzkZbhZrjgb);

    /**
     * 删除个体工商户信贷_标准库_准入监管
     * 
     * @param zjlid 个体工商户信贷_标准库_准入监管ID
     * @return 结果
     */
    public int deleteBzkZbhZrjgbById(String zjlid);

    /**
     * 批量删除个体工商户信贷_标准库_准入监管
     * 
     * @param zjlids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBzkZbhZrjgbByIds(String[] zjlids);
}
