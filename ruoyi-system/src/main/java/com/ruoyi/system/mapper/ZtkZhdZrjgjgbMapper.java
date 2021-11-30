package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ZtkZhdZrjgjgb;

/**
 * 个体工商户信贷_主题库_准入监管结果Mapper接口
 * 
 * @author genius
 * @date 2021-11-30
 */
public interface ZtkZhdZrjgjgbMapper 
{
    /**
     * 查询个体工商户信贷_主题库_准入监管结果
     * 
     * @param tyshxydm 个体工商户信贷_主题库_准入监管结果ID
     * @return 个体工商户信贷_主题库_准入监管结果
     */
    public ZtkZhdZrjgjgb selectZtkZhdZrjgjgbById(String tyshxydm);

    /**
     * 查询个体工商户信贷_主题库_准入监管结果列表
     * 
     * @param ztkZhdZrjgjgb 个体工商户信贷_主题库_准入监管结果
     * @return 个体工商户信贷_主题库_准入监管结果集合
     */
    public List<ZtkZhdZrjgjgb> selectZtkZhdZrjgjgbList(ZtkZhdZrjgjgb ztkZhdZrjgjgb);

    /**
     * 新增个体工商户信贷_主题库_准入监管结果
     * 
     * @param ztkZhdZrjgjgb 个体工商户信贷_主题库_准入监管结果
     * @return 结果
     */
    public int insertZtkZhdZrjgjgb(ZtkZhdZrjgjgb ztkZhdZrjgjgb);

    /**
     * 修改个体工商户信贷_主题库_准入监管结果
     * 
     * @param ztkZhdZrjgjgb 个体工商户信贷_主题库_准入监管结果
     * @return 结果
     */
    public int updateZtkZhdZrjgjgb(ZtkZhdZrjgjgb ztkZhdZrjgjgb);

    /**
     * 删除个体工商户信贷_主题库_准入监管结果
     * 
     * @param tyshxydm 个体工商户信贷_主题库_准入监管结果ID
     * @return 结果
     */
    public int deleteZtkZhdZrjgjgbById(String tyshxydm);

    /**
     * 批量删除个体工商户信贷_主题库_准入监管结果
     * 
     * @param tyshxydms 需要删除的数据ID
     * @return 结果
     */
    public int deleteZtkZhdZrjgjgbByIds(String[] tyshxydms);
}
