package com.ruoyi.qtjs.mapper;

import java.util.List;

import com.ruoyi.qtjs.domain.TsbzQtjspxsh;

/**
 * 群体教师评选审核过程Mapper接口
 *
 * @author ruoyi
 * @date 2020-09-16
 */
public interface TsbzQtjspxshMapper {
    /**
     * 查询群体教师评选审核过程
     *
     * @param id 群体教师评选审核过程ID
     * @return 群体教师评选审核过程
     */
    public TsbzQtjspxsh selectTsbzQtjspxshById(Long id);

    /**
     * 查询群体教师评选审核过程列表
     *
     * @param tsbzQtjspxsh 群体教师评选审核过程
     * @return 群体教师评选审核过程集合
     */
    public List<TsbzQtjspxsh> selectTsbzQtjspxshList(TsbzQtjspxsh tsbzQtjspxsh);

    /**
     * 新增群体教师评选审核过程
     *
     * @param tsbzQtjspxsh 群体教师评选审核过程
     * @return 结果
     */
    public int insertTsbzQtjspxsh(TsbzQtjspxsh tsbzQtjspxsh);

    /**
     * 修改群体教师评选审核过程
     *
     * @param tsbzQtjspxsh 群体教师评选审核过程
     * @return 结果
     */
    public int updateTsbzQtjspxsh(TsbzQtjspxsh tsbzQtjspxsh);

    /**
     * 删除群体教师评选审核过程
     *
     * @param id 群体教师评选审核过程ID
     * @return 结果
     */
    public int deleteTsbzQtjspxshById(Long id);

    /**
     * 批量删除群体教师评选审核过程
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzQtjspxshByIds(Long[] ids);
}
