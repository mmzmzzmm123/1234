package com.ruoyi.jxjs.service;

import java.util.List;

import com.ruoyi.jxjs.domain.TsbzJzxzkhsh;

/**
 * 考核审核过程Service接口
 *
 * @author ruoyi
 * @date 2020-08-29
 */
public interface ITsbzJzxzkhshService {
    /**
     * 查询考核审核过程
     *
     * @param id 考核审核过程ID
     * @return 考核审核过程
     */
    public TsbzJzxzkhsh selectTsbzJzxzkhshById(Long id);

    /**
     * 查询考核审核过程列表
     *
     * @param tsbzJzxzkhsh 考核审核过程
     * @return 考核审核过程集合
     */
    public List<TsbzJzxzkhsh> selectTsbzJzxzkhshList(TsbzJzxzkhsh tsbzJzxzkhsh);

    /**
     * 新增考核审核过程
     *
     * @param tsbzJzxzkhsh 考核审核过程
     * @return 结果
     */
    public int insertTsbzJzxzkhsh(TsbzJzxzkhsh tsbzJzxzkhsh);

    /**
     * 修改考核审核过程
     *
     * @param tsbzJzxzkhsh 考核审核过程
     * @return 结果
     */
    public int updateTsbzJzxzkhsh(TsbzJzxzkhsh tsbzJzxzkhsh);

    /**
     * 批量删除考核审核过程
     *
     * @param ids 需要删除的考核审核过程ID
     * @return 结果
     */
    public int deleteTsbzJzxzkhshByIds(Long[] ids);

    /**
     * 删除考核审核过程信息
     *
     * @param id 考核审核过程ID
     * @return 结果
     */
    public int deleteTsbzJzxzkhshById(Long id);
}
