package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ZtkZhdPfmxb;

/**
 * 福建省泉州市个体工商户信贷_主题库_评分模型Mapper接口
 * 
 * @author genius
 * @date 2021-11-26
 */
public interface ZtkZhdPfmxbMapper 
{
    /**
     * 查询福建省泉州市个体工商户信贷_主题库_评分模型
     * 
     * @param zjlid 福建省泉州市个体工商户信贷_主题库_评分模型ID
     * @return 福建省泉州市个体工商户信贷_主题库_评分模型
     */
    public ZtkZhdPfmxb selectZtkZhdPfmxbById(String zjlid);

    /**
     * 查询福建省泉州市个体工商户信贷_主题库_评分模型列表
     * 
     * @param ztkZhdPfmxb 福建省泉州市个体工商户信贷_主题库_评分模型
     * @return 福建省泉州市个体工商户信贷_主题库_评分模型集合
     */
    public List<ZtkZhdPfmxb> selectZtkZhdPfmxbList(ZtkZhdPfmxb ztkZhdPfmxb);

    /**
     * 新增福建省泉州市个体工商户信贷_主题库_评分模型
     * 
     * @param ztkZhdPfmxb 福建省泉州市个体工商户信贷_主题库_评分模型
     * @return 结果
     */
    public int insertZtkZhdPfmxb(ZtkZhdPfmxb ztkZhdPfmxb);

    /**
     * 修改福建省泉州市个体工商户信贷_主题库_评分模型
     * 
     * @param ztkZhdPfmxb 福建省泉州市个体工商户信贷_主题库_评分模型
     * @return 结果
     */
    public int updateZtkZhdPfmxb(ZtkZhdPfmxb ztkZhdPfmxb);

    /**
     * 删除福建省泉州市个体工商户信贷_主题库_评分模型
     * 
     * @param zjlid 福建省泉州市个体工商户信贷_主题库_评分模型ID
     * @return 结果
     */
    public int deleteZtkZhdPfmxbById(String zjlid);

    /**
     * 批量删除福建省泉州市个体工商户信贷_主题库_评分模型
     * 
     * @param zjlids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZtkZhdPfmxbByIds(String[] zjlids);
}
