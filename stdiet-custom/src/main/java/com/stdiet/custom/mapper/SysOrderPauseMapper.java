package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysOrderPause;
import org.apache.ibatis.annotations.Param;

/**
 * 订单服务暂停Mapper接口
 *
 * @author wonder
 * @date 2021-01-07
 */
public interface SysOrderPauseMapper
{
    /**
     * 查询订单服务暂停
     *
     * @param id 订单服务暂停ID
     * @return 订单服务暂停
     */
    public SysOrderPause selectSysOrderPauseById(Long id);

    /**
     * 查询订单服务暂停列表
     *
     * @param sysOrderPause 订单服务暂停
     * @return 订单服务暂停集合
     */
    public List<SysOrderPause> selectSysOrderPauseList(SysOrderPause sysOrderPause);

    /**
     * 新增订单服务暂停
     *
     * @param sysOrderPause 订单服务暂停
     * @return 结果
     */
    public int insertSysOrderPause(SysOrderPause sysOrderPause);

    /**
     * 修改订单服务暂停
     *
     * @param sysOrderPause 订单服务暂停
     * @return 结果
     */
    public int updateSysOrderPause(SysOrderPause sysOrderPause);

    /**
     * 删除订单服务暂停
     *
     * @param id 订单服务暂停ID
     * @return 结果
     */
    public int deleteSysOrderPauseById(Long id);

    /**
     * 批量删除订单服务暂停
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysOrderPauseByIds(Long[] ids);

    /**
     * 根据订单ID、时间范围查询数量
     * @param sysOrderPause
     * @return
     */
    int getCountByCusIdAndPauseDate(SysOrderPause sysOrderPause);

    /**
     * 根据订单ID删除暂停记录
     * @param orderIds
     * @return
     */
    int deletePauseByOrderId(Long[] orderIds);

    long selectNearMainOrderIdByCusId(Long cusId);

    /**
     * 根据订单ID获取暂停计划
     * @param orderId
     * @return
     */
    List<SysOrderPause> getPauseListByOrderId(@Param("orderId")Long orderId);

    /**
     * 根据客户ID、订单ID查询暂停记录
     * @param sysOrderPause
     * @return
     */
    List<SysOrderPause> getPauseListByCusIdAndOrderId(SysOrderPause sysOrderPause);
}