package com.ruoyi.carpool.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.carpool.mapper.PPassengerMapper;
import com.ruoyi.carpool.domain.PPassenger;
import com.ruoyi.carpool.service.IPPassengerService;

/**
 * 乘客信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
@Service
public class PPassengerServiceImpl implements IPPassengerService 
{
    @Autowired
    private PPassengerMapper pPassengerMapper;

    /**
     * 查询乘客信息
     * 
     * @param id 乘客信息主键
     * @return 乘客信息
     */
    @Override
    public PPassenger selectPPassengerById(Long id)
    {
        return pPassengerMapper.selectPPassengerById(id);
    }

    /**
     * 查询乘客信息列表
     * 
     * @param pPassenger 乘客信息
     * @return 乘客信息
     */
    @Override
    public List<PPassenger> selectPPassengerList(PPassenger pPassenger)
    {
        return pPassengerMapper.selectPPassengerList(pPassenger);
    }

    /**
     * 新增乘客信息
     * 
     * @param pPassenger 乘客信息
     * @return 结果
     */
    @Override
    public int insertPPassenger(PPassenger pPassenger)
    {
        pPassenger.setCreateTime(DateUtils.getNowDate());
        return pPassengerMapper.insertPPassenger(pPassenger);
    }

    /**
     * 修改乘客信息
     * 
     * @param pPassenger 乘客信息
     * @return 结果
     */
    @Override
    public int updatePPassenger(PPassenger pPassenger)
    {
        pPassenger.setUpdateTime(DateUtils.getNowDate());
        return pPassengerMapper.updatePPassenger(pPassenger);
    }

    /**
     * 批量删除乘客信息
     * 
     * @param ids 需要删除的乘客信息主键
     * @return 结果
     */
    @Override
    public int deletePPassengerByIds(Long[] ids)
    {
        return pPassengerMapper.deletePPassengerByIds(ids);
    }

    /**
     * 删除乘客信息信息
     * 
     * @param id 乘客信息主键
     * @return 结果
     */
    @Override
    public int deletePPassengerById(Long id)
    {
        return pPassengerMapper.deletePPassengerById(id);
    }
}
