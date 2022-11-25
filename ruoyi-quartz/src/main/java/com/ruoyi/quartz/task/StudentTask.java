package com.ruoyi.quartz.task;

import com.ruoyi.student.service.IStuInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度
 *
 * @author ruoyi
 */
@Component("studentTask")
public class StudentTask {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IStuInfoService stuInfoService;

    public void changeStudentTag() {
        logger.info("启动定时任务 studentTask");
        int inSchool = stuInfoService.changeTagReturn2InSchool();
        logger.info("变更为[在校]人数: {}", inSchool);
        int outSchool = stuInfoService.changeTagLeave2Out();
        logger.info("变更为[不在校]人数: {}", outSchool);
        logger.info("完成定时任务 studentTask");
    }
}
