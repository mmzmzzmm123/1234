package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByMicrocourseMapper;
import com.ruoyi.project.benyi.domain.ByMicrocourse;
import com.ruoyi.project.benyi.service.IByMicrocourseService;

/**
 * 微型课程Service业务层处理
 *
 * @author tsbz
 * @date 2021-05-13
 */
@Service
public class ByMicrocourseServiceImpl implements IByMicrocourseService {
    @Autowired
    private ByMicrocourseMapper byMicrocourseMapper;

    /**
     * 查询微型课程
     *
     * @param id 微型课程ID
     * @return 微型课程
     */
    @Override
    public ByMicrocourse selectByMicrocourseById(Long id) {
        return byMicrocourseMapper.selectByMicrocourseById(id);
    }

    /**
     * 查询微型课程列表
     *
     * @param byMicrocourse 微型课程
     * @return 微型课程
     */
    @Override
    public List<ByMicrocourse> selectByMicrocourseList(ByMicrocourse byMicrocourse) {
        return byMicrocourseMapper.selectByMicrocourseList(byMicrocourse);
    }

    /**
     * 新增微型课程
     *
     * @param byMicrocourse 微型课程
     * @return 结果
     */
    @Override
    public int insertByMicrocourse(ByMicrocourse byMicrocourse) {
        byMicrocourse.setCreateTime(DateUtils.getNowDate());
        return byMicrocourseMapper.insertByMicrocourse(byMicrocourse);
    }

    /**
     * 修改微型课程
     *
     * @param byMicrocourse 微型课程
     * @return 结果
     */
    @Override
    public int updateByMicrocourse(ByMicrocourse byMicrocourse) {
        return byMicrocourseMapper.updateByMicrocourse(byMicrocourse);
    }

    /**
     * 批量删除微型课程
     *
     * @param ids 需要删除的微型课程ID
     * @return 结果
     */
    @Override
    public int deleteByMicrocourseByIds(Long[] ids) {
        return byMicrocourseMapper.deleteByMicrocourseByIds(ids);
    }

    /**
     * 删除微型课程信息
     *
     * @param id 微型课程ID
     * @return 结果
     */
    @Override
    public int deleteByMicrocourseById(Long id) {
        return byMicrocourseMapper.deleteByMicrocourseById(id);
    }
}
