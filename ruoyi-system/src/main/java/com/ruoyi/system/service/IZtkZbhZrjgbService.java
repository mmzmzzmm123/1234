package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ZtkZbhZrjgb;

/**
 * 个体工商户信贷_主题库_准入监管Service接口
 * 
 * @author genius
 * @date 2021-11-26
 */
public interface IZtkZbhZrjgbService 
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
     * 批量删除个体工商户信贷_主题库_准入监管
     * 
     * @param zjlids 需要删除的个体工商户信贷_主题库_准入监管ID
     * @return 结果
     */
    public int deleteZtkZbhZrjgbByIds(String[] zjlids);

    /**
     * 删除个体工商户信贷_主题库_准入监管信息
     * 
     * @param zjlid 个体工商户信贷_主题库_准入监管ID
     * @return 结果
     */
    public int deleteZtkZbhZrjgbById(String zjlid);
}
