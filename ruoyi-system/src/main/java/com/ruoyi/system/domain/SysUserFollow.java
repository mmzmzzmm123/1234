package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.persistence.Column;
import java.util.Date;

/**
 * 我的关注对象 sys_user_follow
 *
 * @author ruoyi
 * @date 2021-01-24
 */
public class SysUserFollow
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 关注的用户id */
    @Excel(name = "关注的用户id")
    private Long followUserId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

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
    public void setFollowUserId(Long followUserId)
    {
        this.followUserId = followUserId;
    }

    public Long getFollowUserId()
    {
        return followUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysUserFollow{" +
                "id=" + id +
                ", userId=" + userId +
                ", followUserId=" + followUserId +
                ", createTime=" + createTime +
                '}';
    }
}
