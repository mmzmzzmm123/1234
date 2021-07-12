package com.apollo.usv.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apollo.usv.mapper.PlanRouteMapper;
import com.apollo.usv.domain.PlanRoute;
import com.apollo.usv.service.IPlanRouteService;

/**
 * 计划轨迹Service业务层处理
 * 
 * @author lwq
 * @date 2021-07-05
 */
@Service
public class PlanRouteServiceImpl implements IPlanRouteService 
{
    @Autowired
    private PlanRouteMapper planRouteMapper;

    /**
     * 查询计划轨迹
     * 
     * @param routeId 计划轨迹ID
     * @return 计划轨迹
     */
    @Override
    public PlanRoute selectPlanRouteById(Long routeId)
    {
        return planRouteMapper.selectPlanRouteById(routeId);
    }

    /**
     * 查询计划轨迹列表
     * 
     * @param planRoute 计划轨迹
     * @return 计划轨迹
     */
    @Override
    public List<PlanRoute> selectPlanRouteList(PlanRoute planRoute)
    {
        return planRouteMapper.selectPlanRouteList(planRoute);
    }

    /**
     * 新增计划轨迹
     * 
     * @param planRoute 计划轨迹
     * @return 结果
     */
    @Override
    public int insertPlanRoute(PlanRoute planRoute)
    {
        return planRouteMapper.insertPlanRoute(planRoute);
    }

    /**
     * 修改计划轨迹
     * 
     * @param planRoute 计划轨迹
     * @return 结果
     */
    @Override
    public int updatePlanRoute(PlanRoute planRoute)
    {
        return planRouteMapper.updatePlanRoute(planRoute);
    }

    /**
     * 批量删除计划轨迹
     * 
     * @param routeIds 需要删除的计划轨迹ID
     * @return 结果
     */
    @Override
    public int deletePlanRouteByIds(Long[] routeIds)
    {
        return planRouteMapper.deletePlanRouteByIds(routeIds);
    }

    /**
     * 删除计划轨迹信息
     * 
     * @param routeId 计划轨迹ID
     * @return 结果
     */
    @Override
    public int deletePlanRouteById(Long routeId)
    {
        return planRouteMapper.deletePlanRouteById(routeId);
    }
}
