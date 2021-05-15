package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.benyi.domain.ByMicrocourse;
import com.ruoyi.project.benyi.domain.ByTheme;

/**
 * 微型课程Service接口
 *
 * @author tsbz
 * @date 2021-05-13
 */
public interface IByMicrocourseService {
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
     * 构建前端所需要树结构
     *
     * @param byMicrocourses 部门列表
     * @return 树结构列表
     */
    public List<ByMicrocourse> buildMicrocourseDetailTree(List<ByMicrocourse> byMicrocourses);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param byMicrocourses 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildMicrocourseTreeSelect(List<ByMicrocourse> byMicrocourses);

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
     * 批量删除微型课程
     *
     * @param ids 需要删除的微型课程ID
     * @return 结果
     */
    public int deleteByMicrocourseByIds(Long[] ids);

    /**
     * 删除微型课程信息
     *
     * @param id 微型课程ID
     * @return 结果
     */
    public int deleteByMicrocourseById(Long id);
}
