package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    /**
     * 新建子任务
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Task task){
        return taskService.addTask(task);
    }


    /**
     * 删除子任务
     */
    @DeleteMapping("/delete")
    public AjaxResult delete(Long id){
        return taskService.deleteById(id);
    }

    /**
     * 修改子任务
     */
    @PutMapping("/update")
    public AjaxResult update(@RequestBody Task task){
        return taskService.update(task);
    }

    /**
     * 展示所有任务
     */
    @GetMapping("/list")
    public AjaxResult list(){
        return AjaxResult.success(taskService.allList());
    }

    /**
     * 检索单个任务
     */
    @GetMapping("/check")
    public AjaxResult check(Long id){
        return AjaxResult.success(taskService.checkTask(id));
    }
}
