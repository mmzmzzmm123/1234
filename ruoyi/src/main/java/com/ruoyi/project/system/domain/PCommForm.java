package com.ruoyi.project.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 数据模型对象 p_comm_form
 * 
 * @author ruoyi
 * @date 2020-05-14
 */
public class PCommForm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long formId;

    /** 表单名称 */
    @Excel(name = "表单名称")
    private String formName;

    /** 表单描述 */
    @Excel(name = "表单描述")
    private String formComment;

    /** 实体类名称 */
    @Excel(name = "实体类名称")
    private String tableName;

    /** 表单配置json */
    @Excel(name = "表单配置json")
    private String options;

    /** 版本号 */
    @Excel(name = "版本号")
    private String versionName;

    public void setFormId(Long formId) 
    {
        this.formId = formId;
    }

    public Long getFormId() 
    {
        return formId;
    }
    public void setFormName(String formName) 
    {
        this.formName = formName;
    }

    public String getFormName() 
    {
        return formName;
    }
    public void setFormComment(String formComment) 
    {
        this.formComment = formComment;
    }

    public String getFormComment() 
    {
        return formComment;
    }
    public void setTableName(String tableName) 
    {
        this.tableName = tableName;
    }

    public String getTableName() 
    {
        return tableName;
    }
    public void setOptions(String options) 
    {
        this.options = options;
    }

    public String getOptions() 
    {
        return options;
    }
    public void setVersionName(String versionName) 
    {
        this.versionName = versionName;
    }

    public String getVersionName() 
    {
        return versionName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("formId", getFormId())
            .append("formName", getFormName())
            .append("formComment", getFormComment())
            .append("tableName", getTableName())
            .append("options", getOptions())
            .append("versionName", getVersionName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
