package com.jlt.csa.mapper;

import java.util.List;
import com.jlt.csa.domain.PickDetail;

/**
 * 采摘任务明细Mapper接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface PickDetailMapper 
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
     * 删除采摘任务明细
     * 
     * @param id 采摘任务明细主键
     * @return 结果
     */
    public int deletePickDetailById(Long id);

    /**
     * 批量删除采摘任务明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePickDetailByIds(Long[] ids);
}
