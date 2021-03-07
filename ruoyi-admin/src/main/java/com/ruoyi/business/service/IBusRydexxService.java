package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.BusRydexx;

/**
 * 人员定额信息Service接口
 * 
 * @author yaowei
 * @date 2021-02-23
 */
public interface IBusRydexxService 
{
    /**
     * 查询人员定额信息
     * 
     * @param id 人员定额信息ID
     * @return 人员定额信息
     */
    public BusRydexx selectBusRydexxById(Long id);

    /**
     * 查询人员定额信息列表
     * 
     * @param busRydexx 人员定额信息
     * @return 人员定额信息集合
     */
    public List<BusRydexx> selectBusRydexxList(BusRydexx busRydexx);

    /**
     * 新增人员定额信息
     * 
     * @param busRydexx 人员定额信息
     * @return 结果
     */
    public int insertBusRydexx(BusRydexx busRydexx);

    /**
     * 修改人员定额信息
     * 
     * @param busRydexx 人员定额信息
     * @return 结果
     */
    public int updateBusRydexx(BusRydexx busRydexx);

    /**
     * 批量删除人员定额信息
     * 
     * @param ids 需要删除的人员定额信息ID
     * @return 结果
     */
    public int deleteBusRydexxByIds(Long[] ids);

    /**
     * 删除人员定额信息信息
     * 
     * @param id 人员定额信息ID
     * @return 结果
     */
    public int deleteBusRydexxById(Long id);
}
