package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.taskset.InputException;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.mapper.TaskMapper;
import com.ruoyi.system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public AjaxResult addTask(Task task) {
        if (task.getTaskId() == null) {
            throw new InputException("INPUT_ERROR", "无法添加task，因为缺少主键id");
        }
        int affectedRows = taskMapper.insertTask(task);
        if (affectedRows != 0 && taskMapper.selectTaskById(task.getTaskId()) != null) {
            return AjaxResult.success("成功添加任务");
        } else {
            throw new DataAccessException("更新任务集合失败") {
            };
        }
    }

    @Override
    public AjaxResult deleteById(Long id) {
        if (taskMapper.selectTaskById(id)==null){
            throw new InputException("INPUT_ERROR","找不到要删除的任务");
        }
        int affectedRows = taskMapper.deleteById(id);
        if(affectedRows!=0){
            return AjaxResult.success("删除任务成功");
        }else {
            throw new DataAccessException("更新任务集合失败") {
            };
        }
    }

    @Override
    public AjaxResult update(Task task) {
        if (taskMapper.selectTaskById(task.getTaskId())==null){
            throw new InputException("INPUT_ERROR","找不到要修改的任务");
        }
        int affectedRows = taskMapper.updateTask(task);
        if(affectedRows!=0){
            return AjaxResult.success("更新任务成功");
        }else {
            throw new DataAccessException("更新任务集合失败") {
            };
        }
    }

    @Override
    public AjaxResult allList() {
        List<Task> alltask = new ArrayList<>();
        alltask = taskMapper.allTaskList();
        if (alltask.isEmpty()){
            return AjaxResult.success("暂无任务");
        }
        return AjaxResult.success(alltask);
    }

    @Override
    public AjaxResult checkTask(Long id) {
        if (taskMapper.selectTaskById(id)==null){
            throw new InputException("INPUT_ERROR","找不到要查看的任务");
        }
        return AjaxResult.success(taskMapper.selectTaskById(id));
    }
}




