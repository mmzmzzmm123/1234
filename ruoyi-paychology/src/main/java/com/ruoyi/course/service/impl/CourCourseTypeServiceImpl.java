package com.ruoyi.course.service.impl;

import java.util.List;

import com.ruoyi.course.domain.CourCourseType;
import com.ruoyi.course.mapper.CourCourseTypeMapper;
import com.ruoyi.course.service.ICourCourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 课程类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@Service
public class CourCourseTypeServiceImpl implements ICourCourseTypeService
{
    @Autowired
    private CourCourseTypeMapper courCourseTypeMapper;

    /**
     * 查询课程类型
     * 
     * @param typeId 课程类型主键
     * @return 课程类型
     */
    @Override
    public CourCourseType selectCourCourseTypeByTypeId(Long typeId)
    {
        return courCourseTypeMapper.selectCourCourseTypeByTypeId(typeId);
    }

    /**
     * 查询课程类型列表
     * 
     * @param courCourseType 课程类型
     * @return 课程类型
     */
    @Override
    public List<CourCourseType> selectCourCourseTypeList(CourCourseType courCourseType)
    {
        return courCourseTypeMapper.selectCourCourseTypeList(courCourseType);
    }

    /**
     * 新增课程类型
     * 
     * @param courCourseType 课程类型
     * @return 结果
     */
    @Override
    public int insertCourCourseType(CourCourseType courCourseType)
    {
        return courCourseTypeMapper.insertCourCourseType(courCourseType);
    }

    /**
     * 修改课程类型
     * 
     * @param courCourseType 课程类型
     * @return 结果
     */
    @Override
    public int updateCourCourseType(CourCourseType courCourseType)
    {
        return courCourseTypeMapper.updateCourCourseType(courCourseType);
    }

    /**
     * 批量删除课程类型
     * 
     * @param typeIds 需要删除的课程类型主键
     * @return 结果
     */
    @Override
    public int deleteCourCourseTypeByTypeIds(Long[] typeIds)
    {
        return courCourseTypeMapper.deleteCourCourseTypeByTypeIds(typeIds);
    }

    /**
     * 删除课程类型信息
     * 
     * @param typeId 课程类型主键
     * @return 结果
     */
    @Override
    public int deleteCourCourseTypeByTypeId(Long typeId)
    {
        return courCourseTypeMapper.deleteCourCourseTypeByTypeId(typeId);
    }
}
