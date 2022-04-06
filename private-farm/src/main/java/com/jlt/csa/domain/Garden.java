package com.jlt.csa.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 菜地划分对象 csa_garden
 *
 * @author 郏磊涛
 * @date 2022-03-29
 */
public class Garden extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜地id */
    private Long gardenId;

    /** 农场分区 */
    @Excel(name = "农场分区")
    private Long zoneId;

    /** 农场分区对象 */
    private FarmZone farmZone;

    /** 菜地编号 */
    @Excel(name = "菜地编号")
    private String code;

    /** 菜地名称 */
    @Excel(name = "菜地名称")
    private String name;

    /** 平方米 */
    @Excel(name = "平方米")
    private Long m2;

    /** 会员 */
    @Excel(name = "会员id")
    private Long farmerId;

    /** 菜地管理员 */
    @Excel(name = "菜地管理员")
    private Long managerId;

    /** 工人 */
    @Excel(name = "工人")
    private Long workerId;

    /** 客服人员 */
    @Excel(name = "客服人员")
    private Long contacterId;

    /** 耕作状态 */
    @Excel(name = "耕作状态")
    private String isFarming;

    /** 售出状态 */
    @Excel(name = "售出状态")
    private String isSelled;

    /** 竣工状态 */
    @Excel(name = "竣工状态")
    private String isCompleted;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志 */
    private String delFlag;

    public void setGardenId(Long gardenId)
    {
        this.gardenId = gardenId;
    }

    public Long getGardenId()
    {
        return gardenId;
    }
    public void setZoneId(Long zoneId)
    {
        this.zoneId = zoneId;
    }

    public Long getZoneId()
    {
        return zoneId;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public FarmZone getFarmZone() {
        return farmZone;
    }

    public void setFarmZone(FarmZone farmZone) {
        this.farmZone = farmZone;
    }

    public String getCode()
    {
        return code;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setM2(Long m2)
    {
        this.m2 = m2;
    }

    public Long getM2()
    {
        return m2;
    }
    public void setFarmerId(Long farmerId)
    {
        this.farmerId = farmerId;
    }

    public Long getFarmerId()
    {
        return farmerId;
    }
    public void setManagerId(Long managerId)
    {
        this.managerId = managerId;
    }

    public Long getManagerId()
    {
        return managerId;
    }
    public void setWorkerId(Long workerId)
    {
        this.workerId = workerId;
    }

    public Long getWorkerId()
    {
        return workerId;
    }
    public void setContacterId(Long contacterId)
    {
        this.contacterId = contacterId;
    }

    public Long getContacterId()
    {
        return contacterId;
    }
    public void setIsFarming(String isFarming)
    {
        this.isFarming = isFarming;
    }

    public String getIsFarming()
    {
        return isFarming;
    }
    public void setIsSelled(String isSelled)
    {
        this.isSelled = isSelled;
    }

    public String getIsSelled()
    {
        return isSelled;
    }
    public void setIsCompleted(String isCompleted)
    {
        this.isCompleted = isCompleted;
    }

    public String getIsCompleted()
    {
        return isCompleted;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("gardenId", getGardenId())
                .append("zoneId", getZoneId())
                .append("code", getCode())
                .append("name", getName())
                .append("m2", getM2())
                .append("farmerId", getFarmerId())
                .append("managerId", getManagerId())
                .append("workerId", getWorkerId())
                .append("contacterId", getContacterId())
                .append("isFarming", getIsFarming())
                .append("isSelled", getIsSelled())
                .append("isCompleted", getIsCompleted())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
