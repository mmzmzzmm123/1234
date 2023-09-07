package com.ruoyi.common.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.mapper.PsyOrderLogMapper;
import com.ruoyi.common.domain.PsyOrderLog;
import com.ruoyi.common.service.IPsyOrderLogService;

/**
 * 订单操作记录Service业务层处理
 *
 * @author ruoyi
 * @date 2023-09-06
 */
@Service
public class PsyOrderLogServiceImpl implements IPsyOrderLogService
{
    @Autowired
    private PsyOrderLogMapper psyOrderLogMapper;

    /**
     * 查询订单操作记录
     *
     * @param oid 订单操作记录主键
     * @return 订单操作记录
     */
    @Override
    public PsyOrderLog selectPsyOrderLogByOid(String oid)
    {
        return psyOrderLogMapper.selectPsyOrderLogByOid(oid);
    }

    /**
     * 查询订单操作记录列表
     *
     * @param psyOrderLog 订单操作记录
     * @return 订单操作记录
     */
    @Override
    public List<PsyOrderLog> selectPsyOrderLogList(PsyOrderLog psyOrderLog)
    {
        return psyOrderLogMapper.selectPsyOrderLogList(psyOrderLog);
    }

    /**
     * 新增订单操作记录
     *
     * @param psyOrderLog 订单操作记录
     * @return 结果
     */
    @Override
    public int insertPsyOrderLog(PsyOrderLog psyOrderLog)
    {
        psyOrderLog.setCreateTime(DateUtils.getNowDate());
        return psyOrderLogMapper.insertPsyOrderLog(psyOrderLog);
    }

    /**
     * 修改订单操作记录
     *
     * @param psyOrderLog 订单操作记录
     * @return 结果
     */
    @Override
    public int updatePsyOrderLog(PsyOrderLog psyOrderLog)
    {
        return psyOrderLogMapper.updatePsyOrderLog(psyOrderLog);
    }

    /**
     * 批量删除订单操作记录
     *
     * @param oids 需要删除的订单操作记录主键
     * @return 结果
     */
    @Override
    public int deletePsyOrderLogByOids(String[] oids)
    {
        return psyOrderLogMapper.deletePsyOrderLogByOids(oids);
    }

    /**
     * 删除订单操作记录信息
     *
     * @param oid 订单操作记录主键
     * @return 结果
     */
    @Override
    public int deletePsyOrderLogByOid(String oid)
    {
        return psyOrderLogMapper.deletePsyOrderLogByOid(oid);
    }
}
