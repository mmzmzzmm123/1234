package com.ruoyi.contest.service.impl;

import com.ruoyi.contest.domain.Teacher;
import com.ruoyi.contest.mapper.TeacherMapper;
import com.ruoyi.contest.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 教师信息表 服务实现类
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public List<Teacher> selectTeacherList(Teacher teacher) {
        return baseMapper.selectTeacherList(teacher);
    }
}
