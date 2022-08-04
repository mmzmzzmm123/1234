package com.zeroone.crm.teacher.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zeroone.crm.teacher.mapper.TeacherMapper;
import com.zeroone.crm.teacher.domain.Teacher;
import com.zeroone.crm.teacher.service.ITeacherService;

/**
 * 老师信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-02
 */
@Service
public class TeacherServiceImpl implements ITeacherService 
{
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 查询老师信息
     * 
     * @param id 老师信息主键
     * @return 老师信息
     */
    @Override
    public Teacher selectTeacherById(Long id)
    {
        return teacherMapper.selectTeacherById(id);
    }

    /**
     * 查询老师信息列表
     * 
     * @param teacher 老师信息
     * @return 老师信息
     */
    @Override
    public List<Teacher> selectTeacherList(Teacher teacher)
    {
        return teacherMapper.selectTeacherList(teacher);
    }

    /**
     * 新增老师信息
     * 
     * @param teacher 老师信息
     * @return 结果
     */
    @Override
    public int insertTeacher(Teacher teacher)
    {
        return teacherMapper.insertTeacher(teacher);
    }

    /**
     * 修改老师信息
     * 
     * @param teacher 老师信息
     * @return 结果
     */
    @Override
    public int updateTeacher(Teacher teacher)
    {
        return teacherMapper.updateTeacher(teacher);
    }

    /**
     * 批量删除老师信息
     * 
     * @param ids 需要删除的老师信息主键
     * @return 结果
     */
    @Override
    public int deleteTeacherByIds(Long[] ids)
    {
        return teacherMapper.deleteTeacherByIds(ids);
    }

    /**
     * 删除老师信息信息
     * 
     * @param id 老师信息主键
     * @return 结果
     */
    @Override
    public int deleteTeacherById(Long id)
    {
        return teacherMapper.deleteTeacherById(id);
    }
}
