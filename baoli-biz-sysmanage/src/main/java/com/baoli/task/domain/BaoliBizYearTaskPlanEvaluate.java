package com.baoli.task.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 年任务考核对象 baoli_biz_year_task_plan_evaluate
 * 
 * @author niujs
 * @date 2024-05-14
 */
public class BaoliBizYearTaskPlanEvaluate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 省份 */
    @Excel(name = "省份")
    private String provinceId;

    /** 城市 */
    @Excel(name = "城市")
    private String cityId;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    /** 金额 */
    @Excel(name = "金额")
    private Long amount;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long season1;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long season2;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long season3;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long season4;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month1;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month2;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month3;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month4;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month5;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month6;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month7;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month8;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month9;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month10;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month11;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long month12;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date assignTime;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month1EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month1RefrencePlan;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month2EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month2RefrencePlan;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month3EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month3RefrencePlan;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month4EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month4RefrencePlan;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month5EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month5RefrencePlan;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month6EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month6RefrencePlan;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month7EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month7RefrencePlan;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month8EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month8RefrencePlan;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month9EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month9RefrencePlan;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month10EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month10RefrencePlan;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month11EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month11RefrencePlan;

    /** 考核方式 */
    @Excel(name = "考核方式")
    private String month12EvaluateType;

    /** 是否参考年任务 */
    @Excel(name = "是否参考年任务")
    private String month12RefrencePlan;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProvinceId(String provinceId) 
    {
        this.provinceId = provinceId;
    }

    public String getProvinceId() 
    {
        return provinceId;
    }
    public void setCityId(String cityId) 
    {
        this.cityId = cityId;
    }

    public String getCityId() 
    {
        return cityId;
    }
    public void setYear(String year) 
    {
        this.year = year;
    }

    public String getYear() 
    {
        return year;
    }
    public void setAmount(Long amount) 
    {
        this.amount = amount;
    }

    public Long getAmount() 
    {
        return amount;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setSeason1(Long season1) 
    {
        this.season1 = season1;
    }

    public Long getSeason1() 
    {
        return season1;
    }
    public void setSeason2(Long season2) 
    {
        this.season2 = season2;
    }

    public Long getSeason2() 
    {
        return season2;
    }
    public void setSeason3(Long season3) 
    {
        this.season3 = season3;
    }

    public Long getSeason3() 
    {
        return season3;
    }
    public void setSeason4(Long season4) 
    {
        this.season4 = season4;
    }

    public Long getSeason4() 
    {
        return season4;
    }
    public void setMonth1(Long month1) 
    {
        this.month1 = month1;
    }

    public Long getMonth1() 
    {
        return month1;
    }
    public void setMonth2(Long month2) 
    {
        this.month2 = month2;
    }

    public Long getMonth2() 
    {
        return month2;
    }
    public void setMonth3(Long month3) 
    {
        this.month3 = month3;
    }

    public Long getMonth3() 
    {
        return month3;
    }
    public void setMonth4(Long month4) 
    {
        this.month4 = month4;
    }

    public Long getMonth4() 
    {
        return month4;
    }
    public void setMonth5(Long month5) 
    {
        this.month5 = month5;
    }

    public Long getMonth5() 
    {
        return month5;
    }
    public void setMonth6(Long month6) 
    {
        this.month6 = month6;
    }

    public Long getMonth6() 
    {
        return month6;
    }
    public void setMonth7(Long month7) 
    {
        this.month7 = month7;
    }

    public Long getMonth7() 
    {
        return month7;
    }
    public void setMonth8(Long month8) 
    {
        this.month8 = month8;
    }

    public Long getMonth8() 
    {
        return month8;
    }
    public void setMonth9(Long month9) 
    {
        this.month9 = month9;
    }

    public Long getMonth9() 
    {
        return month9;
    }
    public void setMonth10(Long month10) 
    {
        this.month10 = month10;
    }

    public Long getMonth10() 
    {
        return month10;
    }
    public void setMonth11(Long month11) 
    {
        this.month11 = month11;
    }

    public Long getMonth11() 
    {
        return month11;
    }
    public void setMonth12(Long month12) 
    {
        this.month12 = month12;
    }

    public Long getMonth12() 
    {
        return month12;
    }
    public void setAssignTime(Date assignTime) 
    {
        this.assignTime = assignTime;
    }

    public Date getAssignTime() 
    {
        return assignTime;
    }
    public void setMonth1EvaluateType(String month1EvaluateType) 
    {
        this.month1EvaluateType = month1EvaluateType;
    }

    public String getMonth1EvaluateType() 
    {
        return month1EvaluateType;
    }
    public void setMonth1RefrencePlan(String month1RefrencePlan) 
    {
        this.month1RefrencePlan = month1RefrencePlan;
    }

    public String getMonth1RefrencePlan() 
    {
        return month1RefrencePlan;
    }
    public void setMonth2EvaluateType(String month2EvaluateType) 
    {
        this.month2EvaluateType = month2EvaluateType;
    }

    public String getMonth2EvaluateType() 
    {
        return month2EvaluateType;
    }
    public void setMonth2RefrencePlan(String month2RefrencePlan) 
    {
        this.month2RefrencePlan = month2RefrencePlan;
    }

    public String getMonth2RefrencePlan() 
    {
        return month2RefrencePlan;
    }
    public void setMonth3EvaluateType(String month3EvaluateType) 
    {
        this.month3EvaluateType = month3EvaluateType;
    }

    public String getMonth3EvaluateType() 
    {
        return month3EvaluateType;
    }
    public void setMonth3RefrencePlan(String month3RefrencePlan) 
    {
        this.month3RefrencePlan = month3RefrencePlan;
    }

    public String getMonth3RefrencePlan() 
    {
        return month3RefrencePlan;
    }
    public void setMonth4EvaluateType(String month4EvaluateType) 
    {
        this.month4EvaluateType = month4EvaluateType;
    }

    public String getMonth4EvaluateType() 
    {
        return month4EvaluateType;
    }
    public void setMonth4RefrencePlan(String month4RefrencePlan) 
    {
        this.month4RefrencePlan = month4RefrencePlan;
    }

    public String getMonth4RefrencePlan() 
    {
        return month4RefrencePlan;
    }
    public void setMonth5EvaluateType(String month5EvaluateType) 
    {
        this.month5EvaluateType = month5EvaluateType;
    }

    public String getMonth5EvaluateType() 
    {
        return month5EvaluateType;
    }
    public void setMonth5RefrencePlan(String month5RefrencePlan) 
    {
        this.month5RefrencePlan = month5RefrencePlan;
    }

    public String getMonth5RefrencePlan() 
    {
        return month5RefrencePlan;
    }
    public void setMonth6EvaluateType(String month6EvaluateType) 
    {
        this.month6EvaluateType = month6EvaluateType;
    }

    public String getMonth6EvaluateType() 
    {
        return month6EvaluateType;
    }
    public void setMonth6RefrencePlan(String month6RefrencePlan) 
    {
        this.month6RefrencePlan = month6RefrencePlan;
    }

    public String getMonth6RefrencePlan() 
    {
        return month6RefrencePlan;
    }
    public void setMonth7EvaluateType(String month7EvaluateType) 
    {
        this.month7EvaluateType = month7EvaluateType;
    }

    public String getMonth7EvaluateType() 
    {
        return month7EvaluateType;
    }
    public void setMonth7RefrencePlan(String month7RefrencePlan) 
    {
        this.month7RefrencePlan = month7RefrencePlan;
    }

    public String getMonth7RefrencePlan() 
    {
        return month7RefrencePlan;
    }
    public void setMonth8EvaluateType(String month8EvaluateType) 
    {
        this.month8EvaluateType = month8EvaluateType;
    }

    public String getMonth8EvaluateType() 
    {
        return month8EvaluateType;
    }
    public void setMonth8RefrencePlan(String month8RefrencePlan) 
    {
        this.month8RefrencePlan = month8RefrencePlan;
    }

    public String getMonth8RefrencePlan() 
    {
        return month8RefrencePlan;
    }
    public void setMonth9EvaluateType(String month9EvaluateType) 
    {
        this.month9EvaluateType = month9EvaluateType;
    }

    public String getMonth9EvaluateType() 
    {
        return month9EvaluateType;
    }
    public void setMonth9RefrencePlan(String month9RefrencePlan) 
    {
        this.month9RefrencePlan = month9RefrencePlan;
    }

    public String getMonth9RefrencePlan() 
    {
        return month9RefrencePlan;
    }
    public void setMonth10EvaluateType(String month10EvaluateType) 
    {
        this.month10EvaluateType = month10EvaluateType;
    }

    public String getMonth10EvaluateType() 
    {
        return month10EvaluateType;
    }
    public void setMonth10RefrencePlan(String month10RefrencePlan) 
    {
        this.month10RefrencePlan = month10RefrencePlan;
    }

    public String getMonth10RefrencePlan() 
    {
        return month10RefrencePlan;
    }
    public void setMonth11EvaluateType(String month11EvaluateType) 
    {
        this.month11EvaluateType = month11EvaluateType;
    }

    public String getMonth11EvaluateType() 
    {
        return month11EvaluateType;
    }
    public void setMonth11RefrencePlan(String month11RefrencePlan) 
    {
        this.month11RefrencePlan = month11RefrencePlan;
    }

    public String getMonth11RefrencePlan() 
    {
        return month11RefrencePlan;
    }
    public void setMonth12EvaluateType(String month12EvaluateType) 
    {
        this.month12EvaluateType = month12EvaluateType;
    }

    public String getMonth12EvaluateType() 
    {
        return month12EvaluateType;
    }
    public void setMonth12RefrencePlan(String month12RefrencePlan) 
    {
        this.month12RefrencePlan = month12RefrencePlan;
    }

    public String getMonth12RefrencePlan() 
    {
        return month12RefrencePlan;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("provinceId", getProvinceId())
            .append("cityId", getCityId())
            .append("year", getYear())
            .append("amount", getAmount())
            .append("status", getStatus())
            .append("season1", getSeason1())
            .append("season2", getSeason2())
            .append("season3", getSeason3())
            .append("season4", getSeason4())
            .append("month1", getMonth1())
            .append("month2", getMonth2())
            .append("month3", getMonth3())
            .append("month4", getMonth4())
            .append("month5", getMonth5())
            .append("month6", getMonth6())
            .append("month7", getMonth7())
            .append("month8", getMonth8())
            .append("month9", getMonth9())
            .append("month10", getMonth10())
            .append("month11", getMonth11())
            .append("month12", getMonth12())
            .append("createTime", getCreateTime())
            .append("assignTime", getAssignTime())
            .append("month1EvaluateType", getMonth1EvaluateType())
            .append("month1RefrencePlan", getMonth1RefrencePlan())
            .append("month2EvaluateType", getMonth2EvaluateType())
            .append("month2RefrencePlan", getMonth2RefrencePlan())
            .append("month3EvaluateType", getMonth3EvaluateType())
            .append("month3RefrencePlan", getMonth3RefrencePlan())
            .append("month4EvaluateType", getMonth4EvaluateType())
            .append("month4RefrencePlan", getMonth4RefrencePlan())
            .append("month5EvaluateType", getMonth5EvaluateType())
            .append("month5RefrencePlan", getMonth5RefrencePlan())
            .append("month6EvaluateType", getMonth6EvaluateType())
            .append("month6RefrencePlan", getMonth6RefrencePlan())
            .append("month7EvaluateType", getMonth7EvaluateType())
            .append("month7RefrencePlan", getMonth7RefrencePlan())
            .append("month8EvaluateType", getMonth8EvaluateType())
            .append("month8RefrencePlan", getMonth8RefrencePlan())
            .append("month9EvaluateType", getMonth9EvaluateType())
            .append("month9RefrencePlan", getMonth9RefrencePlan())
            .append("month10EvaluateType", getMonth10EvaluateType())
            .append("month10RefrencePlan", getMonth10RefrencePlan())
            .append("month11EvaluateType", getMonth11EvaluateType())
            .append("month11RefrencePlan", getMonth11RefrencePlan())
            .append("month12EvaluateType", getMonth12EvaluateType())
            .append("month12RefrencePlan", getMonth12RefrencePlan())
            .toString();
    }
}
