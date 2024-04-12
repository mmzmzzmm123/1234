package com.ruoyi.autoee.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 建筑物管理对象 a_building
 *
 * @author ruoyi
 * @date 2024-04-12
 */
public class ABuilding extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 建筑物编号 */
    @Excel(name = "建筑物编号")
    private Long id;

    /** 建筑物名称 */
    @Excel(name = "建筑物名称")
    private String buildingName;

    /** 所属学校 */
    @Excel(name = "所属学校")
    private Long schoolId;

    /** 建筑类型 */
    @Excel(name = "建筑类型", dictType = "building_type")
    private String buildingType;

    /** 所在地区 */
    @Excel(name = "所在地区")
    private String Location;

    /** 建筑面积 */
    @Excel(name = "建筑面积")
    private String buildingArea;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setBuildingName(String buildingName)
    {
        this.buildingName = buildingName;
    }

    public String getBuildingName()
    {
        return buildingName;
    }
    public void setSchoolId(Long schoolId)
    {
        this.schoolId = schoolId;
    }

    public Long getSchoolId()
    {
        return schoolId;
    }
    public void setBuildingType(String buildingType)
    {
        this.buildingType = buildingType;
    }

    public String getBuildingType()
    {
        return buildingType;
    }
    public void setLocation(String Location)
    {
        this.Location = Location;
    }

    public String getLocation()
    {
        return Location;
    }
    public void setBuildingArea(String buildingArea)
    {
        this.buildingArea = buildingArea;
    }

    public String getBuildingArea()
    {
        return buildingArea;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("buildingName", getBuildingName())
            .append("schoolId", getSchoolId())
            .append("buildingType", getBuildingType())
            .append("Location", getLocation())
            .append("buildingArea", getBuildingArea())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
