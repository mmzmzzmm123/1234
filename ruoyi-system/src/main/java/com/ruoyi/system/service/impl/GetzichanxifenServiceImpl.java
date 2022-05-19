package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GetzichanxifenMapper;
import com.ruoyi.system.domain.Getzichanxifen;
import com.ruoyi.system.service.IGetzichanxifenService;

/**
 * 资产细分Service业务层处理
 * 
 * @author gongyu
 * @date 2022-05-19
 */
@Service
public class GetzichanxifenServiceImpl implements IGetzichanxifenService 
{
    @Autowired
    private GetzichanxifenMapper getzichanxifenMapper;

    /**
     * 查询资产细分
     * 
     * @param name 资产细分主键
     * @return 资产细分
     */
    @Override
    public Getzichanxifen selectGetzichanxifenByName(String name)
    {
        return getzichanxifenMapper.selectGetzichanxifenByName(name);
    }

    /**
     * 查询资产细分列表
     * 
     * @param getzichanxifen 资产细分
     * @return 资产细分
     */
    @Override
    public List<Getzichanxifen> selectGetzichanxifenList(Getzichanxifen getzichanxifen)
    {
        return getzichanxifenMapper.selectGetzichanxifenList(getzichanxifen);
    }

    /**
     * 新增资产细分
     * 
     * @param getzichanxifen 资产细分
     * @return 结果
     */
    @Override
    public int insertGetzichanxifen(Getzichanxifen getzichanxifen)
    {
        return getzichanxifenMapper.insertGetzichanxifen(getzichanxifen);
    }

    /**
     * 修改资产细分
     * 
     * @param getzichanxifen 资产细分
     * @return 结果
     */
    @Override
    public int updateGetzichanxifen(Getzichanxifen getzichanxifen)
    {
        return getzichanxifenMapper.updateGetzichanxifen(getzichanxifen);
    }

    /**
     * 批量删除资产细分
     * 
     * @param names 需要删除的资产细分主键
     * @return 结果
     */
    @Override
    public int deleteGetzichanxifenByNames(String[] names)
    {
        return getzichanxifenMapper.deleteGetzichanxifenByNames(names);
    }

    /**
     * 删除资产细分信息
     * 
     * @param name 资产细分主键
     * @return 结果
     */
    @Override
    public int deleteGetzichanxifenByName(String name)
    {
        return getzichanxifenMapper.deleteGetzichanxifenByName(name);
    }
}
