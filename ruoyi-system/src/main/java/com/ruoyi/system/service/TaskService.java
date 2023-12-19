package com.ruoyi.system.service;

import com.ruoyi.system.domain.Task;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author lenovo
* @description 针对表【task】的数据库操作Service
* @createDate 2023-12-07 02:55:47
*/
public interface TaskService extends IService<Task> {
    int addTask(Task task);
    int deleteById(Long id);
    int updateById(Long id, Task task);
    List<Task> allList();
    Task checkTask(Long id);
}
