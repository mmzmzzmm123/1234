package com.ruoyi.classschedule.service.impl;

import java.util.List;

import com.ruoyi.classschedule.domain.ClazzCourseTeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.classschedule.mapper.ClazzCourseTeacherMapper;
import com.ruoyi.classschedule.domain.ClazzCourseTeacher;
import com.ruoyi.classschedule.service.IClazzCourseTeacherService;

/**
 * 老师_班级_课程Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-03
 */
@Service
public class ClazzCourseTeacherServiceImpl implements IClazzCourseTeacherService 
{
    @Autowired
    private ClazzCourseTeacherMapper clazzCourseTeacherMapper;

    /**
     * 查询老师_班级_课程
     * 
     * @param id 老师_班级_课程主键
     * @return 老师_班级_课程
     */
    @Override
    public ClazzCourseTeacher selectClazzCourseTeacherById(Long id)
    {
        return clazzCourseTeacherMapper.selectClazzCourseTeacherById(id);
    }

    /**
     * 查询老师_班级_课程列表
     * 
     * @param clazzCourseTeacher 老师_班级_课程
     * @return 老师_班级_课程
     */
    @Override
    public List<ClazzCourseTeacherVo> selectClazzCourseTeacherList(ClazzCourseTeacherVo clazzCourseTeacher)
    {
        return clazzCourseTeacherMapper.selectClazzCourseTeacherList(clazzCourseTeacher);
    }

    /**
     * 新增老师_班级_课程
     * 
     * @param clazzCourseTeacher 老师_班级_课程
     * @return 结果
     */
    @Override
    public int insertClazzCourseTeacher(ClazzCourseTeacher clazzCourseTeacher)
    {
        return clazzCourseTeacherMapper.insertClazzCourseTeacher(clazzCourseTeacher);
    }

    /**
     * 修改老师_班级_课程
     * 
     * @param clazzCourseTeacher 老师_班级_课程
     * @return 结果
     */
    @Override
    public int updateClazzCourseTeacher(ClazzCourseTeacher clazzCourseTeacher)
    {
        return clazzCourseTeacherMapper.updateClazzCourseTeacher(clazzCourseTeacher);
    }

    /**
     * 批量删除老师_班级_课程
     * 
     * @param ids 需要删除的老师_班级_课程主键
     * @return 结果
     */
    @Override
    public int deleteClazzCourseTeacherByIds(Long[] ids)
    {
        return clazzCourseTeacherMapper.deleteClazzCourseTeacherByIds(ids);
    }

    /**
     * 删除老师_班级_课程信息
     * 
     * @param id 老师_班级_课程主键
     * @return 结果
     */
    @Override
    public int deleteClazzCourseTeacherById(Long id)
    {
        return clazzCourseTeacherMapper.deleteClazzCourseTeacherById(id);
    }
}
