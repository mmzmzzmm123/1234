package com.jjpt.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考试记录答案对象 el_paper_qu_answer
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
public class ElPaperQuAnswer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private String id;

    /** 试卷ID */
    @Excel(name = "试卷ID")
    private String paperId;

    /** 回答项ID */
    @Excel(name = "回答项ID")
    private String answerId;

    /** 题目ID */
    @Excel(name = "题目ID")
    private String quId;

    /** 是否正确项 */
    @Excel(name = "是否正确项")
    private Boolean isRight;

    /** 是否选中 */
    @Excel(name = "是否选中")
    private Boolean checked;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 选项标签 */
    @Excel(name = "选项标签")
    private String abc;

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
    public void setAnswerId(String answerId) 
    {
        this.answerId = answerId;
    }

    public String getAnswerId() 
    {
        return answerId;
    }
    public void setQuId(String quId) 
    {
        this.quId = quId;
    }

    public String getQuId() 
    {
        return quId;
    }
    public void setIsRight(Boolean isRight) 
    {
        this.isRight = isRight;
    }

    public Boolean getIsRight() 
    {
        return isRight;
    }
    public void setChecked(Boolean checked) 
    {
        this.checked = checked;
    }

    public Boolean getChecked() 
    {
        return checked;
    }
    public void setSort(Integer sort) 
    {
        this.sort = sort;
    }

    public Integer getSort() 
    {
        return sort;
    }
    public void setAbc(String abc) 
    {
        this.abc = abc;
    }

    public String getAbc() 
    {
        return abc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("paperId", getPaperId())
            .append("answerId", getAnswerId())
            .append("quId", getQuId())
            .append("isRight", getIsRight())
            .append("checked", getChecked())
            .append("sort", getSort())
            .append("abc", getAbc())
            .toString();
    }
}
