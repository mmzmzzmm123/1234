package com.ruoyi.common.service;

import com.ruoyi.common.domain.Sdhcircuit;

import java.util.List;

/**
 * 电路信息Service接口
 *
 * @author ruoyi
 * @date 2023-12-23
 */
public interface ISdhcircuitService {
    /**
     * 查询电路信息
     *
     * @param id 电路信息主键
     * @return 电路信息
     */
    public Sdhcircuit selectSdhcircuitById(Long id);

    /**
     * 查询电路信息列表
     *
     * @param sdhcircuit 电路信息
     * @return 电路信息集合
     */
    public List<Sdhcircuit> selectSdhcircuitList(Sdhcircuit sdhcircuit);
    /**
     * 查询电路信息列表
     *
     * @param sdhcircuit 电路信息
     * @return 导出电路信息集合
     */
    public List<Sdhcircuit> exportSdhcircuitList(Sdhcircuit sdhcircuit);

    /**
     * 新增电路信息
     *
     * @param sdhcircuit 电路信息
     * @return 结果
     */
    public int insertSdhcircuit(Sdhcircuit sdhcircuit);

    /**
     * 修改电路信息
     *
     * @param sdhcircuit 电路信息
     * @return 结果
     */
    public int updateSdhcircuit(Sdhcircuit sdhcircuit);

    /**
     * 批量删除电路信息
     *
     * @param ids 需要删除的电路信息主键集合
     * @return 结果
     */
    public int deleteSdhcircuitByIds(Long[] ids);

    /**
     * 删除电路信息信息
     *
     * @param id 电路信息主键
     * @return 结果
     */
    public int deleteSdhcircuitById(Long id);
}
