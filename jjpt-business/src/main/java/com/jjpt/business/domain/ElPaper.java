package com.jjpt.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考试记录对象 el_paper
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
public class ElPaper extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 试卷ID */
    private String id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 规则ID */
    @Excel(name = "规则ID")
    private String examId;

    /** 考试标题 */
    @Excel(name = "考试标题")
    private String title;

    /** 考试时长 */
    @Excel(name = "考试时长")
    private Long totalTime;

    /** 用户时长 */
    @Excel(name = "用户时长")
    private Long userTime;

    /** 试卷总分 */
    @Excel(name = "试卷总分")
    private Long totalScore;

    /** 及格分 */
    @Excel(name = "及格分")
    private Long qualifyScore;

    /** 客观分 */
    @Excel(name = "客观分")
    private Long objScore;

    /** 主观分 */
    @Excel(name = "主观分")
    private Long subjScore;

    /** 用户得分 */
    @Excel(name = "用户得分")
    private Long userScore;

    /** 是否包含简答题 */
    @Excel(name = "是否包含简答题")
    private Long hasSaq;

    /** 试卷状态 */
    @Excel(name = "试卷状态")
    private Long state;

    /** 截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date limitTime;

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
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setExamId(String examId) 
    {
        this.examId = examId;
    }

    public String getExamId() 
    {
        return examId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setTotalTime(Long totalTime) 
    {
        this.totalTime = totalTime;
    }

    public Long getTotalTime() 
    {
        return totalTime;
    }
    public void setUserTime(Long userTime) 
    {
        this.userTime = userTime;
    }

    public Long getUserTime() 
    {
        return userTime;
    }
    public void setTotalScore(Long totalScore) 
    {
        this.totalScore = totalScore;
    }

    public Long getTotalScore() 
    {
        return totalScore;
    }
    public void setQualifyScore(Long qualifyScore) 
    {
        this.qualifyScore = qualifyScore;
    }

    public Long getQualifyScore() 
    {
        return qualifyScore;
    }
    public void setObjScore(Long objScore) 
    {
        this.objScore = objScore;
    }

    public Long getObjScore() 
    {
        return objScore;
    }
    public void setSubjScore(Long subjScore) 
    {
        this.subjScore = subjScore;
    }

    public Long getSubjScore() 
    {
        return subjScore;
    }
    public void setUserScore(Long userScore) 
    {
        this.userScore = userScore;
    }

    public Long getUserScore() 
    {
        return userScore;
    }
    public void setHasSaq(Long hasSaq) 
    {
        this.hasSaq = hasSaq;
    }

    public Long getHasSaq() 
    {
        return hasSaq;
    }
    public void setState(Long state) 
    {
        this.state = state;
    }

    public Long getState() 
    {
        return state;
    }
    public void setLimitTime(Date limitTime) 
    {
        this.limitTime = limitTime;
    }

    public Date getLimitTime() 
    {
        return limitTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("examId", getExamId())
            .append("title", getTitle())
            .append("totalTime", getTotalTime())
            .append("userTime", getUserTime())
            .append("totalScore", getTotalScore())
            .append("qualifyScore", getQualifyScore())
            .append("objScore", getObjScore())
            .append("subjScore", getSubjScore())
            .append("userScore", getUserScore())
            .append("hasSaq", getHasSaq())
            .append("state", getState())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("limitTime", getLimitTime())
            .toString();
    }
}
