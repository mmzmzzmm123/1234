package com.ruoyi.jyykhgl.mapper;

import java.util.List;

import com.ruoyi.jyykhgl.domain.TsbzJyydyzd;

/**
 * 调研指导Mapper接口
 *
 * @author tsbz
 * @date 2021-01-15
 */
public interface TsbzJyydyzdMapper {
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
     * 删除调研指导
     *
     * @param id 调研指导ID
     * @return 结果
     */
    public int deleteTsbzJyydyzdById(Long id);

    /**
     * 批量删除调研指导
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJyydyzdByIds(Long[] ids);
}
