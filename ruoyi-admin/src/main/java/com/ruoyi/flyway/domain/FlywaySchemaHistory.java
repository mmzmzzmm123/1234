package com.ruoyi.flyway.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * flyway管理对象 flyway_schema_history
 * 
 * @author yangwb
 * @date 2022-07-07
 */
public class FlywaySchemaHistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long installedRank;

    /** 版本 */
    @Excel(name = "版本")
    private String version;

    /** 脚本类型 */
    @Excel(name = "脚本类型")
    private String description;

    /** 脚本后缀 */
    @Excel(name = "脚本后缀")
    private String type;

    /** 脚本名称 */
    @Excel(name = "脚本名称")
    private String script;

    /** 校验编码 */
    @Excel(name = "校验编码")
    private Long checksum;

    /** 执行人 */
    @Excel(name = "执行人")
    private String installedBy;

    /** 操作日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "操作日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installedOn;

    /** 执行时长 */
    @Excel(name = "执行时长")
    private Long executionTime;

    /** 是否成功 */
    @Excel(name = "是否成功")
    private Integer success;

    public void setInstalledRank(Long installedRank) 
    {
        this.installedRank = installedRank;
    }

    public Long getInstalledRank() 
    {
        return installedRank;
    }
    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setScript(String script) 
    {
        this.script = script;
    }

    public String getScript() 
    {
        return script;
    }
    public void setChecksum(Long checksum) 
    {
        this.checksum = checksum;
    }

    public Long getChecksum() 
    {
        return checksum;
    }
    public void setInstalledBy(String installedBy) 
    {
        this.installedBy = installedBy;
    }

    public String getInstalledBy() 
    {
        return installedBy;
    }
    public void setInstalledOn(Date installedOn) 
    {
        this.installedOn = installedOn;
    }

    public Date getInstalledOn() 
    {
        return installedOn;
    }
    public void setExecutionTime(Long executionTime) 
    {
        this.executionTime = executionTime;
    }

    public Long getExecutionTime() 
    {
        return executionTime;
    }
    public void setSuccess(Integer success) 
    {
        this.success = success;
    }

    public Integer getSuccess() 
    {
        return success;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("installedRank", getInstalledRank())
            .append("version", getVersion())
            .append("description", getDescription())
            .append("type", getType())
            .append("script", getScript())
            .append("checksum", getChecksum())
            .append("installedBy", getInstalledBy())
            .append("installedOn", getInstalledOn())
            .append("executionTime", getExecutionTime())
            .append("success", getSuccess())
            .toString();
    }
}
