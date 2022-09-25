package com.ruoyi.system.service.impl;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.LxHsagMapper;
import com.ruoyi.system.domain.LxHsag;
import com.ruoyi.system.service.ILxHsagService;

/**
 * 信号与系统Service业务层处理
 * 
 * @author gongyu
 * @date 2022-06-19
 */
@Service
public class LxHsagServiceImpl implements ILxHsagService 
{
    @Autowired
    private LxHsagMapper lxHsagMapper;

    /**
     * 查询信号与系统
     * 
     * @param sj 信号与系统主键
     * @return 信号与系统
     */
    @Override
    public LxHsag selectLxHsagBySj(Date sj)
    {
        return lxHsagMapper.selectLxHsagBySj(sj);
    }

    /**
     * 查询信号与系统列表
     * 
     * @param lxHsag 信号与系统
     * @return 信号与系统
     */
    @Override
    public List<LxHsag> selectLxHsagList(LxHsag lxHsag)
    {
        return lxHsagMapper.selectLxHsagList(lxHsag);
    }

    /**
     * 新增信号与系统
     * 
     * @param lxHsag 信号与系统
     * @return 结果
     */
    @Override
    public int insertLxHsag(LxHsag lxHsag)
    {
        return lxHsagMapper.insertLxHsag(lxHsag);
    }

    /**
     * 修改信号与系统
     * 
     * @param lxHsag 信号与系统
     * @return 结果
     */
    @Override
    public int updateLxHsag(LxHsag lxHsag)
    {
        return lxHsagMapper.updateLxHsag(lxHsag);
    }

    /**
     * 批量删除信号与系统
     * 
     * @param sjs 需要删除的信号与系统主键
     * @return 结果
     */
    @Override
    public int deleteLxHsagBySjs(Date[] sjs)
    {
        return lxHsagMapper.deleteLxHsagBySjs(sjs);
    }

    /**
     * 删除信号与系统信息
     * 
     * @param sj 信号与系统主键
     * @return 结果
     */
    @Override
    public int deleteLxHsagBySj(Date sj)
    {
        return lxHsagMapper.deleteLxHsagBySj(sj);
    }
}
