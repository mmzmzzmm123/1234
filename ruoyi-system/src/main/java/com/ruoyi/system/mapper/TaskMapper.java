package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author lenovo
* @description 针对表【task】的数据库操作Mapper
* @createDate 2023-12-07 02:55:47
* @Entity com.ruoyi.system.moudle.Task
*/
@Mapper
public interface TaskMapper extends BaseMapper<Task> {
    Task selectTaskById(long id);
    int insertTask(Task task);
    int deleteTask(Long id);
    int updateTask(Task task);
    List<Task> allTaskList();
}




