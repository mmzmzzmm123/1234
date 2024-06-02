package com.baoli.apply.service;

import java.util.List;
import com.baoli.apply.domain.BaoliBizApply;

/**
 * 申请管理Service接口
 * 
 * @author niujs
 * @date 2024-04-21
 */
public interface IBaoliBizApplyService 
{
    /**
     * 查询申请管理
     * 
     * @param id 申请管理主键
     * @return 申请管理
     */
    public BaoliBizApply selectBaoliBizApplyById(Long id);

    /**
     * 查询申请管理列表
     * 
     * @param baoliBizApply 申请管理
     * @return 申请管理集合
     */
    public List<BaoliBizApply> selectBaoliBizApplyList(BaoliBizApply baoliBizApply);

    /**
     * 新增申请管理
     * 
     * @param baoliBizApply 申请管理
     * @return 结果
     */
    public int insertBaoliBizApply(BaoliBizApply baoliBizApply);

    /**
     * 修改申请管理
     * 
     * @param baoliBizApply 申请管理
     * @return 结果
     */
    public int updateBaoliBizApply(BaoliBizApply baoliBizApply);

    /**
     * 批量删除申请管理
     * 
     * @param ids 需要删除的申请管理主键集合
     * @return 结果
     */
    public int deleteBaoliBizApplyByIds(Long[] ids);

    /**
     * 删除申请管理信息
     * 
     * @param id 申请管理主键
     * @return 结果
     */
    public int deleteBaoliBizApplyById(Long id);
}
