package com.ruoyi.project.benyi.mapper;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByPlanweek;

/**
 * 周计划(家长和教育部门)Mapper接口
 *
 * @author tsbz
 * @date 2020-08-24
 */
public interface ByPlanweekMapper
{
    /**
     * 查询周计划(家长和教育部门)
     *
     * @param id 周计划(家长和教育部门)ID
     * @return 周计划(家长和教育部门)
     */
    public ByPlanweek selectByPlanweekById(String id);

    /**
     * 查询周计划(家长和教育部门)列表
     *
     * @param byPlanweek 周计划(家长和教育部门)
     * @return 周计划(家长和教育部门)集合
     */
    public List<ByPlanweek> selectByPlanweekList(ByPlanweek byPlanweek);

    /**
     * 新增周计划(家长和教育部门)
     *
     * @param byPlanweek 周计划(家长和教育部门)
     * @return 结果
     */
    public int insertByPlanweek(ByPlanweek byPlanweek);

    /**
     * 修改周计划(家长和教育部门)
     *
     * @param byPlanweek 周计划(家长和教育部门)
     * @return 结果
     */
    public int updateByPlanweek(ByPlanweek byPlanweek);

    /**
     * 删除周计划(家长和教育部门)
     *
     * @param id 周计划(家长和教育部门)ID
     * @return 结果
     */
    public int deleteByPlanweekById(String id);

    /**
     * 批量删除周计划(家长和教育部门)
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByPlanweekByIds(String[] ids);
}
