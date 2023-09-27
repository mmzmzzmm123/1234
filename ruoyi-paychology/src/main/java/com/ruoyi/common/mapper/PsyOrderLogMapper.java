package com.ruoyi.common.mapper;

import java.util.List;
import com.ruoyi.common.domain.PsyOrderLog;
import org.apache.ibatis.annotations.Param;

/**
 * 订单操作记录Mapper接口
 *
 * @author ruoyi
 * @date 2023-09-06
 */
public interface PsyOrderLogMapper
{
    /**
     * 查询订单操作记录
     *
     * @param oid 订单操作记录主键
     * @return 订单操作记录
     */
    PsyOrderLog selectPsyOrderLogByOid(String oid);

    /**
     * 查询订单操作记录列表
     *
     * @param psyOrderLog 订单操作记录
     * @return 订单操作记录集合
     */
    List<PsyOrderLog> selectPsyOrderLogList(PsyOrderLog psyOrderLog);

    /**
     * 新增订单操作记录
     *
     * @param psyOrderLog 订单操作记录
     * @return 结果
     */
    int insertPsyOrderLog(PsyOrderLog psyOrderLog);

    /**
     * 修改订单操作记录
     *
     * @param psyOrderLog 订单操作记录
     * @return 结果
     */
    int updatePsyOrderLog(PsyOrderLog psyOrderLog);

    int updatePsyOrderLogById(@Param("oid") String oid, @Param("nid") String nid);

    /**
     * 删除订单操作记录
     *
     * @param oid 订单操作记录主键
     * @return 结果
     */
    int deletePsyOrderLogByOid(String oid);

    /**
     * 批量删除订单操作记录
     *
     * @param oids 需要删除的数据主键集合
     * @return 结果
     */
    int deletePsyOrderLogByOids(String[] oids);
}
