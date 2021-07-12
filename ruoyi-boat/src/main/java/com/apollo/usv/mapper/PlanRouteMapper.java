package com.apollo.usv.mapper;

import java.util.List;
import com.apollo.usv.domain.PlanRoute;

/**
 * 计划轨迹Mapper接口
 * 
 * @author lwq
 * @date 2021-07-05
 */
public interface PlanRouteMapper 
{
    /**
     * 查询计划轨迹
     * 
     * @param routeId 计划轨迹ID
     * @return 计划轨迹
     */
    public PlanRoute selectPlanRouteById(Long routeId);

    /**
     * 查询计划轨迹列表
     * 
     * @param planRoute 计划轨迹
     * @return 计划轨迹集合
     */
    public List<PlanRoute> selectPlanRouteList(PlanRoute planRoute);

    /**
     * 新增计划轨迹
     * 
     * @param planRoute 计划轨迹
     * @return 结果
     */
    public int insertPlanRoute(PlanRoute planRoute);

    /**
     * 修改计划轨迹
     * 
     * @param planRoute 计划轨迹
     * @return 结果
     */
    public int updatePlanRoute(PlanRoute planRoute);

    /**
     * 删除计划轨迹
     * 
     * @param routeId 计划轨迹ID
     * @return 结果
     */
    public int deletePlanRouteById(Long routeId);

    /**
     * 批量删除计划轨迹
     * 
     * @param routeIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePlanRouteByIds(Long[] routeIds);
}
