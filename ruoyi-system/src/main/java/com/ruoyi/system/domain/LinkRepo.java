package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.NotNull;

/**
 * 链接仓库对象 link_repo
 *
 * @author ysj
 * @date 2021-12-07
 */
public class LinkRepo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    private String title;

    /** 链接持有者ID */
    @Excel(name = "链接持有者ID")
    @NotNull
    private Long userId;
    private String userName;


    /** 描述信息 */
    @Excel(name = "描述信息")
    private String descri;

    /** 有效时间（单位：s） */
    @Excel(name = "有效时间", readConverterExp = "单=位：s")
    private Long avaTime;

    /** 全景ID */
    @Excel(name = "全景ID")
    @NotNull
    private Long panoramaId;
    private String panName;

    /** 大屏ID */
    @Excel(name = "大屏ID")
    @NotNull
    private Long visualId;
    private String visualName;

    /** 链接有效开始时间 */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "链接有效开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 链接有效结束时间 */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "链接有效结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPanName() {
        return panName;
    }

    public void setPanName(String panName) {
        this.panName = panName;
    }

    public String getVisualName() {
        return visualName;
    }

    public void setVisualName(String visualName) {
        this.visualName = visualName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setDescri(String descri)
    {
        this.descri = descri;
    }

    public String getDescri()
    {
        return descri;
    }
    public void setAvaTime(Long avaTime)
    {
        this.avaTime = avaTime;
    }

    public Long getAvaTime()
    {
        return avaTime;
    }
    public void setPanoramaId(Long panoramaId)
    {
        this.panoramaId = panoramaId;
    }

    public Long getPanoramaId()
    {
        return panoramaId;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }
    public void setVisualId(Long visualId)
    {
        this.visualId = visualId;
    }

    public Long getVisualId()
    {
        return visualId;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
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
                .append("id", getId())
                .append("userId", getUserId())
                .append("descri", getDescri())
                .append("avaTime", getAvaTime())
                .append("panoramaId", getPanoramaId())
                .append("startTime", getStartTime())
                .append("visualId", getVisualId())
                .append("endTime", getEndTime())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
