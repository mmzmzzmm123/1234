package com.ruoyi.project.benyi.mapper;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByPlanweekitem;

/**
 * 周计划(家长和教育部门细化)Mapper接口
 *
 * @author tsbz
 * @date 2020-08-27
 */
public interface ByPlanweekitemMapper
{
    /**
     * 查询周计划(家长和教育部门细化)
     *
     * @param id 周计划(家长和教育部门细化)ID
     * @return 周计划(家长和教育部门细化)
     */
    public ByPlanweekitem selectByPlanweekitemById(Long id);

    /**
     * 查询周计划(家长和教育部门细化)列表
     *
     * @param byPlanweekitem 周计划(家长和教育部门细化)
     * @return 周计划(家长和教育部门细化)集合
     */
    public List<ByPlanweekitem> selectByPlanweekitemList(ByPlanweekitem byPlanweekitem);

    /**
     * 新增周计划(家长和教育部门细化)
     *
     * @param byPlanweekitem 周计划(家长和教育部门细化)
     * @return 结果
     */
    public int insertByPlanweekitem(ByPlanweekitem byPlanweekitem);

    /**
     * 修改周计划(家长和教育部门细化)
     *
     * @param byPlanweekitem 周计划(家长和教育部门细化)
     * @return 结果
     */
    public int updateByPlanweekitem(ByPlanweekitem byPlanweekitem);

    /**
     * 删除周计划(家长和教育部门细化)
     *
     * @param id 周计划(家长和教育部门细化)ID
     * @return 结果
     */
    public int deleteByPlanweekitemById(Long id);

    /**
     * 批量删除周计划(家长和教育部门细化)
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByPlanweekitemByIds(Long[] ids);
}
