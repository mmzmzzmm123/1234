package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 全景图管理对象 panorama_info
 * 
 * @author ruoyi
 * @date 2021-11-01
 */
public class PanoramaInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 全景图名称 */
    @Excel(name = "全景图名称")
    private String name;

    /** 场景个数 */
    @Excel(name = "场景个数")
    private Long sceneNum;

    /** 传感器个数 */
    @Excel(name = "传感器个数")
    private Long sensorNum;

    /** 描述信息 */
    @Excel(name = "描述信息")
    private String descri;

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
    public void setSceneNum(Long sceneNum) 
    {
        this.sceneNum = sceneNum;
    }

    public Long getSceneNum() 
    {
        return sceneNum;
    }
    public void setSensorNum(Long sensorNum) 
    {
        this.sensorNum = sensorNum;
    }

    public Long getSensorNum() 
    {
        return sensorNum;
    }
    public void setDescri(String descri) 
    {
        this.descri = descri;
    }

    public String getDescri() 
    {
        return descri;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("sceneNum", getSceneNum())
            .append("sensorNum", getSensorNum())
            .append("descri", getDescri())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
