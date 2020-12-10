package com.ruoyi.project.benyi.domain;

import com.ruoyi.project.system.domain.SysDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 园所收费标准对象 by_schoolcharge
 *
 * @author tsbz
 * @date 2020-12-10
 */
public class BySchoolcharge extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 园所id
     */
    @Excel(name = "园所id")
    private Long deptId;

    /**
     * 保育费
     */
    @Excel(name = "保育费")
    private Double byf;

    /**
     * 伙食费
     */
    @Excel(name = "伙食费")
    private Double hsf;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

    private SysDept dept;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setByf(Double byf) {
        this.byf = byf;
    }

    public Double getByf() {
        return byf;
    }

    public void setHsf(Double hsf) {
        this.hsf = hsf;
    }

    public Double getHsf() {
        return hsf;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("deptId", getDeptId())
                .append("byf", getByf())
                .append("hsf", getHsf())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("dept", getDept())
                .toString();
    }
}
