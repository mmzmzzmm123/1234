package com.zeroone.crm.teacher.service;

import java.util.List;
import com.zeroone.crm.teacher.domain.Teacher;

/**
 * 老师信息Service接口
 * 
 * @author ruoyi
 * @date 2022-08-02
 */
public interface ITeacherService 
{
    /**
     * 查询老师信息
     * 
     * @param id 老师信息主键
     * @return 老师信息
     */
    public Teacher selectTeacherById(Long id);

    /**
     * 查询老师信息列表
     * 
     * @param teacher 老师信息
     * @return 老师信息集合
     */
    public List<Teacher> selectTeacherList(Teacher teacher);

    /**
     * 新增老师信息
     * 
     * @param teacher 老师信息
     * @return 结果
     */
    public int insertTeacher(Teacher teacher);

    /**
     * 修改老师信息
     * 
     * @param teacher 老师信息
     * @return 结果
     */
    public int updateTeacher(Teacher teacher);

    /**
     * 批量删除老师信息
     * 
     * @param ids 需要删除的老师信息主键集合
     * @return 结果
     */
    public int deleteTeacherByIds(Long[] ids);

    /**
     * 删除老师信息信息
     * 
     * @param id 老师信息主键
     * @return 结果
     */
    public int deleteTeacherById(Long id);
}
