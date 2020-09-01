package com.ruoyi.jxzxkhgl.service;

import java.util.List;

import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhsh;

/**
 * 考核审核过程Service接口
 *
 * @author ruoyi
 * @date 2020-08-29
 */
public interface ITsbzJxzxkhshService {
    /**
     * 查询考核审核过程
     *
     * @param id 考核审核过程ID
     * @return 考核审核过程
     */
    public TsbzJxzxkhsh selectTsbzJzxzkhshById(Long id);

    /**
     * 查询考核审核过程列表
     *
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 考核审核过程集合
     */
    public List<TsbzJxzxkhsh> selectTsbzJzxzkhshList(TsbzJxzxkhsh tsbzJxzxkhsh);

    /**
     * 新增考核审核过程
     *
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 结果
     */
    public int insertTsbzJzxzkhsh(TsbzJxzxkhsh tsbzJxzxkhsh);

    /**
     * 修改考核审核过程
     *
     * @param tsbzJxzxkhsh 考核审核过程
     * @return 结果
     */
    public int updateTsbzJzxzkhsh(TsbzJxzxkhsh tsbzJxzxkhsh);

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
