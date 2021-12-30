package com.ruoyi.carpool.service.impl;

import java.util.List;

import com.ruoyi.carpool.domain.PPassenger;
import com.ruoyi.carpool.mapper.PPassengerMapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.carpool.mapper.PDriverMapper;
import com.ruoyi.carpool.domain.PDriver;
import com.ruoyi.carpool.service.IPDriverService;

/**
 * 司机信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
@Service
public class PDriverServiceImpl implements IPDriverService 
{
    @Autowired
    private PDriverMapper pDriverMapper;

    @Autowired
    private PPassengerMapper pPassengerMapper;


    /**
     * 查询司机信息
     * 
     * @param id 司机信息主键
     * @return 司机信息
     */
    @Override
    public PDriver selectPDriverById(Long id)
    {
        return pDriverMapper.selectPDriverById(id);
    }

    /**
     * 查询司机信息列表
     * 
     * @param pDriver 司机信息
     * @return 司机信息
     */
    @Override
    public List<PDriver> selectPDriverList(PDriver pDriver)
    {
        return pDriverMapper.selectPDriverList(pDriver);
    }

    /**
     * 新增司机信息
     * 
     * @param pDriver 司机信息
     * @return 结果
     */
    @Override
    public int insertPDriver(PDriver pDriver)
    {
        pDriver.setCreateTime(DateUtils.getNowDate());
        pDriver.setDriverId("driver_"+ IdUtils.randomUUID());
        pDriver.setState("0");
        pDriver.setIsBlacklist("0");
        return pDriverMapper.insertPDriver(pDriver);
    }

    /**
     * 修改司机信息
     * 
     * @param pDriver 司机信息
     * @return 结果
     */
    @Override
    public int updatePDriver(PDriver pDriver)
    {
        if(pDriver.getOpenId() == null) return 0;
        pDriver.setUpdateTime(DateUtils.getNowDate());
        String state = pDriver.getState();
        /*修改状态*/
        if("1".equals(state)){
            pPassengerMapper.updatePPassengerApplystate(pDriver.getOpenId());
        }
        return pDriverMapper.updatePDriver(pDriver);
    }

    /**
     * 批量删除司机信息
     * 
     * @param ids 需要删除的司机信息主键
     * @return 结果
     */
    @Override
    public int deletePDriverByIds(Long[] ids)
    {
        return pDriverMapper.deletePDriverByIds(ids);
    }

    /**
     * 删除司机信息信息
     * 
     * @param id 司机信息主键
     * @return 结果
     */
    @Override
    public int deletePDriverById(Long id)
    {
        return pDriverMapper.deletePDriverById(id);
    }

    @Override
    public PDriver getDriverInfo(PDriver pDriver) {
        return pDriverMapper.getDriverInfo(pDriver);
    }

    @Override
    public int agreePDriver(PDriver pDriver) {
        return pDriverMapper.agreePDriver(pDriver);
    }
}
