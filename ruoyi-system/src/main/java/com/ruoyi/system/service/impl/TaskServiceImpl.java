package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.mapper.TaskMapper;
import com.ruoyi.system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author lenovo
* @description 针对表【task】的数据库操作Service实现
* @createDate 2023-12-07 02:55:47
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService{
    @Autowired
    TaskMapper taskMapper;
    @Override
    public int addTask(Task task) {
        int affectedRows = taskMapper.insertTask(task);
        if (affectedRows!=0 && taskMapper.selectTaskById(task.getTaskId())!=null){
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        int affectedRows = taskMapper.deleteById(id);
        if(affectedRows!=0){
            return 1;
        }
        return 0;
    }

    @Override
    public int updateById(Long id, Task task) {
        int affectedRows = taskMapper.updateTask(id, task);
        if(affectedRows!=0){
            return 1;
        }
        return 0;
    }

    @Override
    public List<Task> allList() {
        List<Task> alltask = new ArrayList<>();
        alltask = taskMapper.allTaskList();
        return alltask;
    }

    @Override
    public Task checkTask(Long id) {
        return taskMapper.selectTaskById(id);
    }


}




