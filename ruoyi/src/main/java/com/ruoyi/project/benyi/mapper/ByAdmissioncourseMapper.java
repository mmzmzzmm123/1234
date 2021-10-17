package com.ruoyi.project.benyi.mapper;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByAdmissioncourse;

/**
 * 入学准备课程Mapper接口
 *
 * @author tsbz
 * @date 2021-10-17
 */
public interface ByAdmissioncourseMapper {
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
     * 删除入学准备课程
     *
     * @param id 入学准备课程ID
     * @return 结果
     */
    public int deleteByAdmissioncourseById(Long id);

    /**
     * 批量删除入学准备课程
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByAdmissioncourseByIds(Long[] ids);
}
