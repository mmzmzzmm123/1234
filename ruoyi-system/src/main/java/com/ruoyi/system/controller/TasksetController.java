package com.ruoyi.system.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.Taskset;
import com.ruoyi.system.service.TasksetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务集增删改查
 *
 * @author lyy
 */
@RestController
@RequestMapping("/taskset")
public class TasksetController {
    @Autowired
    TasksetService tasksetService;

    private static final Logger logger = LoggerFactory.getLogger(TasksetController.class);
    /**
     * 批量生成
     */
    @PostMapping("/addbatch")
    public AjaxResult addBatch(@RequestParam("file") MultipartFile file, String splitcol, String keyname, String code, int dept_id) {
        try {
            int res = tasksetService.AddTasksetByExcel(file, splitcol, dept_id);
            if (res != 1) {
                logger.error("批量创建 Taskset 失败");
                return AjaxResult.error("批量创建 Taskset 失败");
            }
            int res2 = tasksetService.CreateTaskbyExcel(file, splitcol, keyname, code);
            if (res2 != 1) {
                logger.error("批量创建 Task 失败");
                return AjaxResult.error("批量创建 Task 失败");
            }
            logger.info("批量创建 Taskset 和 Task 成功");
            return AjaxResult.success("批量创建成功");
        } catch (Exception e) {
            logger.error("批量创建过程中发生异常", e);
            return AjaxResult.error("批量创建过程中发生异常: " + e.getMessage());
        }
    }


    /**
     * 展示任务集列表
     */
    @GetMapping("/list")
    public AjaxResult list(){
        List<Taskset> tasksetList = new ArrayList<>();
        tasksetList = tasksetService.selectAllTaskset();
        return AjaxResult.success(tasksetList);
    }

    /**
     * 添加任务集
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody Taskset taskset){
        int res = tasksetService.insertTaskset(taskset);
        if(res == 1){
            return AjaxResult.success("成功新建任务集");
        }
        return AjaxResult.error("新建任务集合失败");
    }

    /**
     * 删除任务集
     */
    @DeleteMapping("/delete")
    public AjaxResult delete(int id){
        int res = tasksetService.deleteTasksetById(id);
        if(res == 1){
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }

    /**
     * 修改任务集
     */
    @PutMapping("/update")
    public AjaxResult update(int id, @RequestBody Taskset taskset){
        int res = tasksetService.updateTaskset(id, taskset);
        if(res == 1){
            return AjaxResult.success("更新成功");
        }
        return AjaxResult.error("更新失败");
    }

}
