package com.moon.home.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * log对象 home_ip_log
 * 
 * @author goo
 * @date 2022-05-19
 */
public class HomeIpLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /**  */
    @Excel(name = "")
    private String ipV4;

    /**  */
    @Excel(name = "")
    private String ipV6;

    /**  */
    @Excel(name = "")
    private String description;

    /**  */
    @Excel(name = "")
    private Long createUserId;

    /**  */
    @Excel(name = "")
    private Long updateUserId;

    /**  */
    @Excel(name = "")
    private Long closed;

    /**  */
    @Excel(name = "")
    private String region;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setIpV4(String ipV4) 
    {
        this.ipV4 = ipV4;
    }

    public String getIpV4() 
    {
        return ipV4;
    }
    public void setIpV6(String ipV6) 
    {
        this.ipV6 = ipV6;
    }

    public String getIpV6() 
    {
        return ipV6;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setCreateUserId(Long createUserId) 
    {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() 
    {
        return createUserId;
    }
    public void setUpdateUserId(Long updateUserId) 
    {
        this.updateUserId = updateUserId;
    }

    public Long getUpdateUserId() 
    {
        return updateUserId;
    }
    public void setClosed(Long closed) 
    {
        this.closed = closed;
    }

    public Long getClosed() 
    {
        return closed;
    }
    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ipV4", getIpV4())
            .append("ipV6", getIpV6())
            .append("description", getDescription())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createUserId", getCreateUserId())
            .append("updateUserId", getUpdateUserId())
            .append("closed", getClosed())
            .append("region", getRegion())
            .toString();
    }
}
