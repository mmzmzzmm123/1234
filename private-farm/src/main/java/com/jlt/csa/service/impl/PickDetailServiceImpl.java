package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.PickDetailMapper;
import com.jlt.csa.domain.PickDetail;
import com.jlt.csa.service.IPickDetailService;

/**
 * 采摘任务明细Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@Service
public class PickDetailServiceImpl implements IPickDetailService 
{
    @Autowired
    private PickDetailMapper pickDetailMapper;

    /**
     * 查询采摘任务明细
     * 
     * @param id 采摘任务明细主键
     * @return 采摘任务明细
     */
    @Override
    public PickDetail selectPickDetailById(Long id)
    {
        return pickDetailMapper.selectPickDetailById(id);
    }

    /**
     * 查询采摘任务明细列表
     * 
     * @param pickDetail 采摘任务明细
     * @return 采摘任务明细
     */
    @Override
    public List<PickDetail> selectPickDetailList(PickDetail pickDetail)
    {
        return pickDetailMapper.selectPickDetailList(pickDetail);
    }

    /**
     * 新增采摘任务明细
     * 
     * @param pickDetail 采摘任务明细
     * @return 结果
     */
    @Override
    public int insertPickDetail(PickDetail pickDetail)
    {
        pickDetail.setCreateTime(DateUtils.getNowDate());
        return pickDetailMapper.insertPickDetail(pickDetail);
    }

    /**
     * 修改采摘任务明细
     * 
     * @param pickDetail 采摘任务明细
     * @return 结果
     */
    @Override
    public int updatePickDetail(PickDetail pickDetail)
    {
        pickDetail.setUpdateTime(DateUtils.getNowDate());
        return pickDetailMapper.updatePickDetail(pickDetail);
    }

    /**
     * 批量删除采摘任务明细
     * 
     * @param ids 需要删除的采摘任务明细主键
     * @return 结果
     */
    @Override
    public int deletePickDetailByIds(Long[] ids)
    {
        return pickDetailMapper.deletePickDetailByIds(ids);
    }

    /**
     * 删除采摘任务明细信息
     * 
     * @param id 采摘任务明细主键
     * @return 结果
     */
    @Override
    public int deletePickDetailById(Long id)
    {
        return pickDetailMapper.deletePickDetailById(id);
    }
}
