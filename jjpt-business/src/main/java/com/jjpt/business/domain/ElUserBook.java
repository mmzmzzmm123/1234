package com.jjpt.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 错题本对象 el_user_book
 * 
 * @author 卢亚峰
 * @date 2024-05-22
 */
public class ElUserBook extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 考试ID */
    @Excel(name = "考试ID")
    private String examId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 题目ID */
    @Excel(name = "题目ID")
    private String quId;

    /** 错误时间 */
    @Excel(name = "错误时间")
    private Integer wrongCount;

    /** 题目标题 */
    @Excel(name = "题目标题")
    private String title;

    /** 错题序号 */
    @Excel(name = "错题序号")
    private Integer sort;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setExamId(String examId) 
    {
        this.examId = examId;
    }

    public String getExamId() 
    {
        return examId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setQuId(String quId) 
    {
        this.quId = quId;
    }

    public String getQuId() 
    {
        return quId;
    }
    public void setWrongCount(Integer wrongCount)
    {
        this.wrongCount = wrongCount;
    }

    public Integer getWrongCount()
    {
        return wrongCount;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public Integer getSort()
    {
        return sort;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("examId", getExamId())
            .append("userId", getUserId())
            .append("quId", getQuId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("wrongCount", getWrongCount())
            .append("title", getTitle())
            .append("sort", getSort())
            .append("deptId", getDeptId())
            .toString();
    }
}
