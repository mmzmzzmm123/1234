package com.ruoyi.jyykhgl.service;

import java.util.List;

import com.ruoyi.jyykhgl.domain.TsbzJyyjhzj;

/**
 * 计划总结（教研员）Service接口
 *
 * @author tsbz
 * @date 2021-01-15
 */
public interface ITsbzJyyjhzjService {
    /**
     * 查询计划总结（教研员）
     *
     * @param id 计划总结（教研员）ID
     * @return 计划总结（教研员）
     */
    public TsbzJyyjhzj selectTsbzJyyjhzjById(Long id);

    /**
     * 查询计划总结（教研员）列表
     *
     * @param tsbzJyyjhzj 计划总结（教研员）
     * @return 计划总结（教研员）集合
     */
    public List<TsbzJyyjhzj> selectTsbzJyyjhzjList(TsbzJyyjhzj tsbzJyyjhzj);

    /**
     * 新增计划总结（教研员）
     *
     * @param tsbzJyyjhzj 计划总结（教研员）
     * @return 结果
     */
    public int insertTsbzJyyjhzj(TsbzJyyjhzj tsbzJyyjhzj);

    /**
     * 修改计划总结（教研员）
     *
     * @param tsbzJyyjhzj 计划总结（教研员）
     * @return 结果
     */
    public int updateTsbzJyyjhzj(TsbzJyyjhzj tsbzJyyjhzj);

    /**
     * 批量删除计划总结（教研员）
     *
     * @param ids 需要删除的计划总结（教研员）ID
     * @return 结果
     */
    public int deleteTsbzJyyjhzjByIds(Long[] ids);

    /**
     * 删除计划总结（教研员）信息
     *
     * @param id 计划总结（教研员）ID
     * @return 结果
     */
    public int deleteTsbzJyyjhzjById(Long id);
}
