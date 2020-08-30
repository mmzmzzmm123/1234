package com.ruoyi.jxzxkhgl.mapper;

import java.util.List;

import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhgcsj;

/**
 * 考核过程数据Mapper接口
 *
 * @author ruoyi
 * @date 2020-08-29
 */
public interface TsbzJxzxkhgcsjMapper {
    /**
     * 查询考核过程数据
     *
     * @param tsbzJxzxkhgcsj 考核过程数据ID
     * @return 考核过程数据
     */
    public TsbzJxzxkhgcsj selectTsbzJxzxkhgcsjById(TsbzJxzxkhgcsj tsbzJxzxkhgcsj);

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
     * 删除考核过程数据
     *
     * @param id 考核过程数据ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhgcsjById(String id);

    /**
     * 批量删除考核过程数据
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhgcsjByIds(String[] ids);
}
