package com.ruoyi.app.controller.course;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.course.constant.CourConstant;
import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.domain.CourSection;
import com.ruoyi.course.domain.CourUserCourseSection;
import com.ruoyi.course.service.ICourCourseService;
import com.ruoyi.course.service.ICourOrderService;
import com.ruoyi.course.service.ICourSectionService;
import com.ruoyi.course.service.ICourUserCourseSectionService;
import com.ruoyi.course.vo.CourseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 课程Controller
 *
 * @author luzijun
 * @date 2023-03-15
 */
@RestController
@RequestMapping("/app/course")
@Api(value = "AppCourCourseController" ,tags = {"课程API"})
public class AppCourCourseController extends BaseController
{
    @Autowired
    private ICourCourseService courCourseService;

    @Autowired
    private ICourUserCourseSectionService courUserCourseSectionService;

    @Autowired
    private ICourOrderService courOrderService;

    @Autowired
    private ICourSectionService courSectionService;



    /**
     * 查询课程列表
     */
//    @PreAuthorize("@ss.hasPermi('course:course:list')")
    @PostMapping("/list")
    @ApiOperation("查询课程列表")
    public TableDataInfo list(@RequestBody CourCourse courCourse)
    {
        startPage();
        List<CourCourse> list = courCourseService.selectCourCourseList(courCourse);
        return getDataTable(list);
    }

    /**
     * 查询用户的课程列表
     */
//    @PreAuthorize("@ss.hasPermi('course:userSection:list')")
    @PostMapping("/getCourseListByUserId")
    @ApiOperation("根据用户ID查询课程列表")
    public TableDataInfo getCourseListByUserId(@RequestBody Map<String, Object> body)
    {
        Integer userId = Integer.parseInt(body.get("userId").toString());
        startPage();
        List<CourCourse> list = courCourseService.getCourseListByUserId(userId);
        List<CourseVO> courseVOList = new ArrayList<>();
        List<CourseVO> courseFinishList = new ArrayList<>();
        list.forEach(courCourse -> {
            CourseVO courseVO = new CourseVO();

            // 计算课程完成情况
            boolean hasUnFinished = courCourseService.calCourCourseList(courCourse.getCourseId());
            BeanUtils.copyProperties(courCourse, courseVO);
            courseVO.setFinishStatus(hasUnFinished ? 0 : 1);

            // 增加章节列表
            CourSection courSection = CourSection.builder()
                    .courseId(courCourse.getCourseId())
                    .build();
            List<CourSection> sectionList = courSectionService.selectCourSectionList(courSection);
            courseVO.setSectionList(sectionList);

            // 计算课程学习时长
            Integer studyDuration = courCourseService.calCourCourseStudyDuration(userId, courCourse.getCourseId());
            courseVO.setStudyDuration(studyDuration);
            if(hasUnFinished){
                        courseVOList.add(courseVO);
                        courseVOList.stream().sorted(
                        (a,b)->{ return a.getStudyDuration()-b.getStudyDuration();}
                        ).forEach(s->System.out.println(s));
                    }
            else {courseFinishList.add(courseVO);}

        });
        courseVOList.addAll(courseFinishList);
        return getDataTable(courseVOList);
    }

    /**
     * 查询课程信息
     */
//    @PreAuthorize("@ss.hasPermi('course:course:query')")
    @PostMapping(value = "/getInfo")
    @ApiOperation("查询课程信息")
    public AjaxResult getInfo(@RequestParam(value = "id") Integer id)
    {
        return AjaxResult.success(courCourseService.selectCourCourseById(id));
    }

    /**
     * 根据课程主键查询课程详情
     */
//    @PreAuthorize("@ss.hasPermi('course:course:query')")
    @PostMapping(value = "/detail")
    @ApiOperation("查询课程详情")
    public AjaxResult detail(@RequestBody Map<String, String> map)
    {
        Integer userId = Integer.parseInt(map.get("userId")); // 用户主键
        Integer courseId = Integer.parseInt(map.get("courseId")); // 课程主键
        CourCourse course = courCourseService.selectCourCourseById(courseId);
        if (course == null) {
            return AjaxResult.error("查询课程详情失败");
        }

        // 查询课程的学习人数
        CourUserCourseSection courUserCourseSection = new CourUserCourseSection();
        courUserCourseSection.setCourseId(courseId);
        List<CourUserCourseSection> courUserCourseSectionList = courUserCourseSectionService.selectCourUserCourseSectionList(courUserCourseSection);

        CourseVO courseVO = new CourseVO();
        BeanUtils.copyProperties(course, courseVO);
        courseVO.setStudyNum(courUserCourseSectionList.size());

        // 增加章节列表
        CourSection courSection = CourSection.builder()
                .courseId(courseId)
                .build();
        List<CourSection> list = courSectionService.selectCourSectionList(courSection);
        courseVO.setSectionList(list);

        // 查询用户有没有购买该订单
        if (userId == 0) { // 没有给出用户标识
            return AjaxResult.success(courseVO);
        }
        List<CourOrder> courOrderList = courOrderService.selectCourOrderByUser(userId, courseId);
        courseVO.setIsBuy(courOrderList.size() > 0 ? CourConstant.COURSE_BUY : CourConstant.COURSE_NOT_BUY);

        return AjaxResult.success(courseVO);
    }

}
