package com.jjpt.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jjpt.business.annotation.Dict;
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
    @Dict(dictTable = "sys_user", dicText = "nick_name", dicCode = "user_id")
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
    private Integer totalTime;

    /** 用户时长 */
    @Excel(name = "用户时长")
    private Integer userTime;

    /** 试卷总分 */
    @Excel(name = "试卷总分")
    private Integer totalScore;

    /** 及格分 */
    @Excel(name = "及格分")
    private Integer qualifyScore;

    /** 客观分 */
    @Excel(name = "客观分")
    private Integer objScore;

    /** 主观分 */
    @Excel(name = "主观分")
    private Integer subjScore;

    /** 用户得分 */
    @Excel(name = "用户得分")
    private Integer userScore;

    /** 是否包含简答题 */
    @Excel(name = "是否包含简答题")
    private Boolean hasSaq;

    /** 试卷状态 */
    @Excel(name = "试卷状态")
    private Integer state;

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
    public void setTotalTime(Integer totalTime)
    {
        this.totalTime = totalTime;
    }

    public Integer getTotalTime()
    {
        return totalTime;
    }
    public void setUserTime(Integer userTime)
    {
        this.userTime = userTime;
    }

    public Integer getUserTime()
    {
        return userTime;
    }
    public void setTotalScore(Integer totalScore)
    {
        this.totalScore = totalScore;
    }

    public Integer getTotalScore()
    {
        return totalScore;
    }
    public void setQualifyScore(Integer qualifyScore)
    {
        this.qualifyScore = qualifyScore;
    }

    public Integer getQualifyScore()
    {
        return qualifyScore;
    }
    public void setObjScore(Integer objScore)
    {
        this.objScore = objScore;
    }

    public Integer getObjScore()
    {
        return objScore;
    }
    public void setSubjScore(Integer subjScore)
    {
        this.subjScore = subjScore;
    }

    public Integer getSubjScore()
    {
        return subjScore;
    }
    public void setUserScore(Integer userScore)
    {
        this.userScore = userScore;
    }

    public Integer getUserScore()
    {
        return userScore;
    }
    public void setHasSaq(Boolean hasSaq)
    {
        this.hasSaq = hasSaq;
    }

    public Boolean getHasSaq()
    {
        return hasSaq;
    }
    public void setState(Integer state)
    {
        this.state = state;
    }

    public Integer getState()
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
