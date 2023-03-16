package com.ruoyi.app.controller.course;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.domain.CourUserCourseSection;
import com.ruoyi.course.service.ICourCourseService;
import com.ruoyi.course.service.ICourUserCourseSectionService;
import com.ruoyi.course.vo.CourseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/app/course/userSection")
@Api(value = "AppCourUserSectionController" ,tags = {"用户课程章节API"})
public class AppCourUserSectionController extends BaseController {

    @Autowired
    private ICourUserCourseSectionService courUserCourseSectionService;

    @Autowired
    private ICourCourseService courCourseService;

    /**
     * 查询用户的课程列表
     */
//    @PreAuthorize("@ss.hasPermi('course:userSection:list')")
    @PostMapping("/list")
    @ApiOperation("查询课程章节列表")
    public TableDataInfo list(@RequestBody CourUserCourseSection courUserCourseSection)
    {
        startPage();
        List<CourUserCourseSection> list = courUserCourseSectionService.selectCourUserCourseSectionList(courUserCourseSection);
        return getDataTable(list);
    }

    /**
     * 查询用户的课程列表
     */
//    @PreAuthorize("@ss.hasPermi('course:userSection:list')")
    @PostMapping("/getCourseListByUserId")
    @ApiOperation("根据用户编号查询课程列表")
    public TableDataInfo getCourseListByUserId(CourUserCourseSection courUserCourseSection)
    {
        startPage();
        List<CourCourse> list = courUserCourseSectionService.getCourseListByUserId(courUserCourseSection);

        List<CourseVO> courseVOList = new ArrayList<>();
        list.forEach(courCourse -> {
            boolean hasUnFinished = courCourseService.calCourCourseList(courCourse.getCourseId());

            CourseVO courseVO = new CourseVO();
            courseVO.setId(courCourse.getId());
            courseVO.setCourseId(courCourse.getCourseId());
            courseVO.setName(courCourse.getName());
            courseVO.setType(courCourse.getType());
            courseVO.setAuthor(courCourse.getAuthor());
            courseVO.setUrl(courCourse.getUrl());
            courseVO.setPrice(courCourse.getPrice());
            courseVO.setDetail(courCourse.getDetail());
            courseVO.setFinishStatus(hasUnFinished ? 0 : 1);

            courseVOList.add(courseVO);
        });
        return getDataTable(courseVOList);
    }

    /**
     * 新增或更新用户-课程-章节关系
     */
    @PostMapping("/saveUserSectionInfo")
    @ApiOperation("记录课程章节完成情况，新增或更新用户-课程-章节关系")
    public AjaxResult saveUserSectionInfo(@RequestBody CourUserCourseSection courUserCourseSection)
    {
        CourUserCourseSection userCourseSection = new CourUserCourseSection();
        userCourseSection.setUserId(courUserCourseSection.getUserId());
        userCourseSection.setCourseId(courUserCourseSection.getCourseId());
        userCourseSection.setSectionId(courUserCourseSection.getSectionId());
        // 根据用户、课程、章节查询是否已有学习完成记录
        if (this.list(userCourseSection).getTotal() == 0) {
            // 新增
            return AjaxResult.success(courUserCourseSectionService.insertCourUserCourseSection(courUserCourseSection));
        } else {
            // 更新
            return AjaxResult.success(courUserCourseSectionService.updateCourUserCourseSection(courUserCourseSection));
        }
    }
}
