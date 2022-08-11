package com.ruoyi.classschedule.mapper;

import java.util.List;
import com.ruoyi.classschedule.domain.ClazzCourseTeacher;
import com.ruoyi.classschedule.domain.ClazzCourseTeacherVo;

/**
 * 老师_班级_课程Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-03
 */
public interface ClazzCourseTeacherMapper 
{
    /**
     * 查询老师_班级_课程
     * 
     * @param id 老师_班级_课程主键
     * @return 老师_班级_课程
     */
    public ClazzCourseTeacher selectClazzCourseTeacherById(Long id);

    /**
     * 查询老师_班级_课程列表
     * 
     * @param clazzCourseTeacher 老师_班级_课程
     * @return 老师_班级_课程集合
     */
    public List<ClazzCourseTeacherVo> selectClazzCourseTeacherList(ClazzCourseTeacherVo clazzCourseTeacher);

    /**
     * 新增老师_班级_课程
     * 
     * @param clazzCourseTeacher 老师_班级_课程
     * @return 结果
     */
    public int insertClazzCourseTeacher(ClazzCourseTeacher clazzCourseTeacher);

    /**
     * 修改老师_班级_课程
     * 
     * @param clazzCourseTeacher 老师_班级_课程
     * @return 结果
     */
    public int updateClazzCourseTeacher(ClazzCourseTeacher clazzCourseTeacher);

    /**
     * 删除老师_班级_课程
     * 
     * @param id 老师_班级_课程主键
     * @return 结果
     */
    public int deleteClazzCourseTeacherById(Long id);

    /**
     * 批量删除老师_班级_课程
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClazzCourseTeacherByIds(Long[] ids);
}
