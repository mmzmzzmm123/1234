package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysOrderNutritionistReplaceRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 订单营养师、售后更换记录Mapper接口
 *
 * @author xzj
 * @date 2021-04-02
 */
public interface SysOrderNutritionistReplaceRecordMapper
{
    /**
     * 查询订单营养师、售后更换记录
     *
     * @param id 订单营养师、售后更换记录ID
     * @return 订单营养师、售后更换记录
     */
    public SysOrderNutritionistReplaceRecord selectSysOrderNutritionistReplaceRecordById(Long id);

    /**
     * 查询订单营养师、售后更换记录列表
     *
     * @param sysOrderNutritionistReplaceRecord 订单营养师、售后更换记录
     * @return 订单营养师、售后更换记录集合
     */
    public List<SysOrderNutritionistReplaceRecord> selectSysOrderNutritionistReplaceRecordList(SysOrderNutritionistReplaceRecord sysOrderNutritionistReplaceRecord);

    /**
     * 新增订单营养师、售后更换记录
     *
     * @param sysOrderNutritionistReplaceRecord 订单营养师、售后更换记录
     * @return 结果
     */
    public int insertSysOrderNutritionistReplaceRecord(SysOrderNutritionistReplaceRecord sysOrderNutritionistReplaceRecord);

    /**
     * 修改订单营养师、售后更换记录
     *
     * @param sysOrderNutritionistReplaceRecord 订单营养师、售后更换记录
     * @return 结果
     */
    public int updateSysOrderNutritionistReplaceRecord(SysOrderNutritionistReplaceRecord sysOrderNutritionistReplaceRecord);

    /**
     * 删除订单营养师、售后更换记录
     *
     * @param id 订单营养师、售后更换记录ID
     * @return 结果
     */
    public int deleteSysOrderNutritionistReplaceRecordById(Long id);

    /**
     * 批量删除订单营养师、售后更换记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysOrderNutritionistReplaceRecordByIds(Long[] ids);

    /**
     * 根据订单ID查询对应营养师、售后更换记录
     * @param orderId
     * @return
     */
    List<SysOrderNutritionistReplaceRecord> getSysOrderReplaceRecordByOrderId(@Param("orderId")Long orderId);

    /**
     * 根据用户ID查询营养师、售后更换记录
     * @param userId
     * @return
     */
    List<SysOrderNutritionistReplaceRecord> getSysOrderReplaceRecordByUserId(@Param("userId")Long userId);
}