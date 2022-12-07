package com.ruoyi.student.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;

/**
 * 学生历史状态对象 stu_state_his
 * 
 * @author wangzq
 * @date 2022-12-05
 */
public class StuStateHis extends StuInfo
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 历史状态 */
    private String hisState;
    @Excel(name = "历史状态", sort = 2)
    private String hisStateName;

    /** 历史状态日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "历史状态日期", width = 30, dateFormat = "yyyy-MM-dd", sort = 2)
    private Date hisStateDate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setHisState(String hisState)
    {
        this.hisState = hisState;
    }

    public String getHisState() 
    {
        return hisState;
    }
    public void setHisStateDate(Date hisStateDate) 
    {
        this.hisStateDate = hisStateDate;
    }

    public Date getHisStateDate() 
    {
        return hisStateDate;
    }

    public String getHisStateName() {
        return hisStateName;
    }

    public void setHisStateName(String hisStateName) {
        this.hisStateName = hisStateName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("studentId", getStudentId())
            .append("hisState", getHisState())
            .append("hisStateDate", getHisStateDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
