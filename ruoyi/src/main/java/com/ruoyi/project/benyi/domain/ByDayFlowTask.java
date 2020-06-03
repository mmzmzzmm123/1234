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

    /** 一日流程id */
    @Excel(name = "一日流程id")
    private Long detailId;

    /** 任务解读 */
    @Excel(name = "任务解读")
    private String taskContent;

    /** 任务排序 */
    @Excel(name = "任务排序")
    private Integer taskSort;

    /** 标签 */
    @Excel(name = "标签")
    private String label;

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

    public void setDetailId(Long detailId)
    {
        this.detailId = detailId;
    }

    public Long getDetailId()
    {
        return detailId;
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

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("code", getCode())
                .append("taskLable", getTaskLable())
                .append("detailId", getDetailId())
                .append("taskContent", getTaskContent())
                .append("taskSort", getTaskSort())
                .append("label", getLabel())
                .append("createuser", getCreateuser())
                .append("updateuser", getUpdateuser())
                .append("createtime", getCreatetime())
                .append("updatetime", getUpdatetime())
                .append("remark", getRemark())
            .toString();
    }

}
