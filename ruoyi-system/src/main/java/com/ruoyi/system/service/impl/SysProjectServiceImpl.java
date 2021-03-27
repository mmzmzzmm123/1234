package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysProjectMapper;
import com.ruoyi.system.domain.SysProject;
import com.ruoyi.system.service.ISysProjectService;

/**
 * 項目Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-03-01
 */
@Service
public class SysProjectServiceImpl implements ISysProjectService 
{
    @Autowired
    private SysProjectMapper sysProjectMapper;

    /**
     * 查询項目
     * 
     * @param id 項目ID
     * @return 項目
     */
    @Override
    public SysProject selectSysProjectById(Long id)
    {
        return sysProjectMapper.selectSysProjectById(id);
    }

    /**
     * 查询項目列表
     * 
     * @param sysProject 項目
     * @return 項目
     */
    @Override
    public List<SysProject> selectSysProjectList(SysProject sysProject)
    {
        return sysProjectMapper.selectSysProjectList(sysProject);
    }

    /**
     * 新增項目
     * 
     * @param sysProject 項目
     * @return 结果
     */
    @Override
    public int insertSysProject(SysProject sysProject)
    {
        sysProject.setCreateTime(DateUtils.getNowDate());
        return sysProjectMapper.insertSysProject(sysProject);
    }

    /**
     * 修改項目
     * 
     * @param sysProject 項目
     * @return 结果
     */
    @Override
    public int updateSysProject(SysProject sysProject)
    {
        sysProject.setUpdateTime(DateUtils.getNowDate());
        return sysProjectMapper.updateSysProject(sysProject);
    }

    /**
     * 批量删除項目
     * 
     * @param ids 需要删除的項目ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectByIds(Long[] ids)
    {
        return sysProjectMapper.deleteSysProjectByIds(ids);
    }

    /**
     * 删除項目信息
     * 
     * @param id 項目ID
     * @return 结果
     */
    @Override
    public int deleteSysProjectById(Long id)
    {
        return sysProjectMapper.deleteSysProjectById(id);
    }
}
