package com.ruoyi.bookmark.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 备份书签对象 sq_backup_bookmark
 * 
 * @author ruoyi
 * @date 2021-11-22
 */
public class SqBackupBookmark extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 备份配置表 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 1 开启 0 未开启 */
    @Excel(name = "1 开启 0 未开启")
    private Integer aliyun;

    /** 1 开启 0 未开启 */
    @Excel(name = "1 开启 0 未开启")
    private Integer baiduyun;

    /** 1 开启 0 未开启 */
    @Excel(name = "1 开启 0 未开启")
    private Integer weiyun;

    /** 备份周期 单位/天 */
    @Excel(name = "备份周期 单位/天")
    private Integer cycle;

    /** 上次备份时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上次备份时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastTime;

    /** 备份时间(到这个点就进行备份)=上次备份时间+cycle天数 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "备份时间(到这个点就进行备份)=上次备份时间+cycle天数", width = 30, dateFormat = "yyyy-MM-dd")
    private Date backupTime;

    /** 百度token */
    @Excel(name = "百度token")
    private String baiduyunToken;

    /** 阿里token */
    @Excel(name = "阿里token")
    private String aliyunToken;

    /** 微云token */
    @Excel(name = "微云token")
    private String weiyunToken;

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
    public void setAliyun(Integer aliyun) 
    {
        this.aliyun = aliyun;
    }

    public Integer getAliyun() 
    {
        return aliyun;
    }
    public void setBaiduyun(Integer baiduyun) 
    {
        this.baiduyun = baiduyun;
    }

    public Integer getBaiduyun() 
    {
        return baiduyun;
    }
    public void setWeiyun(Integer weiyun) 
    {
        this.weiyun = weiyun;
    }

    public Integer getWeiyun() 
    {
        return weiyun;
    }
    public void setCycle(Integer cycle) 
    {
        this.cycle = cycle;
    }

    public Integer getCycle() 
    {
        return cycle;
    }
    public void setLastTime(Date lastTime) 
    {
        this.lastTime = lastTime;
    }

    public Date getLastTime() 
    {
        return lastTime;
    }
    public void setBackupTime(Date backupTime) 
    {
        this.backupTime = backupTime;
    }

    public Date getBackupTime() 
    {
        return backupTime;
    }
    public void setBaiduyunToken(String baiduyunToken) 
    {
        this.baiduyunToken = baiduyunToken;
    }

    public String getBaiduyunToken() 
    {
        return baiduyunToken;
    }
    public void setAliyunToken(String aliyunToken) 
    {
        this.aliyunToken = aliyunToken;
    }

    public String getAliyunToken() 
    {
        return aliyunToken;
    }
    public void setWeiyunToken(String weiyunToken) 
    {
        this.weiyunToken = weiyunToken;
    }

    public String getWeiyunToken() 
    {
        return weiyunToken;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("aliyun", getAliyun())
            .append("baiduyun", getBaiduyun())
            .append("weiyun", getWeiyun())
            .append("cycle", getCycle())
            .append("lastTime", getLastTime())
            .append("backupTime", getBackupTime())
            .append("baiduyunToken", getBaiduyunToken())
            .append("aliyunToken", getAliyunToken())
            .append("weiyunToken", getWeiyunToken())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
