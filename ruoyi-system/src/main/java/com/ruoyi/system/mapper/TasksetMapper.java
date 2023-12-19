package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Taskset;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author lenovo
* @description 针对表【taskset】的数据库操作Mapper
* @createDate 2023-12-07 02:55:47
* @Entity com.ruoyi.system.domain.Taskset
*/
public interface TasksetMapper extends BaseMapper<Taskset> {
    Taskset selectTasksetById(int id);
    List<Taskset> selectAllTaskset();
    int selectTasksetIdByName(String name);
    int insertTaskset(Taskset taskset);
    int updateTasksetById(int id, Taskset taskset);
    int deleteTasksetById(int id);
}




