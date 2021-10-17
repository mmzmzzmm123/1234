package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByAdmissioncourseMapper;
import com.ruoyi.project.benyi.domain.ByAdmissioncourse;
import com.ruoyi.project.benyi.service.IByAdmissioncourseService;

/**
 * 入学准备课程Service业务层处理
 *
 * @author tsbz
 * @date 2021-10-17
 */
@Service
public class ByAdmissioncourseServiceImpl implements IByAdmissioncourseService {
    @Autowired
    private ByAdmissioncourseMapper byAdmissioncourseMapper;

    /**
     * 查询入学准备课程
     *
     * @param id 入学准备课程ID
     * @return 入学准备课程
     */
    @Override
    public ByAdmissioncourse selectByAdmissioncourseById(Long id) {
        return byAdmissioncourseMapper.selectByAdmissioncourseById(id);
    }

    /**
     * 查询入学准备课程列表
     *
     * @param byAdmissioncourse 入学准备课程
     * @return 入学准备课程
     */
    @Override
    public List<ByAdmissioncourse> selectByAdmissioncourseList(ByAdmissioncourse byAdmissioncourse) {
        return byAdmissioncourseMapper.selectByAdmissioncourseList(byAdmissioncourse);
    }

    /**
     * 新增入学准备课程
     *
     * @param byAdmissioncourse 入学准备课程
     * @return 结果
     */
    @Override
    public int insertByAdmissioncourse(ByAdmissioncourse byAdmissioncourse) {
        byAdmissioncourse.setCreateTime(DateUtils.getNowDate());
        return byAdmissioncourseMapper.insertByAdmissioncourse(byAdmissioncourse);
    }

    /**
     * 修改入学准备课程
     *
     * @param byAdmissioncourse 入学准备课程
     * @return 结果
     */
    @Override
    public int updateByAdmissioncourse(ByAdmissioncourse byAdmissioncourse) {
        return byAdmissioncourseMapper.updateByAdmissioncourse(byAdmissioncourse);
    }

    /**
     * 批量删除入学准备课程
     *
     * @param ids 需要删除的入学准备课程ID
     * @return 结果
     */
    @Override
    public int deleteByAdmissioncourseByIds(Long[] ids) {
        return byAdmissioncourseMapper.deleteByAdmissioncourseByIds(ids);
    }

    /**
     * 删除入学准备课程信息
     *
     * @param id 入学准备课程ID
     * @return 结果
     */
    @Override
    public int deleteByAdmissioncourseById(Long id) {
        return byAdmissioncourseMapper.deleteByAdmissioncourseById(id);
    }
}
