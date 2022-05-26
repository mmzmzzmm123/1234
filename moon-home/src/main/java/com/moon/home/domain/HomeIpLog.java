package com.moon.home.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * ip日志对象 home_ip_log
 * 
 * @author fo
 * @date 2022-05-26
 */
public class HomeIpLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** ipV4 */
    @Excel(name = "ipV4")
    private String ipV4;

    /** ipV6 */
    @Excel(name = "ipV6")
    private String ipV6;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 是否关闭 */
    @Excel(name = "是否关闭")
    private Long closed;

    /** 区域 */
    @Excel(name = "区域")
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
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("closed", getClosed())
            .append("region", getRegion())
            .toString();
    }
}
