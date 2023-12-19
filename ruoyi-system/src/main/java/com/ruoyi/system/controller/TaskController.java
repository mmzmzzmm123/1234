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
        int res = taskService.addTask(task);
        if(res == 1){
            return AjaxResult.success("新建子任务成功");
        }
        return AjaxResult.error("新建子任务失败");
    }


    /**
     * 删除子任务
     */
    @DeleteMapping("/delete")
    public AjaxResult delete(Long id){
        int res = taskService.deleteById(id);
        if(res == 1){
            return AjaxResult.success("删除子任务成功");
        }
        return AjaxResult.error("删除子任务失败");
    }

    /**
     * 修改子任务
     */
    @PutMapping("/update")
    public AjaxResult update(Long id, @RequestBody Task task){
        int res = taskService.updateById(id, task);
        if (res == 1){
            return AjaxResult.success("成功修改子任务");
        }
        return AjaxResult.error("修改子任务失败");
    }

    /**
     * 展示任务集里的所有任务
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
