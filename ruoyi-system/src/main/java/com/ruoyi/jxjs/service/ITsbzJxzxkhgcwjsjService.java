package com.ruoyi.jxjs.service;

import java.util.List;

import com.ruoyi.jxjs.domain.TsbzJxzxkhgcwjsj;

/**
 * 考核过程文件数据Service接口
 *
 * @author ruoyi
 * @date 2020-08-29
 */
public interface ITsbzJxzxkhgcwjsjService {
    /**
     * 查询考核过程文件数据
     *
     * @param id 考核过程文件数据ID
     * @return 考核过程文件数据
     */
    public TsbzJxzxkhgcwjsj selectTsbzJxzxkhgcwjsjById(Long id);

    /**
     * 查询考核过程文件数据列表
     *
     * @param tsbzJxzxkhgcwjsj 考核过程文件数据
     * @return 考核过程文件数据集合
     */
    public List<TsbzJxzxkhgcwjsj> selectTsbzJxzxkhgcwjsjList(TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj);

    /**
     * 新增考核过程文件数据
     *
     * @param tsbzJxzxkhgcwjsj 考核过程文件数据
     * @return 结果
     */
    public int insertTsbzJxzxkhgcwjsj(TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj);

    /**
     * 修改考核过程文件数据
     *
     * @param tsbzJxzxkhgcwjsj 考核过程文件数据
     * @return 结果
     */
    public int updateTsbzJxzxkhgcwjsj(TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj);

    /**
     * 批量删除考核过程文件数据
     *
     * @param ids 需要删除的考核过程文件数据ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhgcwjsjByIds(Long[] ids);

    /**
     * 删除考核过程文件数据信息
     *
     * @param id 考核过程文件数据ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhgcwjsjById(Long id);
}
