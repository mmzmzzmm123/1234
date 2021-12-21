package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.FactorIconMapper;
import com.ruoyi.system.domain.FactorIcon;
import com.ruoyi.system.service.IFactorIconService;

/**
 * 因子图标Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-17
 */
@Service
public class FactorIconServiceImpl implements IFactorIconService 
{
    @Autowired
    private FactorIconMapper factorIconMapper;

    /**
     * 查询因子图标
     * 
     * @param id 因子图标主键
     * @return 因子图标
     */
    @Override
    public FactorIcon selectFactorIconById(Long id)
    {
        return factorIconMapper.selectFactorIconById(id);
    }

    /**
     * 查询因子图标列表
     * 
     * @param factorIcon 因子图标
     * @return 因子图标
     */
    @Override
    public List<FactorIcon> selectFactorIconList(FactorIcon factorIcon)
    {
        return factorIconMapper.selectFactorIconList(factorIcon);
    }

    /**
     * 新增因子图标
     * 
     * @param factorIcon 因子图标
     * @return 结果
     */
    @Override
    public int insertFactorIcon(FactorIcon factorIcon)
    {
        return factorIconMapper.insertFactorIcon(factorIcon);
    }

    /**
     * 修改因子图标
     * 
     * @param factorIcon 因子图标
     * @return 结果
     */
    @Override
    public int updateFactorIcon(FactorIcon factorIcon)
    {
        return factorIconMapper.updateFactorIcon(factorIcon);
    }

    /**
     * 批量删除因子图标
     * 
     * @param ids 需要删除的因子图标主键
     * @return 结果
     */
    @Override
    public int deleteFactorIconByIds(Long[] ids)
    {
        return factorIconMapper.deleteFactorIconByIds(ids);
    }

    /**
     * 删除因子图标信息
     * 
     * @param id 因子图标主键
     * @return 结果
     */
    @Override
    public int deleteFactorIconById(Long id)
    {
        return factorIconMapper.deleteFactorIconById(id);
    }
}
