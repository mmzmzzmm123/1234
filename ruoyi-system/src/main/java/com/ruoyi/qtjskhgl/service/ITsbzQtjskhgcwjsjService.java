package com.ruoyi.qtjskhgl.service;

import java.util.List;

import com.ruoyi.qtjskhgl.domain.TsbzQtjskhgcwjsj;

/**
 * 群体教师考核过程文件数据Service接口
 *
 * @author ruoyi
 * @date 2020-09-17
 */
public interface ITsbzQtjskhgcwjsjService {
    /**
     * 查询群体教师考核过程文件数据
     *
     * @param id 群体教师考核过程文件数据ID
     * @return 群体教师考核过程文件数据
     */
    public TsbzQtjskhgcwjsj selectTsbzQtjskhgcwjsjById(Long id);

    /**
     * 查询群体教师考核过程文件数据列表
     *
     * @param tsbzQtjskhgcwjsj 群体教师考核过程文件数据
     * @return 群体教师考核过程文件数据集合
     */
    public List<TsbzQtjskhgcwjsj> selectTsbzQtjskhgcwjsjList(TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj);

    /**
     * 新增群体教师考核过程文件数据
     *
     * @param tsbzQtjskhgcwjsj 群体教师考核过程文件数据
     * @return 结果
     */
    public int insertTsbzQtjskhgcwjsj(TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj);

    /**
     * 修改群体教师考核过程文件数据
     *
     * @param tsbzQtjskhgcwjsj 群体教师考核过程文件数据
     * @return 结果
     */
    public int updateTsbzQtjskhgcwjsj(TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj);

    /**
     * 批量删除群体教师考核过程文件数据
     *
     * @param ids 需要删除的群体教师考核过程文件数据ID
     * @return 结果
     */
    public int deleteTsbzQtjskhgcwjsjByIds(Long[] ids);

    /**
     * 删除群体教师考核过程文件数据信息
     *
     * @param id 群体教师考核过程文件数据ID
     * @return 结果
     */
    public int deleteTsbzQtjskhgcwjsjById(Long id);
}
