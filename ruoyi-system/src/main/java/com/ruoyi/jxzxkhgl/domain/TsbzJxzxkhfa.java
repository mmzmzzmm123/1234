package com.ruoyi.jxzxkhgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 见习之星考核方案对象 tsbz_jxzxkhfa
 * 
 * @author ruoyi
 * @date 2020-08-29
 */
public class TsbzJxzxkhfa extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 考核方案名称 */
    @Excel(name = "考核方案名称")
    private String name;

    /** 考核方案状态
            0:未启动
            1:启动
            9:已结束 */
    @Excel(name = "考核方案状态")
    private String status;

    /** 考核开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考核开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date starttime;

    /** 考核结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考核结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endtime;

    /** 考核文件名称 */
    @Excel(name = "考核文件名称")
    private String filename;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filepath;

    /** 考核年份 */
    @JsonFormat(pattern = "yyyy")
    @Excel(name = "考核年份", width = 30, dateFormat = "yyyy")
    private Date khnf;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createuserid;

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
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setStarttime(Date starttime) 
    {
        this.starttime = starttime;
    }

    public Date getStarttime() 
    {
        return starttime;
    }
    public void setEndtime(Date endtime) 
    {
        this.endtime = endtime;
    }

    public Date getEndtime() 
    {
        return endtime;
    }
    public void setFilename(String filename) 
    {
        this.filename = filename;
    }

    public String getFilename() 
    {
        return filename;
    }
    public void setFilepath(String filepath) 
    {
        this.filepath = filepath;
    }

    public String getFilepath() 
    {
        return filepath;
    }
    public void setKhnf(Date khnf) 
    {
        this.khnf = khnf;
    }

    public Date getKhnf() 
    {
        return khnf;
    }
    public void setCreateuserid(Long createuserid) 
    {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() 
    {
        return createuserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("status", getStatus())
            .append("starttime", getStarttime())
            .append("endtime", getEndtime())
            .append("filename", getFilename())
            .append("filepath", getFilepath())
            .append("khnf", getKhnf())
            .append("createuserid", getCreateuserid())
            .append("createTime", getCreateTime())
            .toString();
    }
}
