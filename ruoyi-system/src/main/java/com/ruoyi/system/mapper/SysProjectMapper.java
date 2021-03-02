package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysProject;

/**
 * 項目Mapper接口
 * 
 * @author ruoyi
 * @date 2021-03-01
 */
public interface SysProjectMapper 
{
    /**
     * 查询項目
     * 
     * @param id 項目ID
     * @return 項目
     */
    public SysProject selectSysProjectById(Long id);

    /**
     * 查询項目列表
     * 
     * @param sysProject 項目
     * @return 項目集合
     */
    public List<SysProject> selectSysProjectList(SysProject sysProject);

    /**
     * 新增項目
     * 
     * @param sysProject 項目
     * @return 结果
     */
    public int insertSysProject(SysProject sysProject);

    /**
     * 修改項目
     * 
     * @param sysProject 項目
     * @return 结果
     */
    public int updateSysProject(SysProject sysProject);

    /**
     * 删除項目
     * 
     * @param id 項目ID
     * @return 结果
     */
    public int deleteSysProjectById(Long id);

    /**
     * 批量删除項目
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysProjectByIds(Long[] ids);
}
