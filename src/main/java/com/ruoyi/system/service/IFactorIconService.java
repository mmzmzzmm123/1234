package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.FactorIcon;

/**
 * 因子图标Service接口
 * 
 * @author ruoyi
 * @date 2021-12-17
 */
public interface IFactorIconService 
{
    /**
     * 查询因子图标
     * 
     * @param id 因子图标主键
     * @return 因子图标
     */
    public FactorIcon selectFactorIconById(Long id);

    /**
     * 查询因子图标列表
     * 
     * @param factorIcon 因子图标
     * @return 因子图标集合
     */
    public List<FactorIcon> selectFactorIconList(FactorIcon factorIcon);

    /**
     * 新增因子图标
     * 
     * @param factorIcon 因子图标
     * @return 结果
     */
    public int insertFactorIcon(FactorIcon factorIcon);

    /**
     * 修改因子图标
     * 
     * @param factorIcon 因子图标
     * @return 结果
     */
    public int updateFactorIcon(FactorIcon factorIcon);

    /**
     * 批量删除因子图标
     * 
     * @param ids 需要删除的因子图标主键集合
     * @return 结果
     */
    public int deleteFactorIconByIds(Long[] ids);

    /**
     * 删除因子图标信息
     * 
     * @param id 因子图标主键
     * @return 结果
     */
    public int deleteFactorIconById(Long id);
}
