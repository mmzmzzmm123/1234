package com.apollo.usv.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 计划轨迹对象 usv_plan_route
 * 
 * @author lwq
 * @date 2021-07-05
 */
public class PlanRoute extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long routeId;

    /** 轨迹编号 */
    @Excel(name = "轨迹编号")
    private String routeSn;

    /** 航速 */
    @Excel(name = "航速")
    private Double speed;

    /** 通过坐标 */
    @Excel(name = "通过坐标")
    private String coordinates;

    public void setRouteId(Long routeId) 
    {
        this.routeId = routeId;
    }

    public Long getRouteId() 
    {
        return routeId;
    }
    public void setRouteSn(String routeSn) 
    {
        this.routeSn = routeSn;
    }

    public String getRouteSn() 
    {
        return routeSn;
    }
    public void setSpeed(Double speed) 
    {
        this.speed = speed;
    }

    public Double getSpeed() 
    {
        return speed;
    }
    public void setCoordinates(String coordinates) 
    {
        this.coordinates = coordinates;
    }

    public String getCoordinates() 
    {
        return coordinates;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("routeId", getRouteId())
            .append("routeSn", getRouteSn())
            .append("speed", getSpeed())
            .append("coordinates", getCoordinates())
            .toString();
    }
}
