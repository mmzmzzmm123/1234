package com.jjpt.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考试题库对象 el_exam_repo
 * 
 * @author 卢亚峰
 * @date 2024-05-19
 */
public class ElExamRepo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 考试ID */
    @Excel(name = "考试ID")
    private String examId;

    /** 题库ID */
    @Excel(name = "题库ID")
    private String repoId;

    /** 单选题数量 */
    @Excel(name = "单选题数量")
    private Integer radioCount;

    /** 单选题分数 */
    @Excel(name = "单选题分数")
    private Integer radioScore;

    /** 多选题数量 */
    @Excel(name = "多选题数量")
    private Integer multiCount;

    /** 多选题分数 */
    @Excel(name = "多选题分数")
    private Integer multiScore;

    /** 判断题数量 */
    @Excel(name = "判断题数量")
    private Integer judgeCount;

    /** 判断题分数 */
    @Excel(name = "判断题分数")
    private Integer judgeScore;

    /** 简答题数量 */
    @Excel(name = "简答题数量")
    private Integer saqCount;

    /** 简答题分数 */
    @Excel(name = "简答题分数")
    private Integer saqScore;

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
    public void setRepoId(String repoId) 
    {
        this.repoId = repoId;
    }

    public String getRepoId() 
    {
        return repoId;
    }
    public void setRadioCount(Integer radioCount)
    {
        this.radioCount = radioCount;
    }

    public Integer getRadioCount()
    {
        return radioCount;
    }
    public void setRadioScore(Integer radioScore)
    {
        this.radioScore = radioScore;
    }

    public Integer getRadioScore()
    {
        return radioScore;
    }
    public void setMultiCount(Integer multiCount)
    {
        this.multiCount = multiCount;
    }

    public Integer getMultiCount()
    {
        return multiCount;
    }
    public void setMultiScore(Integer multiScore)
    {
        this.multiScore = multiScore;
    }

    public Integer getMultiScore()
    {
        return multiScore;
    }
    public void setJudgeCount(Integer judgeCount)
    {
        this.judgeCount = judgeCount;
    }

    public Integer getJudgeCount()
    {
        return judgeCount;
    }
    public void setJudgeScore(Integer judgeScore)
    {
        this.judgeScore = judgeScore;
    }

    public Integer getJudgeScore()
    {
        return judgeScore;
    }
    public void setSaqCount(Integer saqCount)
    {
        this.saqCount = saqCount;
    }

    public Integer getSaqCount()
    {
        return saqCount;
    }
    public void setSaqScore(Integer saqScore)
    {
        this.saqScore = saqScore;
    }

    public Integer getSaqScore()
    {
        return saqScore;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("examId", getExamId())
            .append("repoId", getRepoId())
            .append("radioCount", getRadioCount())
            .append("radioScore", getRadioScore())
            .append("multiCount", getMultiCount())
            .append("multiScore", getMultiScore())
            .append("judgeCount", getJudgeCount())
            .append("judgeScore", getJudgeScore())
            .append("saqCount", getSaqCount())
            .append("saqScore", getSaqScore())
            .toString();
    }
}
