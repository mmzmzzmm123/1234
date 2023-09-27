package com.ruoyi.common.service;

import java.util.List;
import com.ruoyi.common.domain.PsyOrderLog;

/**
 * 订单操作记录Service接口
 *
 * @author ruoyi
 * @date 2023-09-06
 */
public interface IPsyOrderLogService
{
    /**
     * 查询订单操作记录
     *
     * @param oid 订单操作记录主键
     * @return 订单操作记录
     */
    public PsyOrderLog selectPsyOrderLogByOid(String oid);

    /**
     * 查询订单操作记录列表
     *
     * @param psyOrderLog 订单操作记录
     * @return 订单操作记录集合
     */
    public List<PsyOrderLog> selectPsyOrderLogList(PsyOrderLog psyOrderLog);

    /**
     * 新增订单操作记录
     *
     * @param psyOrderLog 订单操作记录
     * @return 结果
     */
    public int insertPsyOrderLog(PsyOrderLog psyOrderLog);

    /**
     * 修改订单操作记录
     *
     * @param psyOrderLog 订单操作记录
     * @return 结果
     */
    public int updatePsyOrderLog(PsyOrderLog psyOrderLog);

    public void updatePsyOrderLogById(String oid, String nid);

    /**
     * 批量删除订单操作记录
     *
     * @param oids 需要删除的订单操作记录主键集合
     * @return 结果
     */
    public int deletePsyOrderLogByOids(String[] oids);

    /**
     * 删除订单操作记录信息
     *
     * @param oid 订单操作记录主键
     * @return 结果
     */
    public int deletePsyOrderLogByOid(String oid);
}
