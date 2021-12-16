package com.ruoyi.carpool.service;

import java.util.List;
import com.ruoyi.carpool.domain.PDriver;

/**
 * 司机信息Service接口
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public interface IPDriverService 
{
    /**
     * 查询司机信息
     * 
     * @param id 司机信息主键
     * @return 司机信息
     */
    public PDriver selectPDriverById(Long id);

    /**
     * 查询司机信息列表
     * 
     * @param pDriver 司机信息
     * @return 司机信息集合
     */
    public List<PDriver> selectPDriverList(PDriver pDriver);

    /**
     * 新增司机信息
     * 
     * @param pDriver 司机信息
     * @return 结果
     */
    public int insertPDriver(PDriver pDriver);

    /**
     * 修改司机信息
     * 
     * @param pDriver 司机信息
     * @return 结果
     */
    public int updatePDriver(PDriver pDriver);

    /**
     * 批量删除司机信息
     * 
     * @param ids 需要删除的司机信息主键集合
     * @return 结果
     */
    public int deletePDriverByIds(Long[] ids);

    /**
     * 删除司机信息信息
     * 
     * @param id 司机信息主键
     * @return 结果
     */
    public int deletePDriverById(Long id);
}
