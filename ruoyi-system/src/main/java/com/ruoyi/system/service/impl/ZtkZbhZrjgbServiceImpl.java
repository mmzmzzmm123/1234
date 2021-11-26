package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZtkZbhZrjgbMapper;
import com.ruoyi.system.domain.ZtkZbhZrjgb;
import com.ruoyi.system.service.IZtkZbhZrjgbService;

/**
 * 个体工商户信贷_主题库_准入监管Service业务层处理
 * 
 * @author genius
 * @date 2021-11-26
 */
@Service
public class ZtkZbhZrjgbServiceImpl implements IZtkZbhZrjgbService 
{
    @Autowired
    private ZtkZbhZrjgbMapper ztkZbhZrjgbMapper;

    /**
     * 查询个体工商户信贷_主题库_准入监管
     * 
     * @param zjlid 个体工商户信贷_主题库_准入监管ID
     * @return 个体工商户信贷_主题库_准入监管
     */
    @Override
    public ZtkZbhZrjgb selectZtkZbhZrjgbById(String zjlid)
    {
        return ztkZbhZrjgbMapper.selectZtkZbhZrjgbById(zjlid);
    }

    /**
     * 查询个体工商户信贷_主题库_准入监管列表
     * 
     * @param ztkZbhZrjgb 个体工商户信贷_主题库_准入监管
     * @return 个体工商户信贷_主题库_准入监管
     */
    @Override
    public List<ZtkZbhZrjgb> selectZtkZbhZrjgbList(ZtkZbhZrjgb ztkZbhZrjgb)
    {
        return ztkZbhZrjgbMapper.selectZtkZbhZrjgbList(ztkZbhZrjgb);
    }

    /**
     * 新增个体工商户信贷_主题库_准入监管
     * 
     * @param ztkZbhZrjgb 个体工商户信贷_主题库_准入监管
     * @return 结果
     */
    @Override
    public int insertZtkZbhZrjgb(ZtkZbhZrjgb ztkZbhZrjgb)
    {
        return ztkZbhZrjgbMapper.insertZtkZbhZrjgb(ztkZbhZrjgb);
    }

    /**
     * 修改个体工商户信贷_主题库_准入监管
     * 
     * @param ztkZbhZrjgb 个体工商户信贷_主题库_准入监管
     * @return 结果
     */
    @Override
    public int updateZtkZbhZrjgb(ZtkZbhZrjgb ztkZbhZrjgb)
    {
        return ztkZbhZrjgbMapper.updateZtkZbhZrjgb(ztkZbhZrjgb);
    }

    /**
     * 批量删除个体工商户信贷_主题库_准入监管
     * 
     * @param zjlids 需要删除的个体工商户信贷_主题库_准入监管ID
     * @return 结果
     */
    @Override
    public int deleteZtkZbhZrjgbByIds(String[] zjlids)
    {
        return ztkZbhZrjgbMapper.deleteZtkZbhZrjgbByIds(zjlids);
    }

    /**
     * 删除个体工商户信贷_主题库_准入监管信息
     * 
     * @param zjlid 个体工商户信贷_主题库_准入监管ID
     * @return 结果
     */
    @Override
    public int deleteZtkZbhZrjgbById(String zjlid)
    {
        return ztkZbhZrjgbMapper.deleteZtkZbhZrjgbById(zjlid);
    }
}
