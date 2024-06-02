package com.baoli.task.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 年任务管理（T）对象 baoli_biz_year_task_plan
 * 
 * @author niujs
 * @date 2024-05-14
 */
public class BaoliBizYearTaskPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
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
    private Date assignTime;

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
            .toString();
    }
}
