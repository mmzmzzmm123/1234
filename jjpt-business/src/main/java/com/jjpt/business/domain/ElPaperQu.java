package com.jjpt.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考试记录考题对象 el_paper_qu
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
public class ElPaperQu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 试卷ID */
    @Excel(name = "试卷ID")
    private String paperId;

    /** 题目ID */
    @Excel(name = "题目ID")
    private String quId;

    /** 题目类型 */
    @Excel(name = "题目类型")
    private Integer quType;

    /** 是否已答 */
    @Excel(name = "是否已答")
    private Boolean answered;

    /** 主观答案 */
    @Excel(name = "主观答案")
    private String answer;

    /** 问题排序 */
    @Excel(name = "问题排序")
    private Integer sort;

    /** 单题分分值 */
    @Excel(name = "单题分分值")
    private Integer score;

    /** 实际得分(主观题) */
    @Excel(name = "实际得分(主观题)")
    private Integer actualScore;

    /** 是否答对 */
    @Excel(name = "是否答对")
    private Boolean isRight;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setPaperId(String paperId) 
    {
        this.paperId = paperId;
    }

    public String getPaperId() 
    {
        return paperId;
    }
    public void setQuId(String quId) 
    {
        this.quId = quId;
    }

    public String getQuId() 
    {
        return quId;
    }
    public void setQuType(Integer quType) 
    {
        this.quType = quType;
    }

    public Integer getQuType()
    {
        return quType;
    }
    public void setAnswered(Boolean answered)
    {
        this.answered = answered;
    }

    public Boolean getAnswered()
    {
        return answered;
    }
    public void setAnswer(String answer) 
    {
        this.answer = answer;
    }

    public String getAnswer() 
    {
        return answer;
    }
    public void setSort(Integer sort) 
    {
        this.sort = sort;
    }

    public Integer getSort() 
    {
        return sort;
    }
    public void setScore(Integer score) 
    {
        this.score = score;
    }

    public Integer getScore() 
    {
        return score;
    }
    public void setActualScore(Integer actualScore) 
    {
        this.actualScore = actualScore;
    }

    public Integer getActualScore() 
    {
        return actualScore;
    }
    public void setIsRight(Boolean isRight)
    {
        this.isRight = isRight;
    }

    public Boolean getIsRight()
    {
        return isRight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("paperId", getPaperId())
            .append("quId", getQuId())
            .append("quType", getQuType())
            .append("answered", getAnswered())
            .append("answer", getAnswer())
            .append("sort", getSort())
            .append("score", getScore())
            .append("actualScore", getActualScore())
            .append("isRight", getIsRight())
            .toString();
    }
}
