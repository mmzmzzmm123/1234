package com.hxzh.front.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hxzh.front.moudle.domain.Task;
import com.hxzh.front.moudle.vo.TaskInfoVO;


/**
* @author lenovo
* @description 针对表【task】的数据库操作Service
* @createDate 2023-12-07 02:55:47
*/
public interface TaskService extends IService<Task> {
    Task getTaskByTaskKeyAndTasksetId(String taskKey, String tasksetId);

    TaskInfoVO getTaskInfo();
}
