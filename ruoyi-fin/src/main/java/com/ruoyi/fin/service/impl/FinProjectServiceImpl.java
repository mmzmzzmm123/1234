package com.ruoyi.fin.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fin.mapper.FinProjectMapper;
import com.ruoyi.fin.domain.FinProject;
import com.ruoyi.fin.service.IFinProjectService;

/**
 * 记账项目Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-23
 */
@Service
public class FinProjectServiceImpl implements IFinProjectService 
{
    @Autowired
    private FinProjectMapper finProjectMapper;

    /**
     * 查询记账项目
     * 
     * @param projId 记账项目主键
     * @return 记账项目
     */
    @Override
    public FinProject selectFinProjectByProjId(Long projId)
    {
        return finProjectMapper.selectFinProjectByProjId(projId);
    }

    /**
     * 查询记账项目列表
     * 
     * @param finProject 记账项目
     * @return 记账项目
     */
    @Override
    public List<FinProject> selectFinProjectList(FinProject finProject)
    {
        return finProjectMapper.selectFinProjectList(finProject);
    }

    /**
     * 新增记账项目
     * 
     * @param finProject 记账项目
     * @return 结果
     */
    @Override
    public int insertFinProject(FinProject finProject)
    {
        finProject.setCreateTime(DateUtils.getNowDate());
        finProject.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        return finProjectMapper.insertFinProject(finProject);
    }

    /**
     * 修改记账项目
     * 
     * @param finProject 记账项目
     * @return 结果
     */
    @Override
    public int updateFinProject(FinProject finProject)
    {
        finProject.setUpdateTime(DateUtils.getNowDate());
        finProject.setUpdateBy(SecurityUtils.getLoginUser().getUsername());
        return finProjectMapper.updateFinProject(finProject);
    }

    /**
     * 批量删除记账项目
     * 
     * @param projIds 需要删除的记账项目主键
     * @return 结果
     */
    @Override
    public int deleteFinProjectByProjIds(Long[] projIds)
    {
        return finProjectMapper.deleteFinProjectByProjIds(projIds);
    }

    /**
     * 删除记账项目信息
     * 
     * @param projId 记账项目主键
     * @return 结果
     */
    @Override
    public int deleteFinProjectByProjId(Long projId)
    {
        return finProjectMapper.deleteFinProjectByProjId(projId);
    }
}
