package com.moon.home.service;

import java.util.List;
import com.moon.home.domain.HomeIpLog;

/**
 * ip日志Service接口
 * 
 * @author fo
 * @date 2022-05-26
 */
public interface IHomeIpLogService 
{
    /**
     * 查询ip日志
     * 
     * @param id ip日志主键
     * @return ip日志
     */
    public HomeIpLog selectHomeIpLogById(Long id);

    /**
     * 查询ip日志列表
     * 
     * @param homeIpLog ip日志
     * @return ip日志集合
     */
    public List<HomeIpLog> selectHomeIpLogList(HomeIpLog homeIpLog);

    /**
     * 新增ip日志
     * 
     * @param homeIpLog ip日志
     * @return 结果
     */
    public int insertHomeIpLog(HomeIpLog homeIpLog);

    /**
     * 修改ip日志
     * 
     * @param homeIpLog ip日志
     * @return 结果
     */
    public int updateHomeIpLog(HomeIpLog homeIpLog);

    /**
     * 批量删除ip日志
     * 
     * @param ids 需要删除的ip日志主键集合
     * @return 结果
     */
    public int deleteHomeIpLogByIds(Long[] ids);

    /**
     * 删除ip日志信息
     * 
     * @param id ip日志主键
     * @return 结果
     */
    public int deleteHomeIpLogById(Long id);
}
