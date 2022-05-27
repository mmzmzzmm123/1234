package com.moon.home.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moon.home.mapper.HomeIpLogMapper;
import com.moon.home.domain.HomeIpLog;
import com.moon.home.service.IHomeIpLogService;

/**
 * ip日志Service业务层处理
 * 
 * @author fo
 * @date 2022-05-26
 */
@Service
public class HomeIpLogServiceImpl implements IHomeIpLogService 
{
    @Autowired
    private HomeIpLogMapper homeIpLogMapper;

    /**
     * 查询ip日志
     * 
     * @param id ip日志主键
     * @return ip日志
     */
    @Override
    public HomeIpLog selectHomeIpLogById(Long id)
    {
        return homeIpLogMapper.selectHomeIpLogById(id);
    }

    /**
     * 查询ip日志列表
     * 
     * @param homeIpLog ip日志
     * @return ip日志
     */
    @Override
    public List<HomeIpLog> selectHomeIpLogList(HomeIpLog homeIpLog)
    {
        return homeIpLogMapper.selectHomeIpLogList(homeIpLog);
    }

    /**
     * 新增ip日志
     * 
     * @param homeIpLog ip日志
     * @return 结果
     */
    @Override
    public int insertHomeIpLog(HomeIpLog homeIpLog)
    {
        homeIpLog.setCreateTime(DateUtils.getNowDate());
        return homeIpLogMapper.insertHomeIpLog(homeIpLog);
    }

    /**
     * 修改ip日志
     * 
     * @param homeIpLog ip日志
     * @return 结果
     */
    @Override
    public int updateHomeIpLog(HomeIpLog homeIpLog)
    {
        homeIpLog.setUpdateTime(DateUtils.getNowDate());
        return homeIpLogMapper.updateHomeIpLog(homeIpLog);
    }

    /**
     * 批量删除ip日志
     * 
     * @param ids 需要删除的ip日志主键
     * @return 结果
     */
    @Override
    public int deleteHomeIpLogByIds(Long[] ids)
    {
        return homeIpLogMapper.deleteHomeIpLogByIds(ids);
    }

    /**
     * 删除ip日志信息
     * 
     * @param id ip日志主键
     * @return 结果
     */
    @Override
    public int deleteHomeIpLogById(Long id)
    {
        return homeIpLogMapper.deleteHomeIpLogById(id);
    }
}
