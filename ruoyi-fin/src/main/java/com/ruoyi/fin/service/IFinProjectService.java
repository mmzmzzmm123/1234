package com.ruoyi.fin.service;

import java.util.List;
import com.ruoyi.fin.domain.FinProject;

/**
 * 记账项目Service接口
 * 
 * @author ruoyi
 * @date 2023-09-15
 */
public interface IFinProjectService 
{
    /**
     * 查询记账项目
     * 
     * @param projId 记账项目主键
     * @return 记账项目
     */
    public FinProject selectFinProjectByProjId(Long projId);

    /**
     * 查询记账项目列表
     * 
     * @param finProject 记账项目
     * @return 记账项目集合
     */
    public List<FinProject> selectFinProjectList(FinProject finProject);

    /**
     * 新增记账项目
     * 
     * @param finProject 记账项目
     * @return 结果
     */
    public int insertFinProject(FinProject finProject);

    /**
     * 修改记账项目
     * 
     * @param finProject 记账项目
     * @return 结果
     */
    public int updateFinProject(FinProject finProject);

    /**
     * 批量删除记账项目
     * 
     * @param projIds 需要删除的记账项目主键集合
     * @return 结果
     */
    public int deleteFinProjectByProjIds(Long[] projIds);

    /**
     * 删除记账项目信息
     * 
     * @param projId 记账项目主键
     * @return 结果
     */
    public int deleteFinProjectByProjId(Long projId);
}
