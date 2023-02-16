package com.ruoyi.contest.service;

import java.util.List;

import com.ruoyi.contest.domain.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 教师信息Service接口
 *
 * @author ruoyi
 * @date 2023-02-16
 */
public interface ITeacherService extends IService<Teacher>
{
    /**
     * 查询教师信息列表
     *
     * @param teacher 教师信息
     * @return 教师信息集合
     */
    public List<Teacher> selectTeacherList(Teacher teacher);
}
