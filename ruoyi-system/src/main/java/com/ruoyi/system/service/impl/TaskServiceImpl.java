package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.service.TaskService;
import com.ruoyi.system.mapper.TaskMapper;
import org.springframework.stereotype.Service;

/**
* @author JingZhang
* @description 针对表【t_task(任务表)】的数据库操作Service实现
* @createDate 2023-12-28 15:51:22
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService{

}




