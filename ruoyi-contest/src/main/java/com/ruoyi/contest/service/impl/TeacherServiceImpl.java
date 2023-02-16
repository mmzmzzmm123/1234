package com.ruoyi.contest.service.impl;

import java.util.Arrays;
import java.util.List;

import com.ruoyi.contest.domain.Teacher;
import com.ruoyi.contest.mapper.TeacherMapper;
import com.ruoyi.contest.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;

/**
 * 教师信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-16
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService
{
    /**
     * 查询教师信息列表
     *
     * @param teacher 教师信息
     * @return 教师信息
     */
    @Override
    public List<Teacher> selectTeacherList(Teacher teacher)
    {
        return baseMapper.selectTeacherList(teacher);
    }

}
