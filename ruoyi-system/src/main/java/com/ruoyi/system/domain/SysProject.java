package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 項目对象 sys_project
 * 
 * @author ruoyi
 * @date 2021-03-01
 */
public class SysProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目id */
    private Long id;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String name;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private String type;

    /** 项目描述 */
    @Excel(name = "项目描述")
    private String description;

    /** 项目总金额 */
    @Excel(name = "项目总金额")
    private Long total;

    /** 到账金额 */
    @Excel(name = "到账金额")
    private Long receive;

    /** 负责人电话 */
    @Excel(name = "负责人电话")
    private String phone;

    /** 负责人 **/
    private String userName;

    /** 部门 **/
    private String deptName;


    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setTotal(Long total) 
    {
        this.total = total;
    }

    public Long getTotal() 
    {
        return total;
    }
    public void setReceive(Long receive) 
    {
        this.receive = receive;
    }

    public Long getReceive() 
    {
        return receive;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deptId", getDeptId())
            .append("userId", getUserId())
            .append("name", getName())
            .append("type", getType())
            .append("description", getDescription())
            .append("total", getTotal())
            .append("receive", getReceive())
            .append("phone", getPhone())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
