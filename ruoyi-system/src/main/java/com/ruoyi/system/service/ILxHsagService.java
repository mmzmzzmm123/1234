package com.ruoyi.system.service;

import java.sql.Date;
import java.util.List;
import com.ruoyi.system.domain.LxHsag;

/**
 * 信号与系统Service接口
 * 
 * @author gongyu
 * @date 2022-06-19
 */
public interface ILxHsagService 
{
    /**
     * 查询信号与系统
     * 
     * @param sj 信号与系统主键
     * @return 信号与系统
     */
    public LxHsag selectLxHsagBySj(Date sj);

    /**
     * 查询信号与系统列表
     * 
     * @param lxHsag 信号与系统
     * @return 信号与系统集合
     */
    public List<LxHsag> selectLxHsagList(LxHsag lxHsag);

    /**
     * 新增信号与系统
     * 
     * @param lxHsag 信号与系统
     * @return 结果
     */
    public int insertLxHsag(LxHsag lxHsag);

    /**
     * 修改信号与系统
     * 
     * @param lxHsag 信号与系统
     * @return 结果
     */
    public int updateLxHsag(LxHsag lxHsag);

    /**
     * 批量删除信号与系统
     * 
     * @param sjs 需要删除的信号与系统主键集合
     * @return 结果
     */
    public int deleteLxHsagBySjs(Date[] sjs);

    /**
     * 删除信号与系统信息
     * 
     * @param sj 信号与系统主键
     * @return 结果
     */
    public int deleteLxHsagBySj(Date sj);
}
