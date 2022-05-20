package com.moon.home.service;

import java.util.List;
import com.moon.home.domain.HomeIpLog;

/**
 * logService接口
 * 
 * @author goo
 * @date 2022-05-19
 */
public interface IHomeIpLogService 
{
    /**
     * 查询log
     * 
     * @param id log主键
     * @return log
     */
    public HomeIpLog selectHomeIpLogById(Long id);

    /**
     * 查询log列表
     * 
     * @param homeIpLog log
     * @return log集合
     */
    public List<HomeIpLog> selectHomeIpLogList(HomeIpLog homeIpLog);

    /**
     * 新增log
     * 
     * @param homeIpLog log
     * @return 结果
     */
    public int insertHomeIpLog(HomeIpLog homeIpLog);

    /**
     * 修改log
     * 
     * @param homeIpLog log
     * @return 结果
     */
    public int updateHomeIpLog(HomeIpLog homeIpLog);

    /**
     * 批量删除log
     * 
     * @param ids 需要删除的log主键集合
     * @return 结果
     */
    public int deleteHomeIpLogByIds(Long[] ids);

    /**
     * 删除log信息
     * 
     * @param id log主键
     * @return 结果
     */
    public int deleteHomeIpLogById(Long id);
}
