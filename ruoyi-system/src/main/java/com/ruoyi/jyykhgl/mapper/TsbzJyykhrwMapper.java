package com.ruoyi.jyykhgl.mapper;

import java.util.List;

import com.ruoyi.jyykhgl.domain.TsbzJyykhrw;

/**
 * 教研员考核任务Mapper接口
 *
 * @author tsbz
 * @date 2021-01-15
 */
public interface TsbzJyykhrwMapper {
    /**
     * 查询教研员考核任务
     *
     * @param id 教研员考核任务ID
     * @return 教研员考核任务
     */
    public TsbzJyykhrw selectTsbzJyykhrwById(Long id);

    /**
     * 查询教研员考核任务列表
     *
     * @param tsbzJyykhrw 教研员考核任务
     * @return 教研员考核任务集合
     */
    public List<TsbzJyykhrw> selectTsbzJyykhrwList(TsbzJyykhrw tsbzJyykhrw);

    /**
     * 查询教研员考核任务列表
     *
     * @param tsbzJyykhrw 教研员考核任务
     * @return 教研员考核任务集合
     */
    public List<TsbzJyykhrw> selectTsbzJyykhrwStatistics(TsbzJyykhrw tsbzJyykhrw);

    /**
     * 查询教研员考核任务列表
     *
     * @param tsbzJyykhrw 教研员考核任务
     * @return 教研员考核任务集合
     */
    public List<TsbzJyykhrw> selectTsbzJyykhrwBmWcqkStatistics(TsbzJyykhrw tsbzJyykhrw);

    /**
     * 新增教研员考核任务
     *
     * @param tsbzJyykhrw 教研员考核任务
     * @return 结果
     */
    public int insertTsbzJyykhrw(TsbzJyykhrw tsbzJyykhrw);

    /**
     * 修改教研员考核任务
     *
     * @param tsbzJyykhrw 教研员考核任务
     * @return 结果
     */
    public int updateTsbzJyykhrw(TsbzJyykhrw tsbzJyykhrw);

    /**
     * 删除教研员考核任务
     *
     * @param id 教研员考核任务ID
     * @return 结果
     */
    public int deleteTsbzJyykhrwById(Long id);

    /**
     * 批量删除教研员考核任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJyykhrwByIds(Long[] ids);

    /**
     * 批量删除教研员考核任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJyykhrwByPids(Long[] ids);

}
