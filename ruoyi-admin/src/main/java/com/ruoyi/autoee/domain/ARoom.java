package com.ruoyi.autoee.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 房间管理对象 a_room
 *
 * @author ruoyi
 * @date 2024-04-12
 */
public class ARoom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 房间编号 */
    private Long id;

    /** 房间名称 */
    @Excel(name = "房间名称")
    private String roomName;

    /** 所属楼层*/
    private Long floorId;
    @Excel(name = "所属楼层")
    private String floorName;

    /** 房间类型 */
    @Excel(name = "房间类型", dictType = "room_type")
    private String roomType;

    /** 房间面积 */
    @Excel(name = "房间面积")
    private String roomArea;

    /** 所属学科 */
    @Excel(name = "所属学科")
    private String subject;

	public Long getFloorId() {
		return floorId;
	}

	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    public String getRoomName()
    {
        return roomName;
    }
    public void setBuildingId(Long floorId)
    {
        this.floorId = floorId;
    }

    public Long getBuildingId()
    {
        return floorId;
    }
    public void setRoomType(String roomType)
    {
        this.roomType = roomType;
    }

    public String getRoomType()
    {
        return roomType;
    }
    public void setRoomArea(String roomArea)
    {
        this.roomArea = roomArea;
    }

    public String getRoomArea()
    {
        return roomArea;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSubject()
    {
        return subject;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("roomName", getRoomName())
            .append("floorId", getBuildingId())
            .append("roomType", getRoomType())
            .append("roomArea", getRoomArea())
            .append("subject", getSubject())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
