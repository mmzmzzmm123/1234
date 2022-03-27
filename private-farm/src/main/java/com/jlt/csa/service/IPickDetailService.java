package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.PickDetail;

/**
 * 采摘任务明细Service接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface IPickDetailService 
{
    /**
     * 查询采摘任务明细
     * 
     * @param id 采摘任务明细主键
     * @return 采摘任务明细
     */
    public PickDetail selectPickDetailById(Long id);

    /**
     * 查询采摘任务明细列表
     * 
     * @param pickDetail 采摘任务明细
     * @return 采摘任务明细集合
     */
    public List<PickDetail> selectPickDetailList(PickDetail pickDetail);

    /**
     * 新增采摘任务明细
     * 
     * @param pickDetail 采摘任务明细
     * @return 结果
     */
    public int insertPickDetail(PickDetail pickDetail);

    /**
     * 修改采摘任务明细
     * 
     * @param pickDetail 采摘任务明细
     * @return 结果
     */
    public int updatePickDetail(PickDetail pickDetail);

    /**
     * 批量删除采摘任务明细
     * 
     * @param ids 需要删除的采摘任务明细主键集合
     * @return 结果
     */
    public int deletePickDetailByIds(Long[] ids);

    /**
     * 删除采摘任务明细信息
     * 
     * @param id 采摘任务明细主键
     * @return 结果
     */
    public int deletePickDetailById(Long id);
}
