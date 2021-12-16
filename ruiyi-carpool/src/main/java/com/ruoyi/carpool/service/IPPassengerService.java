package com.ruoyi.carpool.service;

import java.util.List;
import com.ruoyi.carpool.domain.PPassenger;

/**
 * 乘客信息Service接口
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public interface IPPassengerService 
{
    /**
     * 查询乘客信息
     * 
     * @param id 乘客信息主键
     * @return 乘客信息
     */
    public PPassenger selectPPassengerById(Long id);

    /**
     * 查询乘客信息列表
     * 
     * @param pPassenger 乘客信息
     * @return 乘客信息集合
     */
    public List<PPassenger> selectPPassengerList(PPassenger pPassenger);

    /**
     * 新增乘客信息
     * 
     * @param pPassenger 乘客信息
     * @return 结果
     */
    public int insertPPassenger(PPassenger pPassenger);

    /**
     * 修改乘客信息
     * 
     * @param pPassenger 乘客信息
     * @return 结果
     */
    public int updatePPassenger(PPassenger pPassenger);

    /**
     * 批量删除乘客信息
     * 
     * @param ids 需要删除的乘客信息主键集合
     * @return 结果
     */
    public int deletePPassengerByIds(Long[] ids);

    /**
     * 删除乘客信息信息
     * 
     * @param id 乘客信息主键
     * @return 结果
     */
    public int deletePPassengerById(Long id);
}
