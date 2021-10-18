package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.benyi.domain.ByAdmissioncourse;
import com.ruoyi.project.benyi.domain.ByMicrocourse;

/**
 * 入学准备课程Service接口
 *
 * @author tsbz
 * @date 2021-10-17
 */
public interface IByAdmissioncourseService {
    /**
     * 查询入学准备课程
     *
     * @param id 入学准备课程ID
     * @return 入学准备课程
     */
    public ByAdmissioncourse selectByAdmissioncourseById(Long id);

    /**
     * 查询入学准备课程列表
     *
     * @param byAdmissioncourse 入学准备课程
     * @return 入学准备课程集合
     */
    public List<ByAdmissioncourse> selectByAdmissioncourseList(ByAdmissioncourse byAdmissioncourse);

    /**
     * 查询入学准备课程列表
     *
     * @param byAdmissioncourse 入学准备课程
     * @return 入学准备课程程集合
     */
    public List<ByAdmissioncourse> selectByAdmissioncourseListTree(ByAdmissioncourse byAdmissioncourse);

    /**
     * 构建前端所需要树结构
     *
     * @param byAdmissioncourses 部门列表
     * @return 树结构列表
     */
    public List<ByAdmissioncourse> buildByAdmissioncourseDetailTree(List<ByAdmissioncourse> byAdmissioncourses);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param byAdmissioncourses 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildByAdmissioncourseTreeSelect(List<ByAdmissioncourse> byAdmissioncourses);

    /**
     * 新增入学准备课程
     *
     * @param byAdmissioncourse 入学准备课程
     * @return 结果
     */
    public int insertByAdmissioncourse(ByAdmissioncourse byAdmissioncourse);

    /**
     * 修改入学准备课程
     *
     * @param byAdmissioncourse 入学准备课程
     * @return 结果
     */
    public int updateByAdmissioncourse(ByAdmissioncourse byAdmissioncourse);

    /**
     * 批量删除入学准备课程
     *
     * @param ids 需要删除的入学准备课程ID
     * @return 结果
     */
    public int deleteByAdmissioncourseByIds(Long[] ids);

    /**
     * 删除入学准备课程信息
     *
     * @param id 入学准备课程ID
     * @return 结果
     */
    public int deleteByAdmissioncourseById(Long id);
}
