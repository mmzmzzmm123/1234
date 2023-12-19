package com.ruoyi.system.service;

import com.ruoyi.system.domain.Taskset;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
* @author lenovo
* @description 针对表【taskset】的数据库操作Service
* @createDate 2023-12-07 02:55:47
*/
public interface TasksetService extends IService<Taskset> {
    /**
     * 根据excel里的指定字段创建任务集合
     * @param file ,col_name, keyname, code
     */
    int AddTasksetByExcel(MultipartFile file, String splitcol, int dept_id) throws IOException;

    /**
     * 根据用户创建的任务集,划分任务
     * @param file, col_name, keyname, code
     */
    int CreateTaskbyExcel(MultipartFile file, String splitcol, String keyname, String code) throws IOException;

    /**
     * 根据ID获取任务集合
     * @param id
     * @return Taskset
     */
    Taskset selectTasksetById(int id);

    /**
     * 根据字段名获得这个字段在excel里的index
     * @param file， name
     * @return int
     */
    int getIndexFromExcel(MultipartFile file, String name) throws IOException;

    /**
     * 检查目标列是否有空行
     * @param file， col
     * @return int
     */
    int checkBlank(MultipartFile file, int Col) throws IOException;

    /**
     * 获取任务集合列表
     * @return List<Taskset>
     */
    List<Taskset> selectAllTaskset();

    /**
     * 新增任务集合
     * @param taskset
     * @return 结果
     */
    int insertTaskset(Taskset taskset);

    /**
     * 更新任务集合信息
     * @return 结果
     */
    int updateTaskset(int id, Taskset taskset);

    /**
     * 删除任务集
     * @param id
     * @return 结果
     */
    int deleteTasksetById(int id);
}
