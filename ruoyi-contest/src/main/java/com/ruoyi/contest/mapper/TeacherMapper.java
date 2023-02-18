package com.ruoyi.contest.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.contest.domain.Teacher;

/**
 * 教师信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-16
 */
public interface TeacherMapper extends BaseMapper<Teacher>
{

    /**
     * 查询教师信息列表
     *
     * @param teacher 教师信息
     * @return 教师信息集合
     */
    public List<Teacher> selectTeacherList(Teacher teacher);

}
