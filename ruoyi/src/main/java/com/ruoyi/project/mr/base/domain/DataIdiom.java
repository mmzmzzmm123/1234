package com.ruoyi.project.mr.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 成语存放对象 data_idiom
 * 
 * @author mr
 * @date 2020-03-03
 */
public class DataIdiom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 成语 */
    @Excel(name = "成语")
    private String idiomName;

    /** 拼音 */
    @Excel(name = "拼音")
    private String idiomPinyin;

    /** 注释 */
    @Excel(name = "注释")
    private String idiomDesc;

    /** 难易程度 */
    @Excel(name = "难易程度")
    private Integer idiomLevel;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setIdiomName(String idiomName) 
    {
        this.idiomName = idiomName;
    }

    public String getIdiomName() 
    {
        return idiomName;
    }
    public void setIdiomPinyin(String idiomPinyin) 
    {
        this.idiomPinyin = idiomPinyin;
    }

    public String getIdiomPinyin() 
    {
        return idiomPinyin;
    }
    public void setIdiomDesc(String idiomDesc) 
    {
        this.idiomDesc = idiomDesc;
    }

    public String getIdiomDesc() 
    {
        return idiomDesc;
    }
    public void setIdiomLevel(Integer idiomLevel) 
    {
        this.idiomLevel = idiomLevel;
    }

    public Integer getIdiomLevel() 
    {
        return idiomLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("idiomName", getIdiomName())
            .append("idiomPinyin", getIdiomPinyin())
            .append("idiomDesc", getIdiomDesc())
            .append("idiomLevel", getIdiomLevel())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
