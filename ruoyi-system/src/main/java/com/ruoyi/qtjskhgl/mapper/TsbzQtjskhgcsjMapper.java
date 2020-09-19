package com.ruoyi.qtjskhgl.mapper;

import java.util.List;

import com.ruoyi.qtjskhgl.domain.TsbzQtjskhgcsj;

/**
 * 群体教师考核过程数据Mapper接口
 *
 * @author ruoyi
 * @date 2020-09-17
 */
public interface TsbzQtjskhgcsjMapper {
    /**
     * 查询群体教师考核过程数据
     *
     * @param tsbzQtjskhgcsj 群体教师考核过程数据ID
     * @return 群体教师考核过程数据
     */
    public TsbzQtjskhgcsj selectTsbzQtjskhgcsjById(TsbzQtjskhgcsj tsbzQtjskhgcsj);

    /**
     * 查询群体教师考核过程数据列表
     *
     * @param tsbzQtjskhgcsj 群体教师考核过程数据
     * @return 群体教师考核过程数据集合
     */
    public List<TsbzQtjskhgcsj> selectTsbzQtjskhgcsjList(TsbzQtjskhgcsj tsbzQtjskhgcsj);

    /**
     * 新增群体教师考核过程数据
     *
     * @param tsbzQtjskhgcsj 群体教师考核过程数据
     * @return 结果
     */
    public int insertTsbzQtjskhgcsj(TsbzQtjskhgcsj tsbzQtjskhgcsj);

    /**
     * 修改群体教师考核过程数据
     *
     * @param tsbzQtjskhgcsj 群体教师考核过程数据
     * @return 结果
     */
    public int updateTsbzQtjskhgcsj(TsbzQtjskhgcsj tsbzQtjskhgcsj);

    /**
     * 删除群体教师考核过程数据
     *
     * @param id 群体教师考核过程数据ID
     * @return 结果
     */
    public int deleteTsbzQtjskhgcsjById(String id);

    /**
     * 批量删除群体教师考核过程数据
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzQtjskhgcsjByIds(String[] ids);
}
