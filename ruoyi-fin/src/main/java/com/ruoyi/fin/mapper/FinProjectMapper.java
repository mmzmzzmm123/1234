package com.ruoyi.fin.mapper;

import java.util.List;
import com.ruoyi.fin.domain.FinProject;

/**
 * 记账项目Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-23
 */
public interface FinProjectMapper 
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
     * 删除记账项目
     * 
     * @param projId 记账项目主键
     * @return 结果
     */
    public int deleteFinProjectByProjId(Long projId);

    /**
     * 批量删除记账项目
     * 
     * @param projIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinProjectByProjIds(Long[] projIds);
}
