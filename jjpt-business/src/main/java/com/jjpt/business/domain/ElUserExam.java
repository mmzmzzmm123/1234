package com.jjpt.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考试记录对象 el_user_exam
 * 
 * @author 卢亚峰
 * @date 2024-05-22
 */
public class ElUserExam extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 考试ID */
    @Excel(name = "考试ID")
    private String examId;

    /** 考试次数 */
    @Excel(name = "考试次数")
    private Integer tryCount;

    /** 最高分数 */
    @Excel(name = "最高分数")
    private Integer maxScore;

    /** 是否通过 */
    @Excel(name = "是否通过")
    private Boolean passed;

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
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setExamId(String examId) 
    {
        this.examId = examId;
    }

    public String getExamId() 
    {
        return examId;
    }
    public void setTryCount(Integer tryCount) 
    {
        this.tryCount = tryCount;
    }

    public Integer getTryCount() 
    {
        return tryCount;
    }
    public void setMaxScore(Integer maxScore) 
    {
        this.maxScore = maxScore;
    }

    public Integer getMaxScore() 
    {
        return maxScore;
    }
    public void setPassed(Boolean passed) 
    {
        this.passed = passed;
    }

    public Boolean getPassed() 
    {
        return passed;
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
            .append("userId", getUserId())
            .append("examId", getExamId())
            .append("tryCount", getTryCount())
            .append("maxScore", getMaxScore())
            .append("passed", getPassed())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("deptId", getDeptId())
            .toString();
    }
}
