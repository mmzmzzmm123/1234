package com.ruoyi.autoee.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 楼层管理对象 a_floor
 *
 * @author ruoyi
 * @date 2024-04-12
 */
public class AFloor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 楼层编号 */
    private Long id;

    /** 楼层名称 */
    @Excel(name = "楼层名称")
    private String floorName;

    /** 所属建筑物 */
    private Long buildingId;
    @Excel(name = "所属建筑物")
    private String buildingName;

    /** 楼层面积 */
    @Excel(name = "楼层面积")
    private String floorArea;

    /** 房间数量 */
    @Excel(name = "房间数量")
    private Integer roomCount;

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setFloorName(String floorName)
    {
        this.floorName = floorName;
    }

    public String getFloorName()
    {
        return floorName;
    }
    public void setBuildingId(Long buildingId)
    {
        this.buildingId = buildingId;
    }

    public Long getBuildingId()
    {
        return buildingId;
    }
    public void setFloorArea(String floorArea)
    {
        this.floorArea = floorArea;
    }

    public String getFloorArea()
    {
        return floorArea;
    }
    public void setRoomCount(Integer roomCount)
    {
        this.roomCount = roomCount;
    }

    public Integer getRoomCount()
    {
        return roomCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("floorName", getFloorName())
            .append("buildingId", getBuildingId())
            .append("floorArea", getFloorArea())
            .append("roomCount", getRoomCount())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
