package com.ruoyi.course.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.domain.CourSection;
import com.ruoyi.course.domain.CourUserCourseSection;
import com.ruoyi.course.mapper.CourCourseMapper;
import com.ruoyi.course.service.ICourCourseService;
import com.ruoyi.course.service.ICourSectionService;
import com.ruoyi.course.service.ICourUserCourseSectionService;
import com.ruoyi.course.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 课程Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@Service
public class CourCourseServiceImpl implements ICourCourseService
{
    @Autowired
    private CourCourseMapper courCourseMapper;

    @Autowired
    private ICourSectionService courSectionService;

    @Autowired
    private ICourUserCourseSectionService courUserCourseSectionService;

    /**
     * 查询课程
     * 
     * @param id 课程主键
     * @return 课程
     */
    @Override
    public CourCourse selectCourCourseById(Long id)
    {
        return courCourseMapper.selectCourCourseById(id);
    }

    /**
     * 课程分类
     *
     * @param typeId 课程主键
     * @return 课程
     */
    @Override
    public CourCourse selectCourCourseByType(Long typeId)
    {
        return courCourseMapper.selectCourCourseByType(typeId);
    }
    /**
     * 查询课程列表
     * 
     * @param courCourse 课程
     * @return 课程
     */
    @Override
    public List<CourCourse> selectCourCourseList(CourCourse courCourse)
    {
        return courCourseMapper.selectCourCourseList(courCourse);
    }

    /**
     * 新增课程
     * 
     * @param courCourse 课程
     * @return 结果
     */
    @Override
    public int insertCourCourse(CourCourse courCourse)
    {
        courCourse.setCreateTime(DateUtils.getNowDate());
        return courCourseMapper.insertCourCourse(courCourse);
    }

    /**
     * 修改课程
     * 
     * @param courCourse 课程
     * @return 结果
     */
    @Override
    public int updateCourCourse(CourCourse courCourse)
    {
        courCourse.setUpdateTime(DateUtils.getNowDate());
        return courCourseMapper.updateCourCourse(courCourse);
    }

    /**
     * 批量删除课程
     * 
     * @param ids 需要删除的课程主键
     * @return 结果
     */
    @Override
    public int deleteCourCourseByIds(Long[] ids)
    {
        return courCourseMapper.deleteCourCourseByIds(ids);
    }

    /**
     * 删除课程信息
     * 
     * @param id 课程主键
     * @return 结果
     */
    @Override
    public int deleteCourCourseById(Long id)
    {
        return courCourseMapper.deleteCourCourseById(id);
    }

    /**
     * 根据课程的章节判断课程是否完成
     *
     * @param courseId 课程编号
     * @return 课程信息
     */
    public boolean calCourCourseList(String courseId) {
        CourUserCourseSection courUserCourseSection = new CourUserCourseSection();
        courUserCourseSection.setCourseId(courseId);
        List<CourUserCourseSection> courUserCourseSectionList =
                courUserCourseSectionService.selectCourUserCourseSectionList(courUserCourseSection);

        return courUserCourseSectionList.stream().anyMatch(item -> item.getFinishStatus() == 0);
    }

    /**
     * 查询课程是否支付
     *
     * @param courseId 课程编号
     * @return 课程支付数量
     */
    public int getPaidCourseCount(String courseId) {
        return courCourseMapper.getPaidCourseCount(courseId);
    }
}
