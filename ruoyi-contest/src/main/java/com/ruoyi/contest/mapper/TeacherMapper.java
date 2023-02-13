package com.ruoyi.contest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.contest.domain.Teacher;

import java.util.List;

/**
 * <p>
 * 教师信息表 Mapper 接口
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
public interface TeacherMapper extends BaseMapper<Teacher> {
    /**
     * 查询教师信息列表
     *
     * @param teacher 教师信息
     * @return 教师信息集合
     */
    public List<Teacher> selectTeacherList(Teacher teacher);

}
