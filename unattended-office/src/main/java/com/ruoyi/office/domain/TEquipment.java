package com.ruoyi.office.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备列表对象 t_equipment
 * 
 * @author ruoyi
 * @date 2023-05-29
 */
public class TEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 发生地? */
    @Excel(name = "发生地?")
    private String name;

    /** 事发阶段 */
    @Excel(name = "事发阶段")
    private String equipType;

    /** 标题 */
    @Excel(name = "标题")
    private String online;

    /** 开关状态 */
    @Excel(name = "开关状态")
    private String onOff;

    /** 设备控制 */
    @Excel(name = "设备控制")
    private String equipControl;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setEquipType(String equipType) 
    {
        this.equipType = equipType;
    }

    public String getEquipType() 
    {
        return equipType;
    }
    public void setOnline(String online)
    {
        this.online = online;
    }

    public String getOnline()
    {
        return online;
    }
    public void setOnOff(String onOff)
    {
        this.onOff = onOff;
    }

    public String getOnOff()
    {
        return onOff;
    }
    public void setEquipControl(String equipControl) 
    {
        this.equipControl = equipControl;
    }

    public String getEquipControl() 
    {
        return equipControl;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("equipType", getEquipType())
            .append("online", getOnline())
            .append("onOff", getOnOff())
            .append("equipControl", getEquipControl())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
