package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BzkZhdPfmxmxb;

/**
 * 个体工商户信贷_标准库_评分模型明细Mapper接口
 * 
 * @author genius
 * @date 2021-11-30
 */
public interface BzkZhdPfmxmxbMapper 
{
    /**
     * 查询个体工商户信贷_标准库_评分模型明细
     * 
     * @param tyshxydm 个体工商户信贷_标准库_评分模型明细ID
     * @return 个体工商户信贷_标准库_评分模型明细
     */
    public BzkZhdPfmxmxb selectBzkZhdPfmxmxbById(String tyshxydm);

    /**
     * 查询个体工商户信贷_标准库_评分模型明细列表
     * 
     * @param bzkZhdPfmxmxb 个体工商户信贷_标准库_评分模型明细
     * @return 个体工商户信贷_标准库_评分模型明细集合
     */
    public List<BzkZhdPfmxmxb> selectBzkZhdPfmxmxbList(BzkZhdPfmxmxb bzkZhdPfmxmxb);

    /**
     * 新增个体工商户信贷_标准库_评分模型明细
     * 
     * @param bzkZhdPfmxmxb 个体工商户信贷_标准库_评分模型明细
     * @return 结果
     */
    public int insertBzkZhdPfmxmxb(BzkZhdPfmxmxb bzkZhdPfmxmxb);

    /**
     * 修改个体工商户信贷_标准库_评分模型明细
     * 
     * @param bzkZhdPfmxmxb 个体工商户信贷_标准库_评分模型明细
     * @return 结果
     */
    public int updateBzkZhdPfmxmxb(BzkZhdPfmxmxb bzkZhdPfmxmxb);

    /**
     * 删除个体工商户信贷_标准库_评分模型明细
     * 
     * @param tyshxydm 个体工商户信贷_标准库_评分模型明细ID
     * @return 结果
     */
    public int deleteBzkZhdPfmxmxbById(String tyshxydm);

    /**
     * 批量删除个体工商户信贷_标准库_评分模型明细
     * 
     * @param tyshxydms 需要删除的数据ID
     * @return 结果
     */
    public int deleteBzkZhdPfmxmxbByIds(String[] tyshxydms);
}
