package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByMicrocourse;

/**
 * 微型课程Mapper接口
 *
 * @author tsbz
 * @date 2021-05-13
 */
public interface ByMicrocourseMapper {
    /**
     * 查询微型课程
     *
     * @param id 微型课程ID
     * @return 微型课程
     */
    public ByMicrocourse selectByMicrocourseById(Long id);

    /**
     * 查询微型课程列表
     *
     * @param byMicrocourse 微型课程
     * @return 微型课程集合
     */
    public List<ByMicrocourse> selectByMicrocourseList(ByMicrocourse byMicrocourse);

    /**
     * 查询微型课程列表
     *
     * @param byMicrocourse 微型课程
     * @return 微型课程集合
     */
    public List<ByMicrocourse> selectByMicrocourseListTree(ByMicrocourse byMicrocourse);

    /**
     * 新增微型课程
     *
     * @param byMicrocourse 微型课程
     * @return 结果
     */
    public int insertByMicrocourse(ByMicrocourse byMicrocourse);

    /**
     * 修改微型课程
     *
     * @param byMicrocourse 微型课程
     * @return 结果
     */
    public int updateByMicrocourse(ByMicrocourse byMicrocourse);

    /**
     * 删除微型课程
     *
     * @param id 微型课程ID
     * @return 结果
     */
    public int deleteByMicrocourseById(Long id);

    /**
     * 批量删除微型课程
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByMicrocourseByIds(Long[] ids);
}
