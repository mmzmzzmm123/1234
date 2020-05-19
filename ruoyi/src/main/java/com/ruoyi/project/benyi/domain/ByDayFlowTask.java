package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 一日流程任务对象 by_day_flow_task
 * 
 * @author tsbz
 * @date 2020-05-14
 */
public class ByDayFlowTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标识 */
    private Long code;

    /** 任务名称(标签) */
    @Excel(name = "任务名称(标签)")
    private String taskLable;

    /** 任务键值 */
    @Excel(name = "任务键值")
    private String taskValue;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String detailName;

    /** 任务目的 */
    @Excel(name = "任务目的")
    private String taskTarget;

    /** 任务解读 */
    @Excel(name = "任务解读")
    private String taskContent;

    /** 任务排序 */
    @Excel(name = "任务排序")
    private Integer taskSort;

    /** 样式属性（其他样式扩展) */
    @Excel(name = "样式属性", readConverterExp = "样式属性（其他样式扩展)")
    private String cssClass;

    /** 表格回显样式 */
    @Excel(name = "表格回显样式")
    private String listClass;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createuser;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    /** 更新人 */
    @Excel(name = "更新人")
    private Long updateuser;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    public void setCode(Long code)
    {
        this.code = code;
    }

    public Long getCode()
    {
        return code;
    }
    public void setTaskLable(String taskLable) 
    {
        this.taskLable = taskLable;
    }

    public String getTaskLable() 
    {
        return taskLable;
    }
    public void setTaskValue(String taskValue) 
    {
        this.taskValue = taskValue;
    }

    public String getTaskValue() 
    {
        return taskValue;
    }
    public void setDetailName(String detailName) 
    {
        this.detailName = detailName;
    }

    public String getDetailName() 
    {
        return detailName;
    }
    public void setTaskTarget(String taskTarget) 
    {
        this.taskTarget = taskTarget;
    }

    public String getTaskTarget() 
    {
        return taskTarget;
    }
    public void setTaskContent(String taskContent) 
    {
        this.taskContent = taskContent;
    }

    public String getTaskContent() 
    {
        return taskContent;
    }
    public void setTaskSort(Integer taskSort) 
    {
        this.taskSort = taskSort;
    }

    public Integer getTaskSort() 
    {
        return taskSort;
    }
    public void setCssClass(String cssClass) 
    {
        this.cssClass = cssClass;
    }

    public String getCssClass() 
    {
        return cssClass;
    }
    public void setListClass(String listClass) 
    {
        this.listClass = listClass;
    }

    public String getListClass() 
    {
        return listClass;
    }
    public void setCreateuser(Long createuser) 
    {
        this.createuser = createuser;
    }

    public Long getCreateuser() 
    {
        return createuser;
    }
    public void setUpdateuser(Long updateuser) 
    {
        this.updateuser = updateuser;
    }

    public Long getUpdateuser() 
    {
        return updateuser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("code", getCode())
            .append("taskLable", getTaskLable())
            .append("taskValue", getTaskValue())
            .append("detailName", getDetailName())
            .append("taskTarget", getTaskTarget())
            .append("taskContent", getTaskContent())
            .append("taskSort", getTaskSort())
            .append("cssClass", getCssClass())
            .append("listClass", getListClass())
            .append("createuser", getCreateuser())
            .append("updateuser", getUpdateuser())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("remark", getRemark())
            .toString();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
