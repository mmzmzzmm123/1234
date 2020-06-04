package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 一日流程标准对象 by_day_flow_standard
 *
 * @author tsbz
 * @date 2020-05-18
 */
public class ByDayFlowStandard extends BaseEntity {
    /**
     * 标识
     */
    private Long id;

    /**
     * 任务id
     */
    @Excel(name = "任务id")
    private Long taskCode;

    /**
     * 标准的名称
     */
    @Excel(name = "标准的名称")
    private String standardTitle;

    /**
     * 标准排序
     */
    @Excel(name = "标准排序")
    private Long standardSort;

    /**
     * 标准数量
     */
    @Excel(name = "解读数量")
    private Long unscrambleCount;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuser;

    /**
     * 更新者
     */
    @Excel(name = "更新者")
    private Long updateuser;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTaskCode(Long taskCode) {
        this.taskCode = taskCode;
    }

    public Long getTaskCode() {
        return taskCode;
    }

    public void setStandardTitle(String standardTitle) {
        this.standardTitle = standardTitle;
    }

    public String getStandardTitle() {
        return standardTitle;
    }

    public void setStandardSort(Long standardSort) {
        this.standardSort = standardSort;
    }

    public Long getStandardSort() {
        return standardSort;
    }

    public void setUnscrambleCount(Long unscrambleCount) {
        this.unscrambleCount = unscrambleCount;
    }

    public Long getUnscrambleCount() {
        return unscrambleCount;
    }

    public void setCreateuser(Long createuser) {
        this.createuser = createuser;
    }

    public Long getCreateuser() {
        return createuser;
    }

    public void setUpdateuser(Long updateuser) {
        this.updateuser = updateuser;
    }

    public Long getUpdateuser() {
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("sid", getId())
                .append("taskCode", getTaskCode())
                .append("standardTitle", getStandardTitle())
                .append("standardSort", getStandardSort())
                .append("unscrambleCount", getUnscrambleCount())
                .append("createuser", getCreateuser())
                .append("createtime", getCreatetime())
                .append("updateuser", getUpdateuser())
                .append("updatetime", getUpdatetime())
                .toString();
    }

}
