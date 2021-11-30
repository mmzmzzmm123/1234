package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ZtkZhdZrjgjgbMapper;
import com.ruoyi.system.domain.ZtkZhdZrjgjgb;
import com.ruoyi.system.service.IZtkZhdZrjgjgbService;

/**
 * 个体工商户信贷_主题库_准入监管结果Service业务层处理
 * 
 * @author genius
 * @date 2021-11-30
 */
@Service
public class ZtkZhdZrjgjgbServiceImpl implements IZtkZhdZrjgjgbService 
{
    @Autowired
    private ZtkZhdZrjgjgbMapper ztkZhdZrjgjgbMapper;

    /**
     * 查询个体工商户信贷_主题库_准入监管结果
     * 
     * @param tyshxydm 个体工商户信贷_主题库_准入监管结果ID
     * @return 个体工商户信贷_主题库_准入监管结果
     */
    @Override
    public ZtkZhdZrjgjgb selectZtkZhdZrjgjgbById(String tyshxydm)
    {
        return ztkZhdZrjgjgbMapper.selectZtkZhdZrjgjgbById(tyshxydm);
    }

    /**
     * 查询个体工商户信贷_主题库_准入监管结果列表
     * 
     * @param ztkZhdZrjgjgb 个体工商户信贷_主题库_准入监管结果
     * @return 个体工商户信贷_主题库_准入监管结果
     */
    @Override
    public List<ZtkZhdZrjgjgb> selectZtkZhdZrjgjgbList(ZtkZhdZrjgjgb ztkZhdZrjgjgb)
    {
        return ztkZhdZrjgjgbMapper.selectZtkZhdZrjgjgbList(ztkZhdZrjgjgb);
    }

    /**
     * 新增个体工商户信贷_主题库_准入监管结果
     * 
     * @param ztkZhdZrjgjgb 个体工商户信贷_主题库_准入监管结果
     * @return 结果
     */
    @Override
    public int insertZtkZhdZrjgjgb(ZtkZhdZrjgjgb ztkZhdZrjgjgb)
    {
        return ztkZhdZrjgjgbMapper.insertZtkZhdZrjgjgb(ztkZhdZrjgjgb);
    }

    /**
     * 修改个体工商户信贷_主题库_准入监管结果
     * 
     * @param ztkZhdZrjgjgb 个体工商户信贷_主题库_准入监管结果
     * @return 结果
     */
    @Override
    public int updateZtkZhdZrjgjgb(ZtkZhdZrjgjgb ztkZhdZrjgjgb)
    {
        return ztkZhdZrjgjgbMapper.updateZtkZhdZrjgjgb(ztkZhdZrjgjgb);
    }

    /**
     * 批量删除个体工商户信贷_主题库_准入监管结果
     * 
     * @param tyshxydms 需要删除的个体工商户信贷_主题库_准入监管结果ID
     * @return 结果
     */
    @Override
    public int deleteZtkZhdZrjgjgbByIds(String[] tyshxydms)
    {
        return ztkZhdZrjgjgbMapper.deleteZtkZhdZrjgjgbByIds(tyshxydms);
    }

    /**
     * 删除个体工商户信贷_主题库_准入监管结果信息
     * 
     * @param tyshxydm 个体工商户信贷_主题库_准入监管结果ID
     * @return 结果
     */
    @Override
    public int deleteZtkZhdZrjgjgbById(String tyshxydm)
    {
        return ztkZhdZrjgjgbMapper.deleteZtkZhdZrjgjgbById(tyshxydm);
    }
}
