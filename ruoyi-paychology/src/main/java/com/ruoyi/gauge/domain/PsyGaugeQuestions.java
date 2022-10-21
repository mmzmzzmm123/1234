package com.ruoyi.gauge.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心理测评问题对象 psy_gauge_questions
 * 
 * @author ruoyi
 * @date 2022-09-06
 */
public class PsyGaugeQuestions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 测评表单ID */
    @Excel(name = "测评表单ID")
    private Long gaugeId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /**
     * 选择类型
     * 0-单选
     * 1-多选
     */
    private Long selectType;

    @Excel(name = "选择类型")
    private String selectTypeName;

    /** 序号 */
    @Excel(name = "序号")
    private Long no;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGaugeId(Long gaugeId) 
    {
        this.gaugeId = gaugeId;
    }

    public Long getGaugeId() 
    {
        return gaugeId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setNo(Long no) 
    {
        this.no = no;
    }

    public Long getNo() 
    {
        return no;
    }

    public Long getSelectType() {
        return selectType;
    }

    public void setSelectType(Long selectType) {
        this.selectType = selectType;
        this.selectTypeName = this.selectType == 0 ? "单选" : "多选";
    }

    public String getSelectTypeName() {
        return selectTypeName;
    }

    public void setSelectTypeName(String selectTypeName) {
        this.selectTypeName = selectTypeName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gaugeId", getGaugeId())
            .append("title", getTitle())
            .append("no", getNo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
