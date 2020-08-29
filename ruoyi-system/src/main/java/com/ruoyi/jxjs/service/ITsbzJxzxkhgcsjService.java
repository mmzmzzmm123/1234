package com.ruoyi.jxjs.service;

import java.util.List;

import com.ruoyi.jxjs.domain.TsbzJxzxkhgcsj;

/**
 * 考核过程数据Service接口
 *
 * @author ruoyi
 * @date 2020-08-29
 */
public interface ITsbzJxzxkhgcsjService {
    /**
     * 查询考核过程数据
     *
     * @param id 考核过程数据ID
     * @return 考核过程数据
     */
    public TsbzJxzxkhgcsj selectTsbzJxzxkhgcsjById(String id);

    /**
     * 查询考核过程数据列表
     *
     * @param tsbzJxzxkhgcsj 考核过程数据
     * @return 考核过程数据集合
     */
    public List<TsbzJxzxkhgcsj> selectTsbzJxzxkhgcsjList(TsbzJxzxkhgcsj tsbzJxzxkhgcsj);

    /**
     * 新增考核过程数据
     *
     * @param tsbzJxzxkhgcsj 考核过程数据
     * @return 结果
     */
    public int insertTsbzJxzxkhgcsj(TsbzJxzxkhgcsj tsbzJxzxkhgcsj);

    /**
     * 修改考核过程数据
     *
     * @param tsbzJxzxkhgcsj 考核过程数据
     * @return 结果
     */
    public int updateTsbzJxzxkhgcsj(TsbzJxzxkhgcsj tsbzJxzxkhgcsj);

    /**
     * 批量删除考核过程数据
     *
     * @param ids 需要删除的考核过程数据ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhgcsjByIds(String[] ids);

    /**
     * 删除考核过程数据信息
     *
     * @param id 考核过程数据ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhgcsjById(String id);
}
