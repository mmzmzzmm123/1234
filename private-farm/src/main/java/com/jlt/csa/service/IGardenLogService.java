package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.GardenLog;

/**
 * 菜地日志Service接口
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public interface IGardenLogService 
{
    /**
     * 查询菜地日志
     * 
     * @param id 菜地日志主键
     * @return 菜地日志
     */
    public GardenLog selectGardenLogById(Long id);

    /**
     * 查询菜地日志列表
     * 
     * @param gardenLog 菜地日志
     * @return 菜地日志集合
     */
    public List<GardenLog> selectGardenLogList(GardenLog gardenLog);

    /**
     * 新增菜地日志
     * 
     * @param gardenLog 菜地日志
     * @return 结果
     */
    public int insertGardenLog(GardenLog gardenLog);

    /**
     * 修改菜地日志
     * 
     * @param gardenLog 菜地日志
     * @return 结果
     */
    public int updateGardenLog(GardenLog gardenLog);

    /**
     * 批量删除菜地日志
     * 
     * @param ids 需要删除的菜地日志主键集合
     * @return 结果
     */
    public int deleteGardenLogByIds(Long[] ids);

    /**
     * 删除菜地日志信息
     * 
     * @param id 菜地日志主键
     * @return 结果
     */
    public int deleteGardenLogById(Long id);
}
