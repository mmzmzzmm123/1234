package com.hxzh.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hxzh.front.common.enums.TaskStatus;
import com.hxzh.front.common.exception.task.TaskNotExistsException;
import com.hxzh.front.common.util.TaskUtils;
import com.hxzh.front.moudle.domain.Task;
import com.hxzh.front.mapper.TaskMapper;
import com.hxzh.front.moudle.vo.TaskInfoVO;
import com.hxzh.front.service.TaskService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author JasonYou
* @description 针对表【task】的数据库操作Service实现
* @createDate 2023-12-07 02:55:47
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Override
    public Task getTaskByTaskKeyAndTasksetId(String taskKey, String tasksetId) {
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        Map<String, String> param = new HashMap<String,String>(){{
            put("task_key", taskKey);
            put("taskset_id", tasksetId);
        }};
        queryWrapper.allEq(param);
        return this.getOne(queryWrapper);
    }

    @Override
    public TaskInfoVO getTaskInfo() {
            Long id= TaskUtils.getTaskId();
            //通过id获取task信息
            Task task = this.getById(id);
            if(ObjectUtils.isEmpty(task)){
                throw new TaskNotExistsException();
            }
            //封装taskInfoVO
            String taskKey = task.getTaskKey();
            String taskStatus = TaskStatus.getNameByValue(task.getTaskStatus());
            Object otherInfo = task.getOtherInfo();

            TaskInfoVO taskInfoVO = new TaskInfoVO();
            taskInfoVO.setTaskKey(taskKey);
            taskInfoVO.setTaskStatus(taskStatus);
            taskInfoVO.setOtherInfo(otherInfo);
            return taskInfoVO;

    }
}




