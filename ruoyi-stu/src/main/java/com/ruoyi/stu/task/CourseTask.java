package com.ruoyi.stu.task;

import com.ruoyi.stu.domain.Semester;
import com.ruoyi.stu.service.ICoursePlanService;
import com.ruoyi.stu.service.ISemesterService;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("courseTask")
public class CourseTask {
        @Autowired
        private ISemesterService semesterService;

        @Autowired
        private ICoursePlanService coursePlanService;
        /**
         * 任务调度
         */
        private static Logger logger = LoggerFactory.getLogger(CourseTask.class);

        public void courseStatusUpdate(){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int id = 0;
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int semesterYear = month > 6 ? year : year-1;
            Semester semester = new Semester();
            semester.setSemesterName(semesterYear+"");
            List<Semester> list = semesterService.selectSemester(semester).stream().sorted(Comparator.comparing(Semester::getSemesterId)).collect(Collectors.toList());
            if(list.size()>0){
                semester = month > 6 ? list.get(0) : list.get(1);
                id = semester.getSemesterId();
            }else{
                String semesterName = semesterYear+"学年第一学期";
                id = semesterService.addSemester(new Semester(null,semesterName));
                String semesterName2 = semesterYear+"学年第二学期";
                semesterService.addSemester(new Semester(null,semesterName2));
            }
            coursePlanService.updateCourseStatusBySemesterId(id);
        }


}
