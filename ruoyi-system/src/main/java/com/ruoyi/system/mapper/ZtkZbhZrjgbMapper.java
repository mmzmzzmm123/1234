package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ZtkZbhZrjgb;

/**
 * 个体工商户信贷_主题库_准入监管Mapper接口
 * 
 * @author genius
 * @date 2021-11-26
 */
public interface ZtkZbhZrjgbMapper 
{
    /**
     * 查询个体工商户信贷_主题库_准入监管
     * 
     * @param zjlid 个体工商户信贷_主题库_准入监管ID
     * @return 个体工商户信贷_主题库_准入监管
     */
    public ZtkZbhZrjgb selectZtkZbhZrjgbById(String zjlid);

    /**
     * 查询个体工商户信贷_主题库_准入监管列表
     * 
     * @param ztkZbhZrjgb 个体工商户信贷_主题库_准入监管
     * @return 个体工商户信贷_主题库_准入监管集合
     */
    public List<ZtkZbhZrjgb> selectZtkZbhZrjgbList(ZtkZbhZrjgb ztkZbhZrjgb);

    /**
     * 新增个体工商户信贷_主题库_准入监管
     * 
     * @param ztkZbhZrjgb 个体工商户信贷_主题库_准入监管
     * @return 结果
     */
    public int insertZtkZbhZrjgb(ZtkZbhZrjgb ztkZbhZrjgb);

    /**
     * 修改个体工商户信贷_主题库_准入监管
     * 
     * @param ztkZbhZrjgb 个体工商户信贷_主题库_准入监管
     * @return 结果
     */
    public int updateZtkZbhZrjgb(ZtkZbhZrjgb ztkZbhZrjgb);

    /**
     * 删除个体工商户信贷_主题库_准入监管
     * 
     * @param zjlid 个体工商户信贷_主题库_准入监管ID
     * @return 结果
     */
    public int deleteZtkZbhZrjgbById(String zjlid);

    /**
     * 批量删除个体工商户信贷_主题库_准入监管
     * 
     * @param zjlids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZtkZbhZrjgbByIds(String[] zjlids);
}
