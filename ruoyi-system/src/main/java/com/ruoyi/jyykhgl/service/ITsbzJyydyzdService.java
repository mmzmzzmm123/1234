package com.ruoyi.jyykhgl.service;

import java.util.List;

import com.ruoyi.jyykhgl.domain.TsbzJyydyzd;

/**
 * 调研指导Service接口
 *
 * @author tsbz
 * @date 2021-01-15
 */
public interface ITsbzJyydyzdService {
    /**
     * 查询调研指导
     *
     * @param id 调研指导ID
     * @return 调研指导
     */
    public TsbzJyydyzd selectTsbzJyydyzdById(Long id);

    /**
     * 查询调研指导列表
     *
     * @param tsbzJyydyzd 调研指导
     * @return 调研指导集合
     */
    public List<TsbzJyydyzd> selectTsbzJyydyzdList(TsbzJyydyzd tsbzJyydyzd);

    /**
     * 新增调研指导
     *
     * @param tsbzJyydyzd 调研指导
     * @return 结果
     */
    public int insertTsbzJyydyzd(TsbzJyydyzd tsbzJyydyzd);

    /**
     * 修改调研指导
     *
     * @param tsbzJyydyzd 调研指导
     * @return 结果
     */
    public int updateTsbzJyydyzd(TsbzJyydyzd tsbzJyydyzd);

    /**
     * 批量删除调研指导
     *
     * @param ids 需要删除的调研指导ID
     * @return 结果
     */
    public int deleteTsbzJyydyzdByIds(Long[] ids);

    /**
     * 删除调研指导信息
     *
     * @param id 调研指导ID
     * @return 结果
     */
    public int deleteTsbzJyydyzdById(Long id);
}
