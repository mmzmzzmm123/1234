package com.ruoyi.app.controller.course;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.constant.CourConstant;
import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.domain.CourSection;
import com.ruoyi.course.domain.CourUserCourseSection;
import com.ruoyi.course.service.ICourCourseService;
import com.ruoyi.course.service.ICourSectionService;
import com.ruoyi.course.service.ICourUserCourseSectionService;
import com.ruoyi.course.vo.CourseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/course/userSection")
@Api(value = "AppCourUserSectionController" ,tags = {"用户课程章节API"})
public class AppCourUserSectionController extends BaseController {

    @Autowired
    private ICourUserCourseSectionService courUserCourseSectionService;

    @Autowired
    private ICourCourseService courCourseService;

    @Autowired
    private ICourSectionService courSectionService;

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
     * 新增或更新用户-课程-章节关系
     */
    @PostMapping("/saveUserSectionInfo")
    @ApiOperation("记录课程章节完成情况，新增或更新用户-课程-章节关系")
    public AjaxResult saveUserSectionInfo(@RequestBody Map<String, Object> map)
    {
        CourUserCourseSection userCourseSection = new CourUserCourseSection();

        Integer userId = Integer.parseInt(map.get("userId").toString());
        Integer courseId = Integer.parseInt(map.get("courseId").toString());
        Integer sectionId = Integer.parseInt(map.get("sectionId").toString());
        Integer endTime = Integer.parseInt(map.get("endTime").toString());

        userCourseSection.setUserId(userId);
        userCourseSection.setCourseId(courseId);
        userCourseSection.setSectionId(sectionId);
        userCourseSection.setEndTime(endTime);

        if (map.get("finishStatus") != null) {
            Integer finishStatus = Integer.parseInt(map.get("finishStatus").toString());
            userCourseSection.setFinishStatus(finishStatus);
        } else {
            userCourseSection.setFinishStatus(CourConstant.SECTION_UNFINISHED);
        }
        // 根据用户、课程、章节查询是否已有学习完成记录
        List<CourUserCourseSection> userCourseSectionList =  courUserCourseSectionService.selectCourUserCourseSectionList(userCourseSection);
        if (userCourseSectionList.size() == 0) {
            // 新增
            return AjaxResult.success(courUserCourseSectionService.insertCourUserCourseSection(userCourseSection));
        } else if (userCourseSectionList.size() == 1){
            // 更新
            userCourseSection.setId(userCourseSectionList.get(0).getId());
            return AjaxResult.success(courUserCourseSectionService.recordEndTime(userCourseSection));
        }
        return AjaxResult.error("记录课程章节异常");
    }
}
