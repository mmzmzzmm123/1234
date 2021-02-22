package com.gox.basic.domain;

import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import com.gox.common.utils.uuid.SnowflakesTools;
import com.gox.basic.domain.form.FormDesignerData;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;


/**
 * 单json存储对象 form_json
 * @author gox
 * @date 2020-12-25
 */
@Alias(value = "FormJson")
public class FormJson extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public FormJson() {
        setId(SnowflakesTools.WORKER.nextId());
    }

    /** id */
    private Long id;

    /** 表单名字 */
    @Excel(name = "表单名字")
    private String formName;
    /** 表单json */
    @Excel(name = "表单json")
    private String formData;
    @Excel(name = "所属节点")
    private Long nodeId;
    @Excel(name = "部门id")
    private Long deptId;
    @Excel(name = "排序")
    private Integer order;
    /** 删除标志 */
    private String delFlag;
    private transient FormDesignerData formDesignerData;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public FormDesignerData getFormDesignerData() {
        return formDesignerData;
    }

    public void setFormDesignerData(FormDesignerData formDesignerData) {
        this.formDesignerData = formDesignerData;
    }



    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFormName(String formName) 
    {
        this.formName = formName;
    }

    public String getFormName() 
    {
        return formName;
    }
    public void setFormData(String formData) 
    {
        this.formData = formData;
    }

    public String getFormData() 
    {
        return formData;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .append("formName", getFormName())
            .append("formData", getFormData())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
