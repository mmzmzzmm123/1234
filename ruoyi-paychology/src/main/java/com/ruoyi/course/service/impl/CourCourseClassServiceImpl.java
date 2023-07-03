package com.ruoyi.course.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.course.mapper.CourCourseClassMapper;
import com.ruoyi.course.domain.CourCourseClass;
import com.ruoyi.course.service.ICourCourseClassService;

/**
 * 类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-07
 */
@Service
public class CourCourseClassServiceImpl implements ICourCourseClassService 
{
    @Autowired
    private CourCourseClassMapper courCourseClassMapper;

    /**
     * 查询类型
     * 
     * @param id 类型主键
     * @return 类型
     */
    @Override
    public CourCourseClass selectCourCourseClassById(Integer id)
    {
        return courCourseClassMapper.selectCourCourseClassById(id);
    }

    /**
     * 查询类型列表
     * 
     * @param courCourseClass 类型
     * @return 类型
     */
    @Override
    public List<CourCourseClass> selectCourCourseClassList(CourCourseClass courCourseClass)
    {
        return courCourseClassMapper.selectCourCourseClassList(courCourseClass);
    }

    /**
     * 新增类型
     * 
     * @param courCourseClass 类型
     * @return 结果
     */
    @Override
    public int insertCourCourseClass(CourCourseClass courCourseClass)
    {
        return courCourseClassMapper.insertCourCourseClass(courCourseClass);
    }

    /**
     * 修改类型
     * 
     * @param courCourseClass 类型
     * @return 结果
     */
    @Override
    public int updateCourCourseClass(CourCourseClass courCourseClass)
    {
        return courCourseClassMapper.updateCourCourseClass(courCourseClass);
    }

    /**
     * 批量删除类型
     * 
     * @param ids 需要删除的类型主键
     * @return 结果
     */
    @Override
    public int deleteCourCourseClassByIds(Integer[] ids)
    {
        return courCourseClassMapper.deleteCourCourseClassByIds(ids);
    }

    /**
     * 删除类型信息
     * 
     * @param id 类型主键
     * @return 结果
     */
    @Override
    public int deleteCourCourseClassById(Integer id)
    {
        return courCourseClassMapper.deleteCourCourseClassById(id);
    }
}