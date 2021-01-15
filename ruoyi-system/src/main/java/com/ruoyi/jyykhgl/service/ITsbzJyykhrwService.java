package com.ruoyi.jyykhgl.service;

import java.util.List;

import com.ruoyi.jyykhgl.domain.TsbzJyykhrw;

/**
 * 教研员考核任务Service接口
 *
 * @author tsbz
 * @date 2021-01-15
 */
public interface ITsbzJyykhrwService {
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
     * 批量删除教研员考核任务
     *
     * @param ids 需要删除的教研员考核任务ID
     * @return 结果
     */
    public int deleteTsbzJyykhrwByIds(Long[] ids);

    /**
     * 删除教研员考核任务信息
     *
     * @param id 教研员考核任务ID
     * @return 结果
     */
    public int deleteTsbzJyykhrwById(Long id);
}
