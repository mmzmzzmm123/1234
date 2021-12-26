package com.ruoyi.carpool.mapper;

import java.util.List;
import com.ruoyi.carpool.domain.PDriver;

/**
 * 司机信息Mapper接口
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public interface PDriverMapper 
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
     * 删除司机信息
     * 
     * @param id 司机信息主键
     * @return 结果
     */
    public int deletePDriverById(Long id);

    /**
     * 批量删除司机信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePDriverByIds(Long[] ids);


    /**
     * 通过电话号码和用户名查询司机的详情
     * @param pDriver
     * @return
     */
    public PDriver getDriverInfo(PDriver pDriver);

    /**
     * 修改司机信息
     *
     * @param pDriver 司机信息
     * @return 结果
     */
    public int agreePDriver(PDriver pDriver);

    /**
     * 修改司机信息
     *
     * @param pDriver 司机信息
     * @return 结果
     */
    public PDriver getDriverInfoByOpenId(String openId);

}
