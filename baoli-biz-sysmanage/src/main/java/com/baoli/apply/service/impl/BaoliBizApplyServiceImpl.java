package com.baoli.apply.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.apply.mapper.BaoliBizApplyMapper;
import com.baoli.apply.domain.BaoliBizApply;
import com.baoli.apply.service.IBaoliBizApplyService;

/**
 * 申请管理Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-21
 */
@Service
public class BaoliBizApplyServiceImpl implements IBaoliBizApplyService 
{
    @Autowired
    private BaoliBizApplyMapper baoliBizApplyMapper;

    /**
     * 查询申请管理
     * 
     * @param id 申请管理主键
     * @return 申请管理
     */
    @Override
    public BaoliBizApply selectBaoliBizApplyById(Long id)
    {
        return baoliBizApplyMapper.selectBaoliBizApplyById(id);
    }

    /**
     * 查询申请管理列表
     * 
     * @param baoliBizApply 申请管理
     * @return 申请管理
     */
    @Override
    public List<BaoliBizApply> selectBaoliBizApplyList(BaoliBizApply baoliBizApply)
    {
        return baoliBizApplyMapper.selectBaoliBizApplyList(baoliBizApply);
    }

    /**
     * 新增申请管理
     * 
     * @param baoliBizApply 申请管理
     * @return 结果
     */
    @Override
    public int insertBaoliBizApply(BaoliBizApply baoliBizApply)
    {
        baoliBizApply.setCreateTime(DateUtils.getNowDate());
        return baoliBizApplyMapper.insertBaoliBizApply(baoliBizApply);
    }

    /**
     * 修改申请管理
     * 
     * @param baoliBizApply 申请管理
     * @return 结果
     */
    @Override
    public int updateBaoliBizApply(BaoliBizApply baoliBizApply)
    {
        baoliBizApply.setUpdateTime(DateUtils.getNowDate());
        return baoliBizApplyMapper.updateBaoliBizApply(baoliBizApply);
    }

    /**
     * 批量删除申请管理
     * 
     * @param ids 需要删除的申请管理主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizApplyByIds(Long[] ids)
    {
        return baoliBizApplyMapper.deleteBaoliBizApplyByIds(ids);
    }

    /**
     * 删除申请管理信息
     * 
     * @param id 申请管理主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizApplyById(Long id)
    {
        return baoliBizApplyMapper.deleteBaoliBizApplyById(id);
    }
}
