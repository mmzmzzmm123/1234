package com.ruoyi.jxjs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJdcxMapper;
import com.ruoyi.jxjs.domain.TsbzJdcx;
import com.ruoyi.jxjs.service.ITsbzJdcxService;

/**
 * 基地区级审核Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
@Service
public class TsbzJdcxServiceImpl implements ITsbzJdcxService 
{
    @Autowired
    private TsbzJdcxMapper tsbzJdcxMapper;

    /**
     * 查询基地区级审核
     * 
     * @param id 基地区级审核ID
     * @return 基地区级审核
     */
    @Override
    public TsbzJdcx selectTsbzJdcxById(Long id)
    {
        return tsbzJdcxMapper.selectTsbzJdcxById(id);
    }

    /**
     * 查询基地区级审核列表
     * 
     * @param tsbzJdcx 基地区级审核
     * @return 基地区级审核
     */
    @Override
    public List<TsbzJdcx> selectTsbzJdcxList(TsbzJdcx tsbzJdcx)
    {
        return tsbzJdcxMapper.selectTsbzJdcxList(tsbzJdcx);
    }

    /**
     * 新增基地区级审核
     * 
     * @param tsbzJdcx 基地区级审核
     * @return 结果
     */
    @Override
    public int insertTsbzJdcx(TsbzJdcx tsbzJdcx)
    {
        tsbzJdcx.setCreateTime(DateUtils.getNowDate());
        return tsbzJdcxMapper.insertTsbzJdcx(tsbzJdcx);
    }

    /**
     * 修改基地区级审核
     * 
     * @param tsbzJdcx 基地区级审核
     * @return 结果
     */
    @Override
    public int updateTsbzJdcx(TsbzJdcx tsbzJdcx)
    {
        return tsbzJdcxMapper.updateTsbzJdcx(tsbzJdcx);
    }

    /**
     * 批量删除基地区级审核
     * 
     * @param ids 需要删除的基地区级审核ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdcxByIds(Long[] ids)
    {
        return tsbzJdcxMapper.deleteTsbzJdcxByIds(ids);
    }

    /**
     * 删除基地区级审核信息
     * 
     * @param id 基地区级审核ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdcxById(Long id)
    {
        return tsbzJdcxMapper.deleteTsbzJdcxById(id);
    }
}
