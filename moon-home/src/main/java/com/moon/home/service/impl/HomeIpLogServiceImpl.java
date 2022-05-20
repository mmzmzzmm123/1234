package com.moon.home.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moon.home.mapper.HomeIpLogMapper;
import com.moon.home.domain.HomeIpLog;
import com.moon.home.service.IHomeIpLogService;

/**
 * logService业务层处理
 * 
 * @author goo
 * @date 2022-05-19
 */
@Service
public class HomeIpLogServiceImpl implements IHomeIpLogService 
{
    @Autowired
    private HomeIpLogMapper homeIpLogMapper;

    /**
     * 查询log
     * 
     * @param id log主键
     * @return log
     */
    @Override
    public HomeIpLog selectHomeIpLogById(Long id)
    {
        return homeIpLogMapper.selectHomeIpLogById(id);
    }

    /**
     * 查询log列表
     * 
     * @param homeIpLog log
     * @return log
     */
    @Override
    public List<HomeIpLog> selectHomeIpLogList(HomeIpLog homeIpLog)
    {
        return homeIpLogMapper.selectHomeIpLogList(homeIpLog);
    }

    /**
     * 新增log
     * 
     * @param homeIpLog log
     * @return 结果
     */
    @Override
    public int insertHomeIpLog(HomeIpLog homeIpLog)
    {
        homeIpLog.setCreateTime(DateUtils.getNowDate());
        return homeIpLogMapper.insertHomeIpLog(homeIpLog);
    }

    /**
     * 修改log
     * 
     * @param homeIpLog log
     * @return 结果
     */
    @Override
    public int updateHomeIpLog(HomeIpLog homeIpLog)
    {
        homeIpLog.setUpdateTime(DateUtils.getNowDate());
        return homeIpLogMapper.updateHomeIpLog(homeIpLog);
    }

    /**
     * 批量删除log
     * 
     * @param ids 需要删除的log主键
     * @return 结果
     */
    @Override
    public int deleteHomeIpLogByIds(Long[] ids)
    {
        return homeIpLogMapper.deleteHomeIpLogByIds(ids);
    }

    /**
     * 删除log信息
     * 
     * @param id log主键
     * @return 结果
     */
    @Override
    public int deleteHomeIpLogById(Long id)
    {
        return homeIpLogMapper.deleteHomeIpLogById(id);
    }
}
